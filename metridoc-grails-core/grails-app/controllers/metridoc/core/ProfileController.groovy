/*
  *Copyright 2013 Trustees of the University of Pennsylvania. Licensed under the
  *	Educational Community License, Version 2.0 (the "License"); you may
  *	not use this file except in compliance with the License. You may
  *	obtain a copy of the License at
  *
  *http://www.osedu.org/licenses/ECL-2.0
  *
  *	Unless required by applicable law or agreed to in writing,
  *	software distributed under the License is distributed on an "AS IS"
  *	BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
  *	or implied. See the License for the specific language governing
  *	permissions and limitations under the License.  */

package metridoc.core

import org.apache.shiro.SecurityUtils
import org.apache.shiro.crypto.hash.Sha256Hash

class ProfileController {

    static boolean isProtected = true
    static allowedMethods = [save: "POST", update: "POST", index: "GET"]


    def index() {
        chain(action: "edit")
    }

    def edit() {
        def currentUser = SecurityUtils.getSubject().getPrincipal()

        ShiroUser shiroUserInstance = ShiroUser.findByUsername(currentUser as String)

        if (shiroUserInstance.username == 'anonymous') {
            flash.message = message(code: 'cannot.modify.message', args: ['Anonymous User'], default: 'Anonymous User cannot be modified.')
        } else if (params.flashMessage) {
            flash.message = params.flashMessage
        }

        [shiroUserInstance: shiroUserInstance]
    }

    def update() {
        def shiroUserInstance = ShiroUser.get(params.id)

        if (!shiroUserInstance) {
            flash.alert = "Could not find user with id ${params.id}"
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (shiroUserInstance.version > version) {
                flash.alert = "Another user has updated this ShiroUser while you were editing"
                render(view: "/profile/edit", model: [shiroUserInstance: shiroUserInstance])
                return
            }
        }

        shiroUserInstance.lock()

        if (params.changePW) {
            shiroUserInstance.validatePasswords = true
            shiroUserInstance.oldPassword = params.oldPassword
            shiroUserInstance.password = params.password
            shiroUserInstance.confirm = params.confirm
            def valid = shiroUserInstance.validate()
            if (valid) {
                shiroUserInstance.passwordHash = new Sha256Hash(shiroUserInstance.password).toHex()
                shiroUserInstance.hashAgainstOldPassword = false
            }

            log.info "password and user details for ${shiroUserInstance.username} are changing"
        } else {
            log.info "user details for ${shiroUserInstance.username} are changing, password will remain the same"
            updateUserInfo(shiroUserInstance, params)
        }

        if (!shiroUserInstance.save(flush: true)) {
            if (shiroUserInstance.errors) {
                ShiroUser.addAlertForAllErrors(shiroUserInstance, flash)
            }

            redirect(action: "edit")
            return
        }

        flash.info = "User details updated"
        redirect(action: "edit")
    }

    static protected updateUserInfo(ShiroUser user, params) {
        if (params.emailAddress) {
            user.emailAddress = params.emailAddress
        }
    }

    static protected updateUserInfoAndPassword(ShiroUser user, params) {
        updateUserInfo(user, params)
        user.with {
            password = params.password
            confirm = params.confirm
            user.setPasswordHash(new Sha256Hash(password).toHex())
        }
    }
}

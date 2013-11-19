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
    def authService
    def ldapOperationsService

    def index() {
        chain(action: "edit")
    }

    def edit() {
        def currentUser = SecurityUtils.getSubject().getPrincipal()

        ShiroUser shiroUserInstance = ShiroUser.findByUsername(currentUser as String)

        if (shiroUserInstance) {
            if (shiroUserInstance.username == 'anonymous') {
                flash.message = message(code: 'cannot.modify.message', args: ['Anonymous User'], default: 'Anonymous User cannot be modified.')
            } else if (params.flashMessage) {
                flash.message = params.flashMessage
            }
        }

        [shiroUserInstance: shiroUserInstance, managingAccount: true, ldapGroups: ldapOperationsService.getGroups(currentUser)]
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

        authService.updateUser(shiroUserInstance, params)

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

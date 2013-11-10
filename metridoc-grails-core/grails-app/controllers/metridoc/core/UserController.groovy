/*
  - Copyright 2013 Trustees of the University of Pennsylvania. Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
package metridoc.core

import org.apache.shiro.SecurityUtils
import org.apache.shiro.crypto.hash.Sha256Hash
import org.codehaus.groovy.grails.web.servlet.mvc.GrailsParameterMap
import org.springframework.dao.DataIntegrityViolationException

class UserController {

    def authService
    static allowedMethods = [save: "POST", update: "POST", delete: ['DELETE', "POST"], list: "GET", index: "GET"]
    static accessControl = {
        role(name: "ROLE_ADMIN")
    }
    def userService

    def index() {
        session.setAttribute("previousExpanded", "userList")
        chain(controller: "manageAccess", action: "list")
    }


    def create() {
        [shiroUserInstance: new ShiroUser(params)]
    }

    def save() {

        def password = params.get('password')
        def confirm = params.get('confirm')

        def username = params.get('username')
        def shiroUserInstance = new ShiroUser(
                username: username,
                oldPassword: password,
                validatePasswords: true,
                password: password,
                confirm: confirm,
                passwordHash: new Sha256Hash(password, username).toHex(),
                emailAddress: params.get('emailAddress'))

        userService.addRolesToUser(shiroUserInstance, params.roles)

        if (!shiroUserInstance.save(flush: true)) {
            ShiroUser.addAlertForAllErrors(shiroUserInstance, flash)
            chain(controller: "manageAccess", action: "list", id: shiroUserInstance.id, previousExpanded: 'userList')
            return
        }

        flash.info = "User '${shiroUserInstance.username}' created"
        session.setAttribute("previousExpanded", "userList")
        chain(controller: "manageAccess", action: "index", id: shiroUserInstance.id, previousExpanded: 'userList')
    }

    def show() {
        def shiroUserInstance = ShiroUser.get(params.id)
        if (!shiroUserInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'shiroUser.label', default: 'ShiroUser'), params.id])
            session.setAttribute("previousExpanded", "userList")
            chain(controller: "manageAccess", action: "index")
            return
        }

        [currentUserName: SecurityUtils.getSubject().getPrincipal(),
                shiroUserInstance: shiroUserInstance]
    }

    def edit() {
        def shiroUserInstance = ShiroUser.get(params.id)
        if (!shiroUserInstance) {
            flash.alert = "Could not find user for id ${params.id}"
            params.previousExpanded = 'userList'
            session.setAttribute("previousExpanded", "userList")
            chain(controller: "manageAccess", action: "list")
            return
        }

        [
                currentUserName: SecurityUtils.getSubject().getPrincipal(),
                shiroUserInstance: shiroUserInstance
        ]
    }

    def update() {

        def id = params.id
        def shiroUserInstance = ShiroUser.get(params.id)
        if (!shiroUserInstance) {
            couldNotFind(id)
            params.previousExpanded = 'userList'
            session.setAttribute("previousExpanded", "userList")
            chain(controller: "manageAccess", action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (shiroUserInstance.version > version) {
                flash.alert = "Another user has updated this ShiroUser while you were editing"
                render(view: "/user/edit", model: [shiroUserInstance: shiroUserInstance])
                return
            }
        }

        shiroUserInstance.lock()
        authService.updateUser(shiroUserInstance, params)
        shiroUserInstance.with {
            roles = []
            def addRole = { roleName ->
                log.debug("adding role ${roleName} for user ${shiroUserInstance}")
                def role = ShiroRole.findByName(roleName) {}
                roles.add(role as ShiroRole)
            }
            def isAString = params.roles instanceof String
            if (isAString) {
                params.roles = [params.roles]
            }
            params.roles.each { roleName ->
                addRole(roleName)
            }
        }

        if (!shiroUserInstance.save(flush: true)) {
            ShiroUser.addAlertForAllErrors(shiroUserInstance, flash)
            if (flash.alert == null) {
                unexpectedError(shiroUserInstance)
            }
            params.previousExpanded = 'userList'
            session.setAttribute("previousExpanded", "userList")
            chain(controller: "manageAccess", action: "list")
            return
        }

        flash.info = "User '${shiroUserInstance.username}' updated"
        params.previousExpanded = 'userList'
        session.setAttribute("previousExpanded", "userList")
        chain(controller: "manageAccess", action: "list", id: shiroUserInstance.id)
    }

    @SuppressWarnings("GroovyUnnecessaryReturn")
    def delete() {

        def shiroUserInstance = ShiroUser.get(params.id)
        if (!shiroUserInstance) {
            couldNotFind(params.id)
            params.previousExpanded = 'userList'
            session.setAttribute("previousExpanded", "userList")
            chain(controller: "manageAccess", action: "list")
            return
        }

        try {
            shiroUserInstance.delete(flush: true)
            flash.info = "User ${shiroUserInstance.username} deleted"
            params.previousExpanded = 'userList'
            session.setAttribute("previousExpanded", "userList")
            chain(controller: "manageAccess", action: "list")
            return
        }
        catch (DataIntegrityViolationException e) {
            log.error("error occurred trying to delete user ${shiroUserInstance}", e)
            unexpectedError()
            params.previousExpanded = 'userList'
            session.setAttribute("previousExpanded", "userList")
            chain(controller: "manageAccess", action: "list", id: params.id)
            return
        }
    }

    def hasUser(String id) {
        render id ? ShiroUser.findByUsername(id) != null : false
    }

    def hasEmail(String id) {
        //TODO:remove this when done debugging
        log.info id
        render id ? ShiroUser.findByEmailAddress(id) != null : false
    }

    private couldNotFind(id) {
        log.error("could not find user with id ${id}")
        flash.alert = "Could not find user with id ${id}"
    }

    private unexpectedError() {
        flash.alert = "Unexpected error occurred"
    }

    private unexpectedError(ShiroUser user) {
        log.error("Unexpected error(s) occurred trying to store $user: \n${user.errors}")
        unexpectedError()
    }
}

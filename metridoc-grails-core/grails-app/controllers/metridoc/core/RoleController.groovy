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

import static org.apache.commons.lang.StringUtils.EMPTY

class RoleController {

    static allowedMethods = [save: "POST", update: "POST", delete: "DELETE", list: "GET", index: "GET"]
    def static final reportName = "Manage Roles"

    static accessControl = {
        role(name: "ROLE_ADMIN")
    }

    def index() {
        session.setAttribute("previousExpanded", "roleList")
        chain(controller: "manageAccess", action: "list")
    }


    def create() {
        [shiroRoleInstance: new ShiroRole(params)]
    }

    def save(String rolename) {

        if (rolename == null || rolename == EMPTY) {
            flash.alert = "rolename has to be provided"
            chain(controller: "manageAccess", action: "list")
        }

        def usedRoleName = rolename
        usedRoleName = usedRoleName.toUpperCase()
        if (!usedRoleName.startsWith('ROLE_')) {
            usedRoleName = 'ROLE_' + usedRoleName
        }

        def shiroRoleInstance = new ShiroRole(name: usedRoleName)

        if (!shiroRoleInstance.save(flush: true)) {
            flash.alert = "Could not save ${shiroRoleInstance}"
            chain(controller: "manageAccess", action: "list")
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'shiroRole.label', default: 'Role'), shiroRoleInstance.name.substring(5)])
        session.setAttribute("previousExpanded", "roleList")
        chain(controller: "manageAccess", action: "index", id: shiroRoleInstance.id, previousExpanded: 'roleList')
    }

    def show() {
        def shiroRoleInstance = ShiroRole.get(params.id)
        if (!shiroRoleInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'shiroRole.label', default: 'Role'), params.id])
            session.setAttribute("previousExpanded", "roleList")
            chain(controller: "manageAccess", action: "list", previousExpanded: 'roleList')
            return
        }
        [shiroRoleInstance: shiroRoleInstance]
    }

    def edit() {
        def shiroRoleInstance = ShiroRole.get(params.id)
        if (!shiroRoleInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'shiroRole.label', default: 'Role'), params.id])
            session.setAttribute("previousExpanded", "roleList")
            chain(controller: "manageAccess", action: "list", previousExpanded: 'roleList')
            return
        }
        [shiroRoleInstance: shiroRoleInstance]
    }

    def update() {
        def shiroRoleInstance = ShiroRole.get(params.id)
        if (!shiroRoleInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'shiroRole.label', default: 'Role'), params.id])
            session.setAttribute("previousExpanded", "roleList")
            chain(controller: "manageAccess", action: "list", previousExpanded: 'roleList')
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (shiroRoleInstance.version > version) {
                shiroRoleInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'shiroRole.label', default: 'Role')] as Object[],
                        "Another user has updated this ShiroRole while you were editing")
                render(view: "/role/edit", model: [shiroRoleInstance: shiroRoleInstance])
                return
            }
        }

        shiroRoleInstance.properties = params

        def roleName = params.get('rolename').toString()
        if (!roleName.startsWith('ROLE_')) {
            shiroRoleInstance.properties.put('name', 'ROLE_' + roleName.toUpperCase())
        } else {
            shiroRoleInstance.properties.put('name', roleName.toUpperCase())
        }

        if (!shiroRoleInstance.save(flush: true)) {
            render(view: "/role/edit", model: [shiroRoleInstance: shiroRoleInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'shiroRole.label', default: 'Role'), shiroRoleInstance.name.substring(5)])
        session.setAttribute("previousExpanded", "roleList")
        chain(controller: "manageAccess", action: "index", id: shiroRoleInstance.id, previousExpanded: 'roleList')
    }
}

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

class ManageAccessController {

    static homePage = [
            title: "Manage Metridoc",
            adminOnly: true,
            description: """
                Create, update, and delete users and roles.  Change configuration, load plugins and restart the
                application
            """
    ]

    static accessControl = {
        role(name: "ROLE_ADMIN")
    }

    def manageReportService

    def index() {
        chain(action: "list", flash: flash)
    }

    def list() {
        def max = Math.min(params.max ? params.int('max') : 10, 100)
        params.max = max
        def userCount = ShiroUser.count()
        def showUserPagination = userCount > max
        def roleCount = ShiroRole.count()
        def showRolePagination = roleCount > max
        def previousExpanded = ""
        if (params.previousExpanded != 'clear') {
            previousExpanded = session.getAttribute("previousExpanded")
        } else {
            previousExpanded = "none"
        }
        session.setAttribute("previousExpanded", "none")
        def oldSearch = session.getAttribute("searchFilter")
        session.setAttribute("searchFilter", "")
        [
                previousExpanded: previousExpanded,
                currentUserName: SecurityUtils.getSubject().getPrincipal(),
                shiroUserInstanceList: ShiroUser.list(params),
                shiroUserInstanceTotal: userCount,
                showUserPagination: showUserPagination,
                shiroRoleInstanceList: ShiroRole.list(params),
                shiroRoleInstanceTotal: roleCount,
                showRolePagination: showRolePagination,
                controllerDetails: manageReportService.controllerDetails,
                shiroFilters: grailsApplication.config.security.shiro.filter.filterChainDefinitions,
                searchFilter: oldSearch
        ]
    }
}

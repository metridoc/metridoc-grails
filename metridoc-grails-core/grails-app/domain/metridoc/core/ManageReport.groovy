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

class ManageReport {
    /**
     * name of the controller to protect
     */
    String controllerName
    /**
     * must a user be logged in?
     */
    Boolean isProtected = false
    /**
     * what roles must a user have to access the controller
     */
    static hasMany = [roles: ShiroRole]

    static mapping = {
        controllerName(index: "idx_report_permissions_controller_name")
    }

    static constraints = {
        controllerName(maxSize: 75)
    }

    static void overrideRoleMaps(Map<String, Map<String, List<String>>> roleMaps) {
        roleMaps.each {
            overrideRoleMap(it.key, it.value)
        }
    }

    static void overrideRoleMap(String controllerName, Map<String, List<String>> roleMap) {
        def permission = findByControllerName(controllerName)
        if (permission) {
            def roles = []
            roleMap.clear()
            roleMap << ["*": roles]
            permission.roles.each {
                roles << it.name
            }
        }
    }
}

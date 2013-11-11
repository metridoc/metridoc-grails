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
  *	permissions and limitations under the License.
  */

package metridoc.core

import org.apache.shiro.SecurityUtils

class WhoamiService {

    def restService
    static final ANONYMOUS = "anonymous"

    String getUsername(Map params) {
        if (params.get("restKey")) {
            return restService.getFromRestCache(params.get("restKey"))
        }
        return SecurityUtils.subject.principal ?: ANONYMOUS
    }

    Map getUserData(Map params) {
        def result = [:]
        result.username = getUsername(params)
        result.roles = getRolesForUser()

        return result
    }

    List getRolesForUser() {
        def roles = []

        ShiroRole.list().each {ShiroRole role ->
            if(SecurityUtils.subject.hasRole(role.name)) {
                roles << role.name
            }
        }

        return roles
    }
}

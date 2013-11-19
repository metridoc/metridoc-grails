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

class LdapSettingsController {

    static accessControl = {
        role(name: "ROLE_ADMIN")
    }

    def encryptionService

    def index() {
        chain(controller: "ldapRole", action: "index")
    }

    def save(String server, String rootDN, String userSearchBase, String userSearchFilter, String managerDN, String unencryptedPassword) {
        def newLdapConfig

        if (LdapData.list().size() > 0) {
            newLdapConfig = LdapData.list().get(0)
            newLdapConfig.server = server
            newLdapConfig.rootDN = rootDN
            newLdapConfig.userSearchBase = userSearchBase
            newLdapConfig.userSearchFilter = userSearchFilter
            newLdapConfig.managerDN = managerDN
        } else {
            newLdapConfig = new LdapData(
                    server: server,
                    rootDN: rootDN,
                    userSearchBase: userSearchBase,
                    userSearchFilter: userSearchFilter,
                    managerDN: managerDN,
            )
        }

        encryptionService.encryptLdapData(newLdapConfig, unencryptedPassword)
        if(!newLdapConfig.validate()) {
            newLdapConfig.errors.allErrors.each {
                flash.alerts << message(error: it)
            }
        }
        else {
            newLdapConfig.save(failOnError: true)
            flash.message = "LDAP configuration updated"
        }

        session.setAttribute("previousExpanded", "ldapConfig")
        chain(controller: "ldapRole", action: "index")
    }
}

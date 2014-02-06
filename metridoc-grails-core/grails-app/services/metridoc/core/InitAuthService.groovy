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

import org.apache.shiro.crypto.hash.Sha256Hash

/**
 * @auhor Tommy Barker
 *
 * initializes all default roles and users.  The default users are <code>admin</code> and <code>anonymous</code>.
 */
class InitAuthService {

    def grailsApplication
    def pluginManager
    def encryptionService
    final static DEFAULT_PASSWORD = "password"
    final static ANONYMOUS = "anonymous"
    final static ADMIN = "admin"
    final static REPORT_USER = "report_user"
    final static ROLE = "ROLE_"
    final static SUPER_USER = "super_user"
    final static REST = "rest"
    final static DEFAULT_ROLES = [ADMIN, SUPER_USER, REST, ANONYMOUS]

    /**
     * calls all security initializations
     */
    def init() {
        initDefaultRoles()
        initRoleOverides()
        initAdminUser()
        initAnonymousUser()
        initCryptKey()

    }

    def initRoleOverides() {
        Map<String, Map<String, List<String>>> roleMaps = pluginManager.getGrailsPluginForClassName("ShiroGrailsPlugin").instance.roleMaps
        ManageReport.overrideRoleMaps(roleMaps)
    }

    def initAdminUser() {
        ShiroUser.withTransaction {

            def adminUser = ShiroUser.find {
                username == "admin"
            }

            if (!adminUser) {
                def preConfiguredPassword = grailsApplication.config.metridoc.admin.password
                def password = preConfiguredPassword ? preConfiguredPassword : DEFAULT_PASSWORD
                if (DEFAULT_PASSWORD == password) {
                    log.warn "Could not find user admin, creating a default one with password '${DEFAULT_PASSWORD}'.  Change this immediatelly"
                }
                adminUser = new ShiroUser(username: 'admin', passwordHash: new Sha256Hash(password, "admin").toHex(), emailAddress: "admin@admin.com")
                def adminRole = ShiroRole.find {
                    name == InitAuthService.createRoleName(ADMIN)
                }
                adminUser.addToRoles(adminRole)
                adminUser.save()
            } else {
                log.debug "admin user exists, the default admin does not need to be created"
            }
        }
    }

    def initAnonymousUser() {
        ShiroUser.withTransaction {
            def anonymousUser = ShiroUser.find() {
                username == ANONYMOUS
            }
            if (anonymousUser) {
                log.debug "anonymous user found, don't need to create a default one"
            } else {
                anonymousUser = new ShiroUser(
                        username: "anonymous",
                        passwordHash: new Sha256Hash("password").toHex(),
                )
            }
            def hasRoles = anonymousUser.roles
            if (!hasRoles) {
                def anonymousRole = ShiroRole.find {
                    name == InitAuthService.createRoleName(ANONYMOUS)
                }
                anonymousUser.addToRoles(anonymousRole)
                anonymousUser.save()
            }
        }
    }
    /**
     * add all default roles if they don't exist, which include ROLE_ANONYMOUS, ROLE_ADMIN, and ROLE_REPORT_USER
     * @return
     */
    def initDefaultRoles() {
        DEFAULT_ROLES.each { shortRoleName ->
            def roleName = InitAuthService.createRoleName(shortRoleName)
            def roleExists = ShiroRole.findByName(roleName)
            if (!roleExists) {
                createRole(shortRoleName).save()
            }
        }
    }

    def initCryptKey() {
        if (CryptKey.list().size() == 0) {
            def mKey = new CryptKey(
                    encryptKey: UUID.randomUUID().toString()
            )
            mKey.save()
        }
    }

    /**
     * creates a role based on short name.  role <code>foo</code> would become <code>ROLE_FOO</code> and would be saved
     * with permission <code>foo</code>
     * @param type
     * @return
     */
    static createRole(String type) {
        def role = new ShiroRole(
                name: InitAuthService.createRoleName(type)
        )
        role.addToPermissions(type)
    }

    static createRoleName(String type) {
        ROLE + type.toUpperCase()
    }
}

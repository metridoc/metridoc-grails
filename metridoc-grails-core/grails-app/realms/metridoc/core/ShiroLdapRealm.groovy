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

import org.apache.shiro.authc.AccountException
import org.apache.shiro.authc.CredentialsException
import org.apache.shiro.authc.IncorrectCredentialsException
import org.apache.shiro.authc.UnknownAccountException
import org.apache.shiro.authc.UsernamePasswordToken

import javax.naming.AuthenticationException
import javax.naming.Context
import javax.naming.NamingException
import javax.naming.directory.InitialDirContext
import javax.naming.directory.SearchControls

/**
 * Simple realm that authenticates users against an LDAP server.
 */
class ShiroLdapRealm {
    static authTokenClass = UsernamePasswordToken
    static LOCALHOST_LDAP = "ldap://localhost:389/"
    public static final int RECURSIVE_SEARCH_SCOPE = 2
    def grailsApplication
    def roleMappingService
    def ldapOperationsService

    def authenticate(authToken) {
        log.info "Attempting to authenticate ${authToken.username} in LDAP realm..."
        def username = authToken.username
        def password = new String(authToken.password)
        def appConfig = LdapData.list().get(0)
        ldapOperationsService.clearCaches(username)
        // Null username is invalid
        if (username == null) {
            throw new AccountException("Null usernames are not allowed by this realm.")
        }

        // Empty username is invalid
        if (username == "") {
            throw new AccountException("Empty usernames are not allowed by this realm.")
        }

        def ldapUrl = appConfig.server

        def ctx
        log.info "Trying to connect to LDAP server ${ldapUrl} ..."

        // If an exception occurs, log it.
        try {
            ctx = ldapOperationsService.connect()
        }
        catch (Exception e) {
            if (ldapUrl != LOCALHOST_LDAP) {
                log.error "Could not connect to ${ldapUrl}: ${e}"
            }
            def msg = 'Could not connect to LDAP server'
            log.warn msg
            throw new org.apache.shiro.authc.AuthenticationException(msg)
        }

        String fullLdapName = ldapOperationsService.validateThatUserExists(username, ctx)
        ldapOperationsService.validateUserCredentials(fullLdapName, password)

        return username
    }

    def isAdmin(principal) {
        ldapOperationsService.hasRole(principal, "ROLE_ADMIN")
    }

    def hasRole(principal, roleName) {
        ldapOperationsService.hasRole(principal as String, roleName as String)
    }

    def hasAllRoles(principal, roleList) {
        boolean allRoles = true

        if (isAdmin(principal)) {
            return true
        }

        for (role in roleList) {
            if (!ldapOperationsService.hasRole(role)) {
                allRoles = false
            }
        }
        return allRoles
    }
}

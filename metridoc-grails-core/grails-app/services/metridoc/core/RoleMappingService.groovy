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

import javax.naming.Context
import javax.naming.directory.InitialDirContext
import javax.naming.directory.SearchControls
import javax.naming.directory.SearchResult

class RoleMappingService {

    private InitialDirContext _ldapContext

    LdapData getLdapData() {
        def ldapDataList = LdapData.list()
        if (ldapDataList) {
            if (ldapDataList.size() > 1) {
                log.warn("There are multiple ldap settings stored, this should not happen.  Using ${ldapDataList.id}")
            }
        }

        ldapDataList ? ldapDataList.get(0) : null
    }

    List userGroupsAsList(String targetUser) {

        if (!canConnectToLdap) return []

        def searchBase = ldapData.rootDN
        def usernameAttribute = ldapData.getUserSearchFilter()
        def searchScope = 2
        def searchControls = new SearchControls()
        searchControls.setSearchScope(searchScope)

        def ctx = ldapContext
        String filter = "($usernameAttribute=${targetUser})"

        def result = ctx.search(searchBase, filter, searchControls)
        def groups = new ArrayList()
        result.each {SearchResult searchResult ->
            searchResult.getAttributes().get("memberOf").all.each {String group ->
                def getCN = group.split(",")[0].split("CN=")[1]
                groups.add(getCN)
            }
        }
        return groups
    }

    def rolesByGroups(groups) {
        def roles = new ArrayList()
        def query = LdapRoleMapping.where {
            groups.contains(name)
        }.list()

        for (group in query) {
            for (role in group.roles) {
                if (!roles.contains(role.name) && role != null && role?.name != "") {
                    roles.add(role.name)
                }
            }
        }
        return roles
    }

    Set allGroups() {
        if (!canConnectToLdap) return []

        def ldapSettings = ldapData
        def searchBase = ldapSettings.rootDN
        def searchScope = 2
        def searchControls = new SearchControls()
        searchControls.setSearchScope(searchScope)

        def allGroups = [] as Set
        def filter = "(objectclass=group)"
        def result = ldapContext.search(searchBase, filter, searchControls);
        result.each {
            def attrs = it.getAttributes();
            allGroups.add(attrs.get("cn").toString().replace("cn: ", ""));
        }
        return allGroups
    }

    def isValidGroup(groupName) {
        return allGroups().contains(groupName)
    }

    InitialDirContext getLdapContext() {

        if (_ldapContext) {
            return _ldapContext
        }

        def ldapSettings = ldapData
        if (!ldapSettings) return null

        def env = [] as Hashtable
        env[Context.INITIAL_CONTEXT_FACTORY] = "com.sun.jndi.ldap.LdapCtxFactory"
        env[Context.SECURITY_AUTHENTICATION] = "simple"
        env[Context.SECURITY_PRINCIPAL] = ldapSettings.managerDN
        env[Context.SECURITY_CREDENTIALS] = ldapSettings.unencryptedPassword
        env[Context.PROVIDER_URL] = ldapSettings.server

        return new InitialDirContext(env)
    }

    boolean isCanConnectToLdap() {
        try {
            if (ldapContext) {
                return true
            }
        }
        catch (Exception ignored) {
            return false
        }

        return false
    }
}

package metridoc.core

import com.google.common.cache.Cache
import com.google.common.cache.CacheBuilder
import org.apache.shiro.authc.IncorrectCredentialsException
import org.apache.shiro.authc.UnknownAccountException

import javax.naming.AuthenticationException
import javax.naming.Context
import javax.naming.directory.InitialDirContext
import javax.naming.directory.SearchControls
import java.util.concurrent.TimeUnit

@SuppressWarnings("GroovyAssignabilityCheck")
class LdapOperationsService {

    Cache<String, Set<String>> groupsByUserNameCache = CacheBuilder.newBuilder().expireAfterWrite(15, TimeUnit.MINUTES).build()
    Cache<String, Set<String>> mappedRolesByUserNameCache = CacheBuilder.newBuilder().expireAfterWrite(15, TimeUnit.MINUTES).build()

    boolean hasRole(String userName, String role) {
        mappedRolesByUserNameCache.get(userName) {
            Set<String> roles = []
            getGroups(userName).each {groupName ->
                def mapping = LdapRoleMapping.findByName(groupName)
                if (mapping) {
                    mapping.roles.each {shiroRole ->
                        roles << shiroRole.name
                    }
                }
            }

            return roles
        }.contains(role)
    }

    InitialDirContext connect() {
        new InitialDirContext(getEnvFromLdapData() as Hashtable)
    }

    Hashtable getEnvFromLdapData() {
        LdapData ldapData = LdapData.first()

        return getEnvFromLdapData(ldapData.managerDN, ldapData.unencryptedPassword)
    }

    Hashtable getEnvFromLdapData(String userDN, String unencryptedPassword) {
        LdapData ldapData = LdapData.first()
        def env = new Hashtable()

        env[Context.INITIAL_CONTEXT_FACTORY] = "com.sun.jndi.ldap.LdapCtxFactory"
        if (userDN != null) {
            env[Context.SECURITY_PRINCIPAL] = userDN
            if (unencryptedPassword != null) {
                env[Context.SECURITY_CREDENTIALS] = unencryptedPassword
            }
        }
        env[Context.PROVIDER_URL] = ldapData.server

        return env
    }

    String getUserQuery(String username) {
        getUserQuery(username, LdapData.first().userSearchFilter)
    }

    void validateUserCredentials(String userQuery, String unencryptedPassword) {
        try {
            def env = getEnvFromLdapData(userQuery, unencryptedPassword)
            new InitialDirContext(env as Hashtable)
        }
        catch (AuthenticationException ignored) {
            log.info "Invalid password for [$userQuery]"
            throw new IncorrectCredentialsException("Invalid password for user '${userQuery}'")
        }
    }

    protected Set<String> getGroups(String userName) {
        return groupsByUserNameCache.get(userName) {

                InitialDirContext ctx
                try {
                    ctx = connect()
                }
                catch (Exception ignore) {

                }

                Set<String> result = []
                def ldapData = LdapData.first()
                try {
                    def fullName = validateThatUserExists(userName, ctx)
                    SearchControls searchControls = new SearchControls()
                    searchControls.setSearchScope(2)

                    def base = ldapData.groupSearchBase ? "$ldapData.groupSearchBase,$ldapData.rootDN" : "$ldapData.rootDN"
                    def filter = ldapData.groupSearchFilter.replaceAll(/\{0\}/, fullName).replaceAll(/\{1\}/, userName)
                    def searchResult = ctx.search(base, filter, searchControls)
                    while (searchResult.hasMore()) {
                        def next = searchResult.next()
                        result << next.attributes.get("cn").get()
                    }

                } catch (Exception ignored) {
                    //ignore
                }

                return result
        }
    }

    String validateThatUserExists(String userName, InitialDirContext initialDirContext) {
        LdapData data = LdapData.first()
        validateThatUserExists(userName, getUserQuery(userName), data.userSearchBase, data.rootDN, initialDirContext)
    }

    protected static String validateThatUserExists(String username, String filter, String userSearchBase,
                                                   String rootDN, InitialDirContext initialDirContext) throws UnknownAccountException {

        SearchControls searchControls = new SearchControls()

        searchControls.setSearchScope(2)

        def base = "$userSearchBase,$rootDN"
        def result = initialDirContext.search(base, filter, searchControls)
        if (!result.hasMore()) {
            throw new UnknownAccountException("No account found for user [${username}]")
        }

        result.next().nameInNamespace
    }

    protected static String getUserQuery(String username, String filter) {
        String result
        if (filter.contains("{0}")) {
            result = "${filter.replaceAll(/\{0\}/, username)}"
        } else {
            result = "$filter=$username"
        }
        return "($result)"
    }

    void clearCaches(String userName) {
        groupsByUserNameCache.invalidate(userName)
        mappedRolesByUserNameCache.invalidate(userName)
    }
}

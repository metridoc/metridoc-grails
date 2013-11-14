package metridoc.core

import org.apache.shiro.authc.IncorrectCredentialsException
import org.apache.shiro.authc.UnknownAccountException

import javax.naming.AuthenticationException
import javax.naming.Context
import javax.naming.directory.InitialDirContext
import javax.naming.directory.SearchControls

@SuppressWarnings("GroovyAssignabilityCheck")
class LdapOperationsService {

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

    void validateUserCredentials(String userName, String unencryptedPassword) {
        try {
            def env = getEnvFromLdapData(userName, unencryptedPassword)
            new InitialDirContext(env as Hashtable)
        }
        catch (AuthenticationException ignored) {
            log.info "Invalid password for [$userName]"
            throw new IncorrectCredentialsException("Invalid password for user '${userName}'")
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
        }
        else {
            result = "$filter=$username"
        }
        return "($result)"
    }
}

package metridoc.core

import javax.naming.Context
import javax.naming.directory.InitialDirContext

@SuppressWarnings("GroovyAssignabilityCheck")
class LdapOperationsService {

    InitialDirContext connect() {
        new InitialDirContext(getEnvFromLdapData())
    }

    HashMap getEnvFromLdapData() {
        LdapData ldapData = LdapData.first()
        def env = new Hashtable()

        env[Context.INITIAL_CONTEXT_FACTORY] = "com.sun.jndi.ldap.LdapCtxFactory"
        env[Context.SECURITY_PRINCIPAL] = ldapData.managerDN
        env[Context.SECURITY_CREDENTIALS] = ldapData.unencryptedPassword
        env[Context.PROVIDER_URL] = ldapData.server

        return env
    }
}

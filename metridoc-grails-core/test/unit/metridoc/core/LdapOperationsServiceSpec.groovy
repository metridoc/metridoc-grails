package metridoc.core

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification

import javax.naming.Context

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(LdapOperationsService)
@Mock([LdapData, CryptKey])
class LdapOperationsServiceSpec extends Specification {

    void setup() {
        new CryptKey(
                encryptKey: "123123"
        ).save(failOnError: true)
    }

    void "test extracting environment from LdapData"() {
        given:
        def encryptionService = new EncryptionService()
        def ldapData = new LdapData(
                server: "ldap://foo.bar",
                managerDN: "foobarDN",
                rootDN: "foobarRootDN",
                userSearchBase: "userBase",
                userSearchFilter: "foobar",
        )
        encryptionService.encryptLdapData(ldapData, "password")
        ldapData.save(failOnError: true)

        when:
        Hashtable env = service.getEnvFromLdapData()

        then:
        env[Context.INITIAL_CONTEXT_FACTORY] == "com.sun.jndi.ldap.LdapCtxFactory"
        env[Context.SECURITY_PRINCIPAL] == ldapData.managerDN
        env[Context.SECURITY_CREDENTIALS] == ldapData.unencryptedPassword
        env[Context.PROVIDER_URL] == ldapData.server
    }
}

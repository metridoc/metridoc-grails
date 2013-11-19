package metridoc.core

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import org.apache.shiro.authc.UnknownAccountException
import spock.lang.Specification

import javax.naming.Context
import javax.naming.NamingEnumeration
import javax.naming.NamingException
import javax.naming.directory.InitialDirContext
import javax.naming.directory.SearchControls
import javax.naming.directory.SearchResult

@TestFor(LdapOperationsService)
@Mock([LdapData, CryptKey])
class LdapOperationsServiceSpec extends Specification {

    void setup() {
        new CryptKey(
                encryptKey: "123123"
        ).save(failOnError: true)

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
    }

    void "test extracting environment from LdapData"() {
        when:
        def ldapData = LdapData.first()
        Hashtable env = service.getEnvFromLdapData()

        then:
        env[Context.INITIAL_CONTEXT_FACTORY] == "com.sun.jndi.ldap.LdapCtxFactory"
        env[Context.SECURITY_PRINCIPAL] == ldapData.managerDN
        env[Context.SECURITY_CREDENTIALS] == ldapData.unencryptedPassword
        env[Context.PROVIDER_URL] == ldapData.server

        when: "user name is null"
        env = service.getEnvFromLdapData(null, null)

        then:
        2 == env.size()
        !env.containsKey(Context.SECURITY_PRINCIPAL)
        !env.containsKey(Context.SECURITY_CREDENTIALS)

        when: "password is null"
        env = service.getEnvFromLdapData("foo", null)

        then:
        3 == env.size()
        !env.containsKey(Context.SECURITY_CREDENTIALS)
    }

    void "test generating user filter"() {
        expect:
        a == service.getUserQuery(b, c)

        where:
        a              || b     | c
        "(bar=foo)"    || "foo" | "bar"
        "(foobar=foo)" || "foo" | "foobar={0}"
    }

    void "test generating data from stored filter"() {
        expect:
        a == service.getUserQuery(b)

        where:
        a              || b
        "(foobar=bar)" || "bar"
    }

    void "test validating a user existence"() {
        when:
        def result = service.validateThatUserExists("foo", "bar", "foo", "foobar", new InitialDirContextMock())

        then:
        noExceptionThrown()
        "foobar" == result

        when:
        service.validateThatUserExists("base", "bar", "baz", "foobar", new InitialDirContextMock())

        then:
        thrown(UnknownAccountException)

        when:
        result = service.validateThatUserExists("foo", new InitialDirContextMock())

        then:
        noExceptionThrown()
        "foobar" == result
    }
}

class InitialDirContextMock extends InitialDirContext {
    @Override
    NamingEnumeration<SearchResult> search(String name, String filter, SearchControls cons) throws NamingException {
        def trueNamingEnumeration = [
                hasMore: {
                    true
                },
                next: {
                    def result = new SearchResult(null, null, null)
                    result.nameInNamespace = "foobar"

                    return result
                }
        ] as NamingEnumeration

        if ("foo,foobar" == name) {
            return trueNamingEnumeration
        }

        def ldapData = LdapData.first()
        if("${ldapData.userSearchBase},${ldapData.rootDN}" == name) {
            return trueNamingEnumeration
        }

        [
                hasMore: {
                    false
                }
        ] as NamingEnumeration
    }
}

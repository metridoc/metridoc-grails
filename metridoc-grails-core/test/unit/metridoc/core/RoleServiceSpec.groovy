package metridoc.core

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification

@TestFor(RoleService)
@Mock([ShiroUser, ShiroRole, LdapRoleMapping])
class RoleServiceSpec extends Specification {

    void "test attachments to role for deletion"() {
        given:
        ShiroRole notAttached = new ShiroRole(
                name: "notAttached"
        )
        notAttached.save(failOnError: true)

        ShiroUser user = new ShiroUser(
                emailAddress: "foo@bar.com",
                passwordHash: "asdasfdasd",
                username: "blahblah"
        )
        user.save(failOnError: true)

        ShiroRole attached = new ShiroRole(
                name: "attached",
                users: [user]
        )
        attached.save(failOnError: true)

        ShiroRole ldapAttached = new ShiroRole(
                name: "ldapAttached"
        )
        ldapAttached.save(failOnError: true)

        LdapRoleMapping ldapMapping = new LdapRoleMapping(
                name: "ldapMapping",
                roles: [ldapAttached]
        )
        ldapMapping.save(failOnError: true)

        when:
        def result = service.roleAttachedToUsers(notAttached.id)

        then:
        !result

        when:
        result = service.roleAttachedToUsers(attached.id)

        then:
        result

        when:
        result = service.roleAttachedToLdapMapping(ldapAttached.id)

        then:
        result

        when:
        service.deletedRole(notAttached.id)

        then:
        noExceptionThrown()

        when:
        service.deletedRole(attached.id)

        then:
        thrown(AssertionError)

        when:
        service.deletedRole(ldapAttached.id)

        then:
        thrown(AssertionError)
    }
}

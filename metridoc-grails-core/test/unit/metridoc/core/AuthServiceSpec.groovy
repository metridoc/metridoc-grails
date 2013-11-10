package metridoc.core

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import org.apache.shiro.crypto.hash.Sha256Hash
import org.codehaus.groovy.grails.web.servlet.mvc.GrailsParameterMap
import spock.lang.Specification

/**
 * Created with IntelliJ IDEA on 11/8/13
 * @author Tommy Barker
 */
@TestFor(AuthService)
@Mock(ShiroUser)
class AuthServiceSpec extends Specification {

    void "test changing a users details"() {
        when:
        def params = new GrailsParameterMap([:], null)
        params.emailAddress = "foo@bar.com"
        def user = new ShiroUser()
        service.updateUser(user, params)

        then:
        user.emailAddress == "foo@bar.com"

        when: "password is stored in old method with no salting"
        params.changePW = true
        user = new ShiroUser()
        params = new GrailsParameterMap([:], null)
        params.oldPassword = "password123"
        def oldPasswordHash = new Sha256Hash(params.oldPassword).toHex()
        user.passwordHash = oldPasswordHash

        //needed to make valid
        user.username = "foobar"
        user.emailAddress = "foo@bar.com"

        params.password = "newPassword123"
        params.confirm = "newPassword123"
        params.changePW = true
        service.updateUser(user, params)

        then:
        oldPasswordHash != user.passwordHash
        user.passwordHash == new Sha256Hash(user.password, "foobar").toHex()

        when: "password is stored in new method with salting"
        user.passwordHash = new Sha256Hash(params.oldPassword, "foobar").toHex()
        user.hashAgainstOldPassword = true
        service.updateUser(user, params)

        then:
        oldPasswordHash != user.passwordHash
        user.passwordHash == new Sha256Hash(user.password, "foobar").toHex()
    }
}

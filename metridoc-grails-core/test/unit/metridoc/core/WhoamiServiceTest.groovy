package metridoc.core

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import org.apache.shiro.SecurityUtils
import org.junit.Test

/**
 * @author Tommy Barker
 */
@Mock(ShiroRole)
@TestFor(WhoamiService)
class WhoamiServiceTest {

    @Test
    void "test whoami data"() {
        SecurityUtils.metaClass.'static'.getSubject = {
            [
                    principal: "foo",
                    hasRole: {String roleName ->
                        if("ADMIN" == roleName) {
                            return false
                        }

                        return true
                    }
            ]
        }

        ["ADMIN", "USER", "SUPER_USER"].each {
            new ShiroRole(name: it).save(failOnError: true, flush: true)
        }

        def result = service.getUserData([:])
        assert "foo" == result.username
        assert result.roles.contains("USER")
        assert result.roles.contains("SUPER_USER")
        assert !result.roles.contains("ADMIN")
    }
}

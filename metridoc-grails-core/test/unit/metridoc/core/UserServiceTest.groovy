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

import grails.test.mixin.Mock
import org.junit.Before
import org.junit.Test

/**
 * Created by IntelliJ IDEA.
 * User: tbarker
 * Date: 8/6/12
 * Time: 3:13 PM
 */
@Mock([ShiroRole, ShiroUser])
class UserServiceTest {

    def userService = new UserService()

    @Before
    void setupMockData() {
        def anonymous = new ShiroRole(name: UserService.ROLE_ANONYMOUS)
        anonymous.id = 1
        anonymous.save(flush: true)
        def foo = new ShiroRole(name: "ROLE_FOO")
        foo.id = 2
        foo.save(flush: true)
    }

    @Test
    void testAddingRoles() {
        def user = new ShiroUser()
        userService.addRolesToUser(user, ["ROLE_FOO"])
        assert 2 == user.roles.size()
        user.roles.each {
            assert "ROLE_FOO" == it.name || "ROLE_ANONYMOUS" == it.name
        }
    }

    @Test
    void "anonymous is added by default regardless of whether or not any roles are provided"() {
        def user = new ShiroUser()
        userService.addRolesToUser(user, null)
        assert 1 == user.roles.size()

        user.roles.each {
            assert UserService.ROLE_ANONYMOUS == it.name
        }
    }
}

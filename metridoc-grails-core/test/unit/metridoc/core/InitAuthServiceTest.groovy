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
import org.junit.Test

/**
 * Created by IntelliJ IDEA.
 * User: tbarker
 * Date: 7/26/12
 * Time: 1:53 PM
 */
@Mock(ShiroRole)
class InitAuthServiceTest {

    @Test
    void "test creation of role name"() {
        assert "ROLE_FOO" == InitAuthService.createRoleName("foo")
    }

    @Test
    void "test role creation"() {
        ShiroRole role = InitAuthService.createRole("foo")
        assert "ROLE_FOO" == role.name
        assert role.permissions.contains("foo")
        assert 1 == role.permissions.size()
    }

    @Test
    void "test creation of default roles"() {
        def initAuthService = new InitAuthService()
        initAuthService.initDefaultRoles()

        assert 4 == ShiroRole.list().size()
    }
}

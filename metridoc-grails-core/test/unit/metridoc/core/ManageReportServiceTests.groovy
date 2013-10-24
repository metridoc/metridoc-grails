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



import grails.test.mixin.*
import org.codehaus.groovy.grails.commons.DefaultGrailsControllerClass
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(ManageReportService)
class ManageReportServiceTests {

    def roleMaps = [
            user:[
                    "*":["ROLE_ADMIN", "ROLE_REST"],
                    foo:["ROLE_FOO", "ROLE_ADMIN"]
            ],
            bar:[
                    foo:["ROLE_FOO"]
            ]
    ]

    @Test
    void "if a controller is marked as protected or has roles, then it is protected"() {
        def clazz = new DefaultGrailsControllerClass(WhoamiController)
        assert ManageReportService.isControllerGrailsClassProtected(clazz)
    }

    @Test
    void "test normalizing roleMaps"() {
        def roleMap = ManageReportService.getNormalizedRoleMapsHelper(roleMaps)
        assert roleMap.containsKey("user")
        assert roleMap.containsKey("bar")
        assert 2 == roleMap.size()
        checkUserController(roleMap)
    }

    @Test
    void "test retrieving roles based on controller name"() {
        def roles = ManageReportService.getRolesByControllerAndRoleMap("user", roleMaps)
        checkForUserRoles(roles)
        roles = ManageReportService.getRolesByControllerAndRoleMap("foobar", roleMaps)
        assert roles != null
        assert roles.isEmpty()
    }

    void checkUserController(roleMap) {
        def user = roleMap.user
        assert 3 == user.size()
        checkForUserRoles(user)
    }

    void checkForUserRoles(Set<String> user) {
        assert user.contains("ROLE_ADMIN")
        assert user.contains("ROLE_REST")
        assert user.contains("ROLE_FOO")
    }
}

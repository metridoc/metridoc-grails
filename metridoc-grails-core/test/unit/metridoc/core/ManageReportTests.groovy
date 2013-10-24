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
 * Created with IntelliJ IDEA.
 * User: tbarker
 * Date: 1/30/13
 * Time: 1:57 PM
 * To change this template use File | Settings | File Templates.
 */
@Mock([ManageReport, ShiroRole])
class ManageReportTests {

    @Test
    void "nothing happens if no data is stored"() {
        def originalRoles = ["ROLE_ADMIN", "ROLE_REST"]
        def roleMap = [
                "*": originalRoles
        ]

        ManageReport.overrideRoleMap("user", roleMap)
        assert originalRoles == roleMap.get("*")
    }

    @Test
    void "stored info overrides defaults"() {
        def role = new ShiroRole(name: "ROLE_FOO")
        role.save(flush: true)
        new ManageReport(controllerName: "user", isProtected: true, roles: [role]).save(flush: true, failOnError: true)
        def roleMap = [
                "*": ["ROLE_ADMIN", "ROLE_REST"]
        ]
        ManageReport.overrideRoleMap("user", roleMap)
        def actionMap = roleMap.get("*")
        assert 1 == actionMap.size()
        assert "ROLE_FOO" == actionMap.get(0)
    }
}

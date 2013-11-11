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
  *	permissions and limitations under the License.
  */

package metridoc.rid

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import org.codehaus.groovy.grails.web.servlet.mvc.SynchronizerTokensHolder

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestFor(RidAdminDepartmentController)
@Mock(RidDepartment)
class RidAdminDepartmentControllerTests {

    void testInvalidSave() {
        def token = SynchronizerTokensHolder.store(session)
        controller.params[SynchronizerTokensHolder.TOKEN_KEY] = token.generateToken("/ridAdminDepartment/list")
        controller.params[SynchronizerTokensHolder.TOKEN_URI] = "/ridAdminDepartment/list"

        controller.params.inForm = 1
        controller.save()

        assert response.redirectedUrl == '/ridAdminDepartment/list'
        assert flash.message == null
        assert RidDepartment.count() == 0
    }

    void testValidSave() {
        def token = SynchronizerTokensHolder.store(session)
        controller.params[SynchronizerTokensHolder.TOKEN_KEY] = token.generateToken("/ridAdminDepartment/list")
        controller.params[SynchronizerTokensHolder.TOKEN_URI] = "/ridAdminDepartment/list"

        controller.flash.alerts = []
        controller.params.name = "test"
        controller.params.inForm = 1
        controller.save()

        assert response.redirectedUrl == '/ridAdminDepartment/list'
        assert flash.message != null
        assert flash.alerts.size() == 0
        assert RidDepartment.count() == 1

        // Cannot submit repeatedly
        response.reset()
        controller.save()
        assert response.redirectedUrl == '/ridAdminDepartment/list'
        assert flash.alerts.get(0) == "Don't click the create button more than one time to make duplicated submission!"
    }
}

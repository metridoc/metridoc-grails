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
import grails.test.mixin.TestFor

/**
 * Created with IntelliJ IDEA on 4/15/13
 * @author Tommy Barker
 */
@TestFor(ManageReportController)
@Mock(ManageReport)
class ManageReportControllerTests {

    void "test fixing bug where NPE occurs when setting permissions for a controller and the controller has no roles"() {
        new ManageReport(controllerName: "foo").save(failOnError: true)
        controller.initAuthService = [
                init: {
                    //do nothing, just mock it so unit test will run
                }
        ]
        controller.manageReportService = new ManageReportService()
        controller.manageReportService.initAuthService = [
                init: {

                }
        ]
        controller.update("foo") //null pointer exception will occur here if bug exists
    }
}

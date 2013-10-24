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
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(CommonService)
class CommonServiceTests {

    def configMockWithUsername = [
        config: [
                grails: [
                        mail: [
                                username: "foo"
                        ]

                ]
        ]
    ]

    def configMockWith_NO_Username = [
        config: [

        ]
    ]

    @Test
    void "if the property grails_mail_username is not set, then email service is NOT configured"() {
        assert !service.doEmailIsConfigured(configMockWith_NO_Username)
        assert !service.doEmailIsConfigured(configMockWithUsername)
    }
}

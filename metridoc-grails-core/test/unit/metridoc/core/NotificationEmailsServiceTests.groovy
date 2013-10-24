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

import grails.test.mixin.TestFor
import org.junit.Test

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(NotificationEmailsService)
class NotificationEmailsServiceTests {

    @Test
    void "whether mail is enable or not depends on the existence of the property grails_mail"() {
        disableMail()
        assert !service.mailIsEnabled()
        enableMail()
        assert service.mailIsEnabled()
    }

    @Test
    void "mail is sent if mail is enabled, otherwise not"() {
        disableMail()
        def mailHelper = new MailHelper()
        service.mailService = mailHelper
        service.doSendEmail(["foo"] as String[], "foo", "foo")
        assert !mailHelper.callCount
        enableMail()
        service.doSendEmail(["foo"] as String[], "foo", "foo")
        assert 1 == mailHelper.callCount
    }

    @Test
    void "mail is enabled by default by system property"() {
        assert !NotificationEmailsService.emailDisabledFromSystemProperty()
    }

    void enableMail() {
        def grailsApplication = [
                config: [
                        grails: [
                                mail: "mail mock"
                        ]
                ]
        ]
        service.grailsApplication = grailsApplication
    }

    void disableMail() {
        def grailsApplication = [
                config: new ConfigObject()
        ]
        service.grailsApplication = grailsApplication
    }

}

class MailHelper {
    int callCount = 0

    def sendMail(Closure closure) {
        closure.delegate = this
        callCount++
    }

    def to(recipients) {

    }

    def subject(String subject) {

    }

    def body(String body) {

    }
}
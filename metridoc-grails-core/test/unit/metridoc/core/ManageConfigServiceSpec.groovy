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

package metridoc.core

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification

@TestFor(ManageConfigService)
@Mock(NotificationEmails)
class ManageConfigServiceSpec extends Specification {

    void "test flash alerts should fire off if there are any invalid emails"() {
        given:
        def flash = [:]
        flash.alerts = []
        def emails = "not emails"

        when:
        service.updateReportUserEmails(emails, flash)

        then:
        flash.alerts[0] == "[not] is not a valid email"
        flash.alerts[1] == "[emails] is not a valid email"
    }

    void "if emails are valid, they override what was there"() {
        given:
        new NotificationEmails(
                email:"foo@bar.com",
                scope: ManageConfigService.REPORT_ISSUES
        ).save(flush: true, failOnError: true)
        //put one in that will be used
        new NotificationEmails(
                email:"foobar@flip.com",
                scope: ManageConfigService.REPORT_ISSUES
        ).save(flush: true, failOnError: true)
        new NotificationEmails(
                email:"bar@bar.com",
                scope: "notReportIssue"
        ).save(flush: true, failOnError: true)
        def emails = "foobar@flip.com foobar@flop.com"
        def flash = [:]

        when:
        service.updateReportUserEmails(emails, flash)
        String reportIssueEmails = service.reportIssueEmails

        then:
        "bar@bar.com" == NotificationEmails.findByScope("notReportIssue").email
        reportIssueEmails.contains("foobar@flip.com")
        reportIssueEmails.contains("foobar@flop.com")
    }
}

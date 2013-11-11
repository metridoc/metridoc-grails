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

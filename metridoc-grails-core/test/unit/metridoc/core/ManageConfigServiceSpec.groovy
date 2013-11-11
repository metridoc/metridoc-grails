package metridoc.core

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import org.apache.shiro.mgt.RememberMeManager
import org.apache.shiro.web.mgt.CookieRememberMeManager
import org.apache.shiro.web.servlet.Cookie
import spock.lang.Specification

@TestFor(ManageConfigService)
@Mock([NotificationEmails, RememberCookieAge])
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

    void "test setting the cookie age"() {
        given:
        boolean maxAgeCalled = false
        service.shiroRememberMeManager = [
                getCookie: {
                    [
                        setMaxAge:{
                            maxAgeCalled = true
                        }
                    ] as Cookie
                }
        ] as CookieRememberMeManager

        when: "cookie age is negative"
        service.updateRememberMeCookieAge(-10)

        then: "previous cookie age is used"
        3600 == RememberCookieAge.instance.ageInSeconds
        !maxAgeCalled

        when:
        service.updateRememberMeCookieAge(10)

        then:
        10 == RememberCookieAge.instance.ageInSeconds
        maxAgeCalled
    }
}

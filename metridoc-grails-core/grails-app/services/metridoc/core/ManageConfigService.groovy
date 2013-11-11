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

import org.apache.commons.lang.StringUtils

class ManageConfigService {

    public static final String REPORT_ISSUES = "ReportIssues"
    def shiroRememberMeManager

    def updateReportUserEmails(String reportIssueEmails, Map flash) {
        if(!reportIssueEmails) return
        def validEmails = []
        log.debug "loading emails [$reportIssueEmails]"
        boolean allAreValid = true

        reportIssueEmails.split(/\s/).each {
            log.debug "loading email [$it]"
            def email = NotificationEmails.findByEmail(it)
            if(email == null) {
                email = new NotificationEmails (
                        email: it,
                        scope: REPORT_ISSUES
                )
                if(!email.save()) {
                    flash.alerts << "[$it] is not a valid email"
                    allAreValid = false
                }
                else {
                    validEmails << email.email
                }
            }
            else {
                validEmails << email.email
            }
        }

        if (allAreValid) {
            removeNoLongerUsedEmails(validEmails as Set)
        }
    }

    protected static void removeNoLongerUsedEmails(Set<String> validEmails) {

        NotificationEmails.findByScope(ManageConfigService.REPORT_ISSUES).each {
            if(!validEmails.contains(it.email)) {
                it.delete()
            }
        }
    }

    String getReportIssueEmails() {
        StringBuilder emails = new StringBuilder()

        NotificationEmails.findAllByScope(REPORT_ISSUES).each {
            emails.append(it.email)
            emails.append(",")
        }

        String response
        if (emails) {
            response = StringUtils.chop(emails.toString())
        }

        log.debug "using emails [$response] for reporting errors issues"
        return response
    }

    def updateRememberMeCookieAge(int cookieAge) {
        if (cookieAge >= 0) {
            RememberCookieAge instance = RememberCookieAge.instance
            instance.ageInSeconds = cookieAge
            if(instance.save()) {
                shiroRememberMeManager.getCookie().setMaxAge(cookieAge)
            }
        }
    }
}

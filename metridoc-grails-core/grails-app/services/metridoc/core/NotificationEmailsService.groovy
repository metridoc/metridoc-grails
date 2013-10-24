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

class NotificationEmailsService {
    def mailService
    def grailsApplication

    static boolean emailDisabledFromSystemProperty() {
        def emailDisabled = System.getProperty("metridoc.email.disabled", "false")
        Boolean.valueOf(emailDisabled)
    }

    boolean mailIsEnabled() {
        grailsApplication.config.grails.mail
    }

    void sendEmail(String scope, String message, String subject) {
        if (!emailDisabledFromSystemProperty()) {
            def emails = NotificationEmails.getEmailsByScope(scope)
            if (emails) {
                doSendEmail(emasils as String[], message, subject)
            } else {
                log.warn "Could not send the email with subject [$subject] since there were no emails"
            }
        } else {
            log.info "email notification with subject [$subject] not sent since emails are disabled"
        }
    }

    private doSendEmail(String[] recipients, String message, String subject) {
        if (mailIsEnabled()) {
            mailService.sendMail {
                to recipients
                subject subject
                body message
            }
        } else {
            log.warn("Could not send the email with subject [$subject] since mail is not set up correctly")
        }
    }
}

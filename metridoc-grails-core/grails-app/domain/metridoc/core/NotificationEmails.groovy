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

import groovy.transform.ToString
import org.apache.commons.lang.text.StrBuilder

/**
 * contains all emails related to various operations in metridoc such as job failures
 */
class NotificationEmails {
    /**
     * scope of the email... ie what kind of notification is this
     */
    String scope
    String email


    static constraints = {
        email email: true, blank: false, unique: ['scope'], maxSize: 75
        scope blank: false, maxSize: 75
    }

    static List<NotificationEmails> convertToEmails(String scope, String emails) {
        def emailList = convertEmailsToList(emails)
        def result = []
        emailList.each {
            result << new NotificationEmails(email: it, scope: scope)
        }

        return result
    }

    /**
     *
     * @param scope scope of the notification
     * @param emails emails separated by whitespace to store
     * @return all failed emails
     */
    static String[] storeEmails(String scope, String emails) {
        def failures = []
        NotificationEmails.withNewTransaction {
            convertToEmails(scope, emails).each {
                def alreadyStored = NotificationEmails.findByScopeAndEmail(scope, it.email)
                if (!alreadyStored) {
                    if (!it.save()) {
                        failures << it.email
                    }
                }
            }

            def emailList = convertEmailsToList(emails) as Set
            NotificationEmails.findAllByScope(scope).each {
                if (!emailList.contains(it.email)) {
                    NotificationEmails.get(it.id).delete()
                }
            }
        }

        return failures as String[]
    }

    static List<String> getEmailsByScope(String scope) {
        List<String> result = []
        def notificationEmails = NotificationEmails.findAllByScope(scope)
        if (notificationEmails) {
            notificationEmails.each {
                result << it.email
            }
        }
        return result
    }

    private static String[] convertEmailsToList(String emails) {
        if (emails) {
            def trimmed = emails.trim()
            if (trimmed) {
                return trimmed.split(/\s+/)
            }
        }

        return [] as String[]
    }

    private static String convertListToString(List<NotificationEmails> emails) {
        def result = new StrBuilder()
        emails.each {
            result.appendln(it.email)
        }

        return result.toString()
    }

    @Override
    public String toString() {
        return "NotificationEmails{" +
                "id=" + id +
                ", scope='" + scope + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    boolean equals(o) {
        if (this.is(o)) return true
        if (!(o instanceof NotificationEmails)) return false

        NotificationEmails that = (NotificationEmails) o

        if (email != that.email) return false
        if (scope != that.scope) return false

        return true
    }

    int hashCode() {
        int result
        result = (scope != null ? scope.hashCode() : 0)
        result = 31 * result + (email != null ? email.hashCode() : 0)
        return result
    }
}

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
import org.apache.commons.lang.SystemUtils
import org.junit.Test

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(NotificationEmails)
class NotificationEmailsTests {

    def emailsAsString = """
        foo@fam.com bar@blam.com
        foobar@bar.com
        """

    @Test
    void "given a string of emails separated by white space, they can be converted into a list"() {
        def emailList = NotificationEmails.convertEmailsToList(emailsAsString)
        assert "foo@fam.com" == emailList[0]
        assert "bar@blam.com" == emailList[1]
        assert "foobar@bar.com" == emailList[2]
    }

    @Test
    void "blank or nulll emails will return an empty list if conwverted to a list"() {
        def emailList = NotificationEmails.convertEmailsToList("")
        assert 0 == emailList.size()
        emailList = NotificationEmails.convertEmailsToList(null)
        assert 0 == emailList.size()
        emailList = NotificationEmails.convertEmailsToList("   ")
        assert 0 == emailList.size()
    }

    @Test
    void "given a list of email notifications, emails can be converted to a string of new line delimited emails"() {
        def notifications = []
        notifications << new NotificationEmails(email: "foo@blam.com", scope: "foo")
        notifications << new NotificationEmails(email: "bar@blam.com", scope: "foo")
        notifications << new NotificationEmails(email: "baz@blam.com", scope: "foo")

        def newLine = SystemUtils.LINE_SEPARATOR
        assert "foo@blam.com${newLine}bar@blam.com${newLine}baz@blam.com${newLine}" as String ==
                NotificationEmails.convertListToString(notifications)
    }

    @Test
    void "test retrieving emails"() {
        new NotificationEmails(scope: "foo", email: "foo@foo.com").save(flush: true, failOnError: true)
        new NotificationEmails(scope: "foo", email: "bar@foo.com").save(flush: true, failOnError: true)

        def emails = NotificationEmails.getEmailsByScope("foo")
        assert 2 == emails.size()
        assert emails.contains("foo@foo.com")
        assert emails.contains("bar@foo.com")
    }

    @Test
    void "test storing emails as string"() {
        NotificationEmails.storeEmails("foo", emailsAsString)
        def emails = NotificationEmails.getEmailsByScope("foo")
        assert 3 == emails.size()
    }

    @Test
    void "storing emails by scope overwrites previously stored information"() {
        NotificationEmails.storeEmails("foo", emailsAsString)
        NotificationEmails.storeEmails("foo", "foo@blam.com")
        assert 1 == NotificationEmails.findAllByScope("foo").size()
        assert "foo@blam.com" == NotificationEmails.findAllByScope("foo").get(0).email
    }

    @Test
    void "convert to emails test"() {
        assert 3 == NotificationEmails.convertToEmails("foo", emailsAsString).size()
    }

    @Test
    void "if emails is null or empty string, then all emails for the provided scope are deleted"() {
        new NotificationEmails(email: "blah@blah.com", scope: "blah").save(flush: true)
        new NotificationEmails(email: "blah@blah.com", scope: "foo").save(flush: true)
        assert 2 == NotificationEmails.count()
        assert NotificationEmails.findByScope("blah")
        NotificationEmails.storeEmails("blah", "  \n ")
        assert !NotificationEmails.findByScope("blah")
        assert NotificationEmails.findByScope("foo")
        NotificationEmails.storeEmails("foo", null)
        assert !NotificationEmails.findByScope("foo")
    }

    @Test
    void "can't store emails if a scope is not provided"() {
        try {
            NotificationEmails.storeEmails(null, "blah")
            assert false: "failure should have occurred"
        } catch (AssertionError assertionError) {

        }
    }

    @Test
    void "test toString"() {
        assert new NotificationEmails().toString().contains("id=")
    }
}

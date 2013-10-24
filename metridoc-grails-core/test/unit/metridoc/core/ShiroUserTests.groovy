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
import org.apache.commons.lang.StringUtils
import org.apache.shiro.crypto.hash.Sha256Hash
import org.junit.Before
import org.junit.Test

import static metridoc.core.ShiroUser.*

@Mock([ShiroUser, ShiroRole])
class ShiroUserTests {

    static final blahEmailAddress = "blah@foo.com"
    def flash = [:]
    private ShiroUser seededUser
    private ShiroUser notSeededUser

    @Before
    void "prime the database"() {
        def adminRole = new ShiroRole(name: "ROLE_ADMIN")
        adminRole.save(flush: true)
        seededUser = new ShiroUser(username: "foobar", emailAddress: blahEmailAddress, passwordHash: new Sha256Hash("password",
                "foobar").toHex())
        seededUser.password = "password"
        seededUser.save(flush: true, failOnError: true)
        notSeededUser = new ShiroUser(username: "admin", emailAddress: "admin@admin.com",
                passwordHash: new Sha256Hash("password").toHex(), roles: [adminRole])
        notSeededUser.password = "password"
        notSeededUser.save(flush: true)
    }

    @Test
    void "test salting if not salted"() {
        def notSaltedUser = new ShiroUser()
        notSaltedUser.with {
            username = "foo"
            passwordHash = new Sha256Hash("password").toHex()
            saltIfNotSalted("password")
        }
        assert notSaltedUser.passwordHash == new Sha256Hash("password", "foo").toHex()
    }

    @Test
    void "test old password matching"() {
        def foo = new ShiroUser(passwordHash: new Sha256Hash("password").toHex(), username: "foo")
        foo.oldPassword = "password"
        assert foo.oldPasswordMatch()
        foo.passwordHash = new Sha256Hash("password", "foo").toHex()
        assert foo.oldPasswordMatch()
        foo.oldPassword = "noMatch"
        assert !foo.oldPasswordMatch()
    }

    @Test
    void "test basic seeding"() {
        assert seededUser.isSalted()
        assert !notSeededUser.isSalted()
    }

    @Test
    void "test password matching"() {
        assert seededUser.passwordsMatch()
        assert notSeededUser.passwordsMatch()
        seededUser.password = "noMatch"
        assert !seededUser.passwordsMatch()
    }

    @Test
    void "user admin must always have a role admin"() {
        def admin = ShiroUser.findByUsername("admin")
        admin.roles = []
        assert !admin.validate()
        addAlertForAllErrors(admin, flash)
        assert ADMIN_MUST_HAVE_ROLE_ADMIN == flash.alert
    }

    @Test
    void "test that flash messages are set on email errors"() {
        checkForError(passwordHash: "blah", username: "barfoo", emailAddress: blahEmailAddress, "emailAddress", EMAIL_ERROR(blahEmailAddress))
        checkForError(passwordHash: "blah", username: "barfoo", "emailAddress", FIELD_CANNOT_BE_NULL_OR_BLANK("email"))
        checkForError(passwordHash: "blah", username: "barfoo", emailAddress: StringUtils.EMPTY, "emailAddress", FIELD_CANNOT_BE_NULL_OR_BLANK("email"))
        checkForError(passwordHash: "blah", username: "barfoo", emailAddress: "foobar", "emailAddress", NOT_A_VALID_EMAIL("foobar"))
    }

    @Test
    void "test flash messages for username errors"() {
        def fooEmail = "foo@foo.com"
        checkForError(
                emailAddress: fooEmail,
                passwordHash: "blahblah",
                "username",
                FIELD_CANNOT_BE_NULL_OR_BLANK("username")
        )

        checkForError(
                username: StringUtils.EMPTY,
                emailAddress: fooEmail,
                passwordHash: "blahblah",
                "username",
                FIELD_CANNOT_BE_NULL_OR_BLANK("username")
        )

        checkForError(
                username: "foobar",
                emailAddress: fooEmail,
                passwordHash: "blahblah",
                "username",
                USERNAME_IS_NOT_UNIQUE
        )
    }


    @Test
    void "test that flash message are set on bad passwords"() {
        def fooEmail = "foo@foo.com"
        checkForError(validatePasswords: true,
                username: "barfoo",
                emailAddress: fooEmail,
                passwordHash: "blahblah",
                "passwordHash",
                FIELD_CANNOT_BE_NULL_OR_BLANK("password"))

        checkForError(validatePasswords: true,
                username: "barfoo",
                emailAddress: fooEmail,
                password: StringUtils.EMPTY,
                passwordHash: "blahblah",
                "passwordHash",
                FIELD_CANNOT_BE_NULL_OR_BLANK("password"))

        checkForError(validatePasswords: true,
                username: "barfoo",
                emailAddress: fooEmail,
                password: "bar",
                passwordHash: "blahblah",
                "passwordHash",
                PASSWORD_MISMATCH)

        checkForError(validatePasswords: true,
                oldPassword: "blahblah",
                username: "barfoo",
                emailAddress: fooEmail,
                password: "foobar",
                passwordHash: "blahblah",
                "passwordHash",
                OLD_PASSWORD_MISMATCH)

        checkForError(validatePasswords: true,
                oldPassword: "blahblah",
                username: "barfoo",
                emailAddress: fooEmail,
                password: "foobar",
                confirm: "barfoo",
                passwordHash: new Sha256Hash("blahblah").toHex(),
                "passwordHash",
                CONFIRM_MISMATCH)
    }

    static void checkForError(LinkedHashMap userParams, String field, String errorMessage) {
        Map flash = [:]
        ShiroUser user = new ShiroUser()
        userParams.each {
            user."${it.key}" = it.value
        }
        assert !user.validate()
        assert user.errors.getFieldError(field)
        addAlertForAllErrors(user, flash)
        assert errorMessage == flash.alert
    }
}

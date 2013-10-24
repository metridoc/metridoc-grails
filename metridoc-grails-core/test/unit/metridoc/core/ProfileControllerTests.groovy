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
import grails.test.mixin.TestFor
import org.apache.shiro.crypto.hash.Sha256Hash
import org.junit.Test

@TestFor(ProfileController)
@Mock(ShiroUser)
class ProfileControllerTests {

    ShiroUser user = new ShiroUser(emailAddress: "foo@bar.com", passwordHash: "123abc", username: "foo")

    @Test
    void "if changePW not checked then only the user email are changed"() {
        assert "foo@bar.com" == user.emailAddress
        ProfileController.updateUserInfo(user, [id: user.id, emailAddress: "bar@bar.com", username: "bar"]) //user name cannot change
        assert "bar@bar.com" == user.emailAddress
    }

    @Test
    void "if changePW is checked then user email and password are changed"() {
        assert "foo@bar.com" == user.emailAddress
        ProfileController.updateUserInfoAndPassword(user, [id: user.id, emailAddress: "bar@bar.com", username: "bar", password: "barPassword"]) //user name cannot change
        assert "bar@bar.com" == user.emailAddress
        assert new Sha256Hash("barPassword").toHex() == user.passwordHash
    }
}

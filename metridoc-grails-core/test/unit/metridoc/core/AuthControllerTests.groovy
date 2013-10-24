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

@SuppressWarnings("GroovyAccessibility")
@TestFor(AuthController)
class AuthControllerTests {

    void loadParameters() {
        params << [username: "joe", targetUri: "http://foo.com"]
    }

    @Test
    void "testing the return values of getModel"() {
        loadParameters()
        def model = controller.getModel()
        assert !model.rememberMe
        assert "joe" == model.username
        assert "http://foo.com" == model.targetUri
        controller.params << [rememberMe: true]
        model = controller.getModel()
        assert model.rememberMe
    }

    @Test
    void "test that login returns the same values as model"() {
        loadParameters()
        assert controller.getModel() == controller.login()
    }

    @Test
    void "test that index chains to login"() {
        controller.index()
        assert response.redirectedUrl == '/auth/login'
    }

    @Test
    void "unauthorized returns nothing"() {
        assert null == controller.unauthorized()
    }
}

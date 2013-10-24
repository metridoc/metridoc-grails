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

@TestFor(AuthService)
class AuthServiceTests {

    @Test
    void "do basic id check"() {
        def id = service.addResetLink()
        assert service.canReset(id)
    }

    @Test
    void "id should be removed after checked"() {
        def id = service.addResetLink()
        assert service.canReset(id)
        assert service.dateById.size() == 0
    }

    @Test
    void "test that ids expire"() {
        def id = service.addResetLink()
        def twentyMinutes = 1000 * 60 * 20
        def now = new Date().time + twentyMinutes
        //noinspection GroovyAccessibility
        assert !service.canDoReset(id, now)
    }

    @Test
    void "cant reset if the id is not in the cache"() {
        assert !service.canReset(123)
    }
}

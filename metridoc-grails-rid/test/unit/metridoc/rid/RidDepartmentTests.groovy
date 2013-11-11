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

package metridoc.rid

import grails.test.mixin.TestFor
import org.junit.Test

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(RidDepartment)
class RidDepartmentTests {

    @Test
    void testBootStrap() {
        mockForConstraintsTests(RidDepartment)

        List<String> dpt = Arrays.asList("Biology", "Cinema Studies", "History", "Philosophy", "...");
        for (String i in dpt) {
            def da = new RidDepartment(name: i)
            da.save()
            if (da.hasErrors()) println da.errors
        }

        assert RidDepartment.list().size() > 0
    }
}

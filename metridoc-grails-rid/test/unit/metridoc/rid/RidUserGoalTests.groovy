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
@TestFor(RidUserGoal)
class RidUserGoalTests {

    @Test
    void testBootStrap() {
        mockForConstraintsTests(RidUserGoal)

        List<String> uGoal = Arrays.asList("Senior Thesis", "Master Thesis", "Dissertation",
                "Independent Research");
        for (String i in uGoal) {
            def p = new RidUserGoal(name: i)
            p.save()
            if (p.hasErrors()) println p.errors
        }

        assert RidUserGoal.list().size() > 0
    }
}

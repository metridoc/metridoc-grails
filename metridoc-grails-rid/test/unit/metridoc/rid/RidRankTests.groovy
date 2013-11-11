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

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(RidRank)
class RidRankTests {

    void testBootStrap() {
        mockForConstraintsTests(RidRank)

        List<String> rank = Arrays.asList("Undergrad student", "Grad student", "PhD/PostDoc",
                "Clinical: intern, resident, fellow", "Clinical: other", "Faculty", "Alumni", "Stuff",
                "Other (please indicate)")
        for (String i in rank) {
            def c = new RidRank(name: i)
            c.save()
            if (c.hasErrors()) println c.errors
        }

        assert RidRank.list().size() > 0
    }
}

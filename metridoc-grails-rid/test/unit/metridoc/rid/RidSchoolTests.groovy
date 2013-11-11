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
@TestFor(RidSchool)
class RidSchoolTests {

    void testBootStrap() {
        mockForConstraintsTests(RidSchool)

        List<String> schools = Arrays.asList("SAS", "SEAS", "Wharton", "GSE", "Vet", "Nursing", "Med",
                "Dental", "SP2", "Design", "UPHS", "CHOP", "Annenberg", "Law", "Penn Other (please indicate)",
                "Outside Entity (please indicate)")
        for (String i in schools) {
            def e = new RidSchool(name: i, inForm: 1)
            e.save()
            if (e.hasErrors()) println e.errors
        }

        def lst = RidSchool.list()
        assert lst.size() > 0
        for (RidSchool i in lst)
            assert i.inForm == 1
    }
}

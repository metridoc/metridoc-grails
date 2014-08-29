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
import grails.validation.ValidationException
import org.junit.Test

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(RidConsTransaction)
class RidConsTransactionTests {

    @Test
    void testConstraints() {
        mockForConstraintsTests(RidConsTransaction)

        def ridTransaction = new RidConsTransaction(interactOccurrences: 1111)
        assert !ridTransaction.validate()

        def ridTransaction2 = new RidConsTransaction(interactOccurrences: 11111)
        assert !ridTransaction2.validate()
        assert "max" == ridTransaction2.errors["interactOccurrences"]
    }

    @Test
    void testDatabase() {
        mockForConstraintsTests(RidConsTransaction)

        def ridTransaction = new RidConsTransaction(interactOccurrences: 211110)
        def ridTransaction2 = new RidConsTransaction(interactOccurrences: 61110)
        try {
            ridTransaction.save(failOnError: true)
            ridTransaction2.save(failOnError: true)
        }
        catch (ValidationException e) {
            println(e.message)
        }
        assert 0 == RidConsTransaction.list().size()
    }
}

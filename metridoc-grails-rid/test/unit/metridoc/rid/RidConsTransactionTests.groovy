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

        def ridTransaction = new RidConsTransaction(interactOccurrences: 20)
        assert !ridTransaction.validate()

        def ridTransaction2 = new RidConsTransaction(interactOccurrences: 60)
        assert !ridTransaction2.validate()
        assert "max" == ridTransaction2.errors["interactOccurrences"]
    }

    @Test
    void testDatabase() {
        mockForConstraintsTests(RidConsTransaction)

        def ridTransaction = new RidConsTransaction(interactOccurrences: 20)
        def ridTransaction2 = new RidConsTransaction(interactOccurrences: 60)
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

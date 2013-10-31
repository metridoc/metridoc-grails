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

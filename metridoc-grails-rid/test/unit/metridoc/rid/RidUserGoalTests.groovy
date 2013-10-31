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

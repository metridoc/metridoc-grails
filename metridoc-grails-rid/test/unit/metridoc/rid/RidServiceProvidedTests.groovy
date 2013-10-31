package metridoc.rid

import grails.test.mixin.TestFor

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(RidServiceProvided)
class RidServiceProvidedTests {

    void testBootStrap() {
        mockForConstraintsTests(RidServiceProvided)

        List<String> sProvided = Arrays.asList("Course design", "Research assistance",
                "Acquisition/Collections", "Copyright assistance for author", "...")
        for (String i in sProvided) {
            def s = new RidServiceProvided(name: i)
            s.save()
            if (s.hasErrors()) println s.errors
        }

        assert RidServiceProvided.list().size() > 0
    }
}

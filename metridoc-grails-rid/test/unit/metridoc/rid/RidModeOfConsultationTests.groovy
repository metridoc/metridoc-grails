package metridoc.rid

import grails.test.mixin.TestFor

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(RidModeOfConsultation)
class RidModeOfConsultationTests {

    void testBootStrap() {
        mockForConstraintsTests(RidModeOfConsultation)

        List<String> cMode = Arrays.asList("Email", "Phone", "Chat", "In person(in library)",
                "In person(outside library)", "Conferencing software")
        for (String i in cMode) {
            def c = new RidModeOfConsultation(name: i)
            c.save()
            if (c.hasErrors()) println c.errors
        }
        assert RidModeOfConsultation.list().size() > 0
    }
}

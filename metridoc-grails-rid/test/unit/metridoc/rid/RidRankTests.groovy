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

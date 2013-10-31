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

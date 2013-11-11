package metridoc.rid

class RidConsTransactionTemplate extends RidConsTransactionBase {

    static belongsTo = [department: RidDepartment,
            courseSponsor: RidCourseSponsor,
            userGoal: RidUserGoal,
            modeOfConsultation: RidModeOfConsultation,
            rank: RidRank,
            serviceProvided: RidServiceProvided,
            school: RidSchool,
            ridLibraryUnit: RidLibraryUnit]

    static transients = ['otherRank', 'otherUserGoal', 'otherModeOfConsultation', 'otherSchool',
            'otherCourseSponsor', 'otherService']

    //Records the owner/creator of this template
    //Leaves it blank if this is NOT a template
    String templateOwner = ""

    static constraints = {
        // STATEMENT OF WORK
        userQuestion(blank: true, nullable: true, maxSize: 500)
        interactOccurrences(nullable: true, min: 0, max: 50)
        prepTime(nullable: true, min: 0)
        eventLength(nullable: true, min: 0)
        notes(blank: true, nullable: true, maxSize: 500)
        staffPennkey(blank: false, nullable: false, maxSize: 100)

        // ROLES
        facultySponsor(blank: true, nullable: true, maxSize: 100)
        courseName(blank: true, nullable: true, maxSize: 100)
        courseNumber(blank: true, nullable: true, maxSize: 100)
        otherRank(blank: true, nullable: true, maxSize: 50)
        otherUserGoal(blank: true, nullable: true, maxSize: 50)
        otherModeOfConsultation(blank: true, nullable: true, maxSize: 50)
        school(nullable: true)
        otherSchool(blank: true, nullable: true, maxSize: 50)
        department(nullable: true)
        userName(blank: true, nullable: true, maxSize: 50)
        courseSponsor(nullable: true)
        otherCourseSponsor(blank: true, nullable: true, maxSize: 50)

        // DESCRIPTION
        serviceProvided(nullable: true)
        otherService(blank: true, nullable: true, maxSize: 100)
        modeOfConsultation(nullable: true)
        userGoal(nullable: true)
        ridLibraryUnit(nullable: true)

    }

}

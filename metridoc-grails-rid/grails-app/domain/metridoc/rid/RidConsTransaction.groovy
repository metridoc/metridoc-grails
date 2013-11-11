package metridoc.rid

class RidConsTransaction extends RidConsTransactionBase {

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

    String spreadsheetName

    static constraints = {
        // STATEMENT OF WORK
        userQuestion(blank: true, nullable: true, maxSize: 500)
        interactOccurrences(nullable: false, min: 0, max: 50)
        prepTime(nullable: false, min: 0)
        eventLength(nullable: false, min: 0)
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
        serviceProvided(nullable: false)
        otherService(blank: true, nullable: true, maxSize: 100)
        modeOfConsultation(nullable: false)
        userGoal(nullable: true)
        ridLibraryUnit(nullable: false)
        spreadsheetName(nullable: true, blank: true)
    }
}

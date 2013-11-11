package metridoc.rid

class RidInsTransaction extends RidInsTransactionBase {

    static belongsTo = [
            department: RidDepartment,
//            rank: RidRank,
            school: RidSchool,
            ridLibraryUnit: RidLibraryUnit,
            location: RidLocation,
            sessionType: RidSessionType,
            audience: RidAudience,
            instructionalMaterials: RidInstructionalMaterials]

    static transients = ['otherSchool', 'otherLocation',
            'otherSessionType', 'otherAudience', 'otherInstructionalMaterials']

    String spreadsheetName

    static constraints = {
        // STATEMENT OF WORK
        prepTime(nullable: false, min: 0)
        eventLength(nullable: false, min: 0)
        notes(blank: true, nullable: true, maxSize: 500)
        sessionDescription(blank: true, nullable: true, maxSize: 500)
        instructorPennkey(blank: false, nullable: false, maxSize: 100)
        coInstructorPennkey(blank: true, nullable: true, maxSize: 100)
        sequenceName(blank: true, nullable: true, maxSize: 100)
        sequenceUnit(validator: { value ->
            return ((!value) || (value > 0)) }, nullable: true)
        location(blank: false, nullable: false)
        otherLocation(blank: true, nullable: true, maxSize: 50)

        // ROLES
        facultySponsor(blank: true, nullable: true, maxSize: 100)
        courseName(blank: true, nullable: true, maxSize: 100)
        courseNumber(blank: true, nullable: true, maxSize: 100)
        //otherRank(blank: true, nullable: true, maxSize: 50)
        school(nullable: true)
        otherSchool(blank: true, nullable: true, maxSize: 50)
        department(nullable: true)
        //userName(blank: true, nullable: true, maxSize: 50)
        requestor(blank: true, nullable: true, maxSize: 50)

        // DESCRIPTION
        ridLibraryUnit(nullable: false)
        attendanceTotal(blank: false, nullable: false, min: 0)
        spreadsheetName(nullable: true, blank: true)
        sessionType(nullable: false, blank: false)
        otherSessionType(nullable: true, blank: true)
        audience(nullable: true, blank: true)
        otherAudience(nullable: true, blank: true)
        instructionalMaterials(blank: true, nullable: true, maxSize: 50)
        otherInstructionalMaterials(blank: true, nullable: true, maxSize: 50)
    }
}

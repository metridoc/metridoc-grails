package metridoc.rid

class RidInsTransactionTemplate extends RidInsTransactionBase {

    static belongsTo = [department: RidDepartment,
            //rank: RidRank,
            school: RidSchool,
            ridLibraryUnit: RidLibraryUnit,
            location: RidLocation,
            sessionType: RidSessionType,
            audience: RidAudience,
            instructionalMaterials: RidInstructionalMaterials]

    static transients = [/*'otherRank',*/ 'otherUser', 'otherSchool', 'otherLocation', 'otherSessionType',
            'otherSessionType', 'otherAudience', 'otherInstructionalMaterials']

    //Records the owner/creator of this template
    //Leaves it blank if this is NOT a template
    String templateOwner = ""

    static constraints = {
        // STATEMENT OF WORK
        prepTime(nullable: true, min: 0)
        eventLength(nullable: true, min: 0)
        notes(blank: true, nullable: true, maxSize: 500)
        sessionDescription(blank: true, nullable: true, maxSize: 500)
        instructorPennkey(blank: false, nullable: false, maxSize: 100)
        coInstructorPennkey(blank: true, nullable: true, maxSize: 100)
        sequenceName(blank: true, nullable: true, maxSize: 100)
        sequenceUnit(validator: { value ->
            return ((!value) || (value > 0)) }, nullable: true)
        location(blank: true, nullable: true)
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
        ridLibraryUnit(nullable: true)
        attendanceTotal(blank: true, nullable: true, min: 0)
        sessionType(nullable: true, blank: true)
        otherSessionType(nullable: true, blank: true)
        audience(nullable: true, blank: true)
        otherAudience(nullable: true, blank: true)
        instructionalMaterials(blank: true, nullable: true, maxSize: 50)
        otherInstructionalMaterials(blank: true, nullable: true, maxSize: 50)
    }

}

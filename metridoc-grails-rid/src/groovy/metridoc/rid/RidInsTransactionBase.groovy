package metridoc.rid

abstract class RidInsTransactionBase {

    // statement of work
    Integer prepTime = 0
    Integer eventLength = 1
    Integer attendanceTotal = 1
    String notes
    String instructorPennkey
    String coInstructorPennkey
    String sequenceName
    Integer sequenceUnit
    String otherSessionType
    String otherAudience
    String sessionDescription
    String otherLocation
    String otherInstructionalMaterials

    // roles
    //String userName
    String facultySponsor
    String courseName
    String courseNumber
    //String otherRank
    String otherSchool
    String requestor

    // Calendar dateOfInstruction = Calendar.getInstance()
    Date dateOfInstruction = new Date()

}

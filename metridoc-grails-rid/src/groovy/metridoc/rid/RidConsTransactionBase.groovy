package metridoc.rid

abstract class RidConsTransactionBase {

    // statement of work
    String userQuestion
    Integer interactOccurrences = 1
    Integer prepTime = 0
    Integer eventLength = 1
    String notes
    String staffPennkey

    // roles
    String userName
    String facultySponsor
    String courseName
    String courseNumber
    String otherRank
    String otherUserGoal
    String otherModeOfConsultation
    String otherSchool
    String otherCourseSponsor

    // description
    String otherService
    // Calendar dateOfConsultation = Calendar.getInstance()
    Date dateOfConsultation = new Date()

    String toString() {
        String userQ = new String(userQuestion)
        if (userQ != null && userQ.length() > 32)
            userQ = userQ.substring(0, 32) + "..."
        return "ID: ${id}; userQuestion: ${userQ}"
    }
}

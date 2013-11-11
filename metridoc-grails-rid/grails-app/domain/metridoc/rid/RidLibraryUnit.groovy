package metridoc.rid

class RidLibraryUnit {

    static hasMany = [userGoal: RidUserGoal,
            modeOfConsultation: RidModeOfConsultation,
            serviceProvided: RidServiceProvided,
            sessionType: RidSessionType,
            ridConsTransaction: RidConsTransaction,
            ridInsTransaction: RidInsTransaction]

    String name

    String toString() {
        return "${name}"
    }

    static constraints = {
        name(blank: false, nullable: false, unique: true)
    }
}

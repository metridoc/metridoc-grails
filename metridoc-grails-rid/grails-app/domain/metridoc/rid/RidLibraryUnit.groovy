package metridoc.rid

class RidLibraryUnit {

    static hasMany = [userGoal: RidUserGoal,
            modeOfConsultation: RidModeOfConsultation,
            serviceProvided: RidServiceProvided,
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

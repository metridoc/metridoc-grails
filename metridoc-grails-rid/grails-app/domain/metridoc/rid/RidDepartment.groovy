package metridoc.rid

class RidDepartment {

    static hasMany = [ridConsTransaction: RidConsTransaction,
            ridInsTransaction: RidInsTransaction]
    String name
    String fullName

    String toString() {
        return "${name}"
    }

    static constraints = {
        name(blank: false, nullable: false, unique: true)
        fullName(blank: true, nullable: true)
        ridConsTransaction(nullable: true)
        ridInsTransaction(nullable: true)
    }
}

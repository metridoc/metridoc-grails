package metridoc.rid

class RidRank {

    static hasMany = [ridConsTransaction: RidConsTransaction,
            ridInsTransaction: RidInsTransaction]

    String name
    Integer inForm = 0

    String toString() {
        return "${name}"
    }

    static constraints = {
        name(blank: false, nullable: false, unique: true)
        inForm(nullable: false, inList: [0, 1, 2])
        ridConsTransaction(nullable: true)
        ridInsTransaction(nullable: true)
    }
}

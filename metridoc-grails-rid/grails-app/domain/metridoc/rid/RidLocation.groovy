package metridoc.rid

class RidLocation {

    static hasMany = [ridInsTransaction: RidInsTransaction]

    String name
    Integer inForm = 0

    String toString() {
        return "${name}"
    }

    static constraints = {
        name(blank: false, nullable: false, unique: true)
        inForm(nullable: false, inList: [0, 1, 2])
        ridInsTransaction(nullable: true)
    }
}

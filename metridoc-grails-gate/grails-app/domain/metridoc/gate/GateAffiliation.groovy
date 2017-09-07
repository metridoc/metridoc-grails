package metridoc.gate

class GateAffiliation {

    static hasMany = [gateEntryRecord: GateEntryRecord]

    String name
    Integer id

    String toString() {
        return "${name}"
    }

    static constraints = {
        name(blank: false, nullable: false)
        id(blank: false, nullable: false)
        gateEntryRecord(nullable: true)
    }
}
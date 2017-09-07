package metridoc.gate

class GateEntryRecord {

    static belongsTo = [
    	gateAffiliation: GateAffiliation,
    	gateCenter: GateCenter,
    	gateDepartment: GateDepartment,
    	gateDoor: GateDoor,
    	gateUSC: GateUSC
    ]

    Date date;
    String time;
    Integer door;
    Integer affiliation;
    Integer center;
    Integer department;
    Integer usc;

    static constraints = {
        date(blank: false, nullable: false)
        time(blank: false, nullable: false)
        door(blank: false, nullable: false)
        affiliation(blank: false, nullable: false)
        usc(blank: false, nullable: false)
        center(blank: true, nullable: true)
        department(blank: true, nullable: true)
    }
}
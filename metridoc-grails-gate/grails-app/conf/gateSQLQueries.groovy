gateSQLQueries{
	getRecordsByAffiliation = "
		SELECT gd.door_name, ga.affiliation_name, COUNT(*)
		FROM gate_entry_record ger
		LEFT JOIN gate_door gd
		ON gd.door_id = ger.door
		LEFT JOIN gate_affiliation ga
		ON ga.affiliation_id = ger.affiliation
		WHERE ger.entry_datetime BETWEEN {start_datetime} AND {end_datetime}
		GROUP BY gd.door_name, ga.affiliation_name;
	"

	getRecordsByCenter = "
		SELECT gd.door_name, gc.center_name, COUNT(*)
		FROM gate_entry_record ger
		LEFT JOIN gate_door gd
		ON gd.door_id = ger.door
		LEFT JOIN gate_center gc
		ON gc.center_id = ger.center
		WHERE ger.entry_datetime BETWEEN {start_datetime} AND {end_datetime}
		GROUP BY gd.door_name, gc.center_name;
	"

	getRecordsByDepartment = "
		SELECT gd.door_name, gde.department_name, COUNT(*)
		FROM gate_entry_record ger
		LEFT JOIN gate_door gd
		ON gd.door_id = ger.door
		LEFT JOIN gate_department gde
		ON gde.department_id = ger.department
		WHERE ger.entry_datetime BETWEEN {start_datetime} AND {end_datetime}
		GROUP BY gd.door_name, gde.department_name;
	"

	getRecordsByUSC = "
		SELECT gd.door_name, gu.USC_name, COUNT(*)
		FROM gate_entry_record ger
		LEFT JOIN gate_door gd
		ON gd.door_id = ger.door
		LEFT JOIN gate_USC gu
		ON gu.USC_id = ger.USC
		WHERE ger.entry_datetime BETWEEN {start_datetime} AND {end_datetime}
		GROUP BY gd.door_name, gu.USC_name;
	"

	getAllRecords = "
		SELECT ger.entry_datetime, gd.door_name, ga.affiliation_name, gc.center_name, gu.USC_name, gde.department_name
		FROM gate_entry_record ger
		LEFT JOIN gate_door gd
		ON gd.door_id = ger.door
		LEFT JOIN gate_affiliation ga
		ON ga.affiliation_id = ger.affiliation
		LEFT JOIN gate_center gc
		ON gc.center_id = ger.center
		LEFT JOIN gate_USC gu
		ON gu.USC_id = ger.USC
		LEFT JOIN gate_department gde
		ON gde.department_id = ger.department
		WHERE ger.entry_datetime BETWEEN {start_datetime} AND {end_datetime};
	"

	getAllDoors = "SELECT * FROM gate_door;"

	getAllAffiliations = "SELECT * FROM gate_affiliation;"

	getAllCenters = "SELECT * FROM gate_center;"

	getAllDepartments = "SELECT * FROM gate_department;"

	getAllUSCs = "SELECT * FROM gate_USC;"

	insertNewDoor = "INSERT INTO gate_door (door_id, door_name)
                     VALUES ({door_id}, {door_name});"

    insertNewAffiliation = "INSERT INTO gate_affiliation (affiliation_id, affiliation_name)
    						VALUES ({affiliation_id}, {affiliation_name});"

    insertNewCenter = "INSERT INTO gate_center (center_id, center_name)
                       VALUES ({center_id}, {center_name});"

    insertNewDepartment = "INSERT INTO gate_department (department_id, department_name)
                           VALUES ({department_id}, {department_name});"

    insertNewUSCs = "INSERT INTO gate_USC (USC_id, USC_name)
                     VALUES ({USC_id}, {USC_name});"

    insertNewEntry = "INSERT INTO gate_entry_record (entry_datetime, door, affiliation, center, USC, department)
    				  VALUES ({entry_datetime}, {door}, {affiliation}, {center}, {USC}, {department});"
}
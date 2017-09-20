gateSQLQueries{
	getRecordsByAffiliation = '''
		SELECT gd.door_name, ga.affiliation_name, COUNT(*)
		FROM gate_entry_record ger
		LEFT JOIN gate_door gd
		ON gd.door_id = ger.door
		LEFT JOIN gate_affiliation ga
		ON ga.affiliation_id = ger.affiliation
		WHERE ger.entry_datetime BETWEEN ? AND ?
		GROUP BY gd.door_name, ga.affiliation_name;
	'''

	getRecordsByCenter = '''
		SELECT gd.door_name, gc.center_name, COUNT(*)
		FROM gate_entry_record ger
		LEFT JOIN gate_door gd
		ON gd.door_id = ger.door
		LEFT JOIN gate_center gc
		ON gc.center_id = ger.center
		WHERE ger.entry_datetime BETWEEN ? AND ?
		GROUP BY gd.door_name, gc.center_name;
	'''

	getRecordsByDepartment = '''
		SELECT gd.door_name, gde.department_name, COUNT(*)
		FROM gate_entry_record ger
		LEFT JOIN gate_door gd
		ON gd.door_id = ger.door
		LEFT JOIN gate_department gde
		ON gde.department_id = ger.department
		WHERE ger.entry_datetime BETWEEN ? AND ?
		GROUP BY gd.door_name, gde.department_name'''

	getRecordsByUSC = '''
		SELECT gd.door_name, gu.USC_name, COUNT(*)
		FROM gate_entry_record ger
		LEFT JOIN gate_door gd
		ON gd.door_id = ger.door
		LEFT JOIN gate_USC gu
		ON gu.USC_id = ger.USC
		WHERE ger.entry_datetime BETWEEN ? AND ?
		GROUP BY gd.door_name, gu.USC_name'''

	getAllRecords = '''
		SELECT gate_entry_record.entry_datetime, gate_door.door_name, gate_affiliation.affiliation_name, gate_center.center_name, gate_USC.USC_name, gate_department.department_name
		FROM gate_entry_record
		LEFT JOIN gate_door
		ON gate_door.door_id = gate_entry_record.door
		LEFT JOIN gate_affiliation
		ON gate_affiliation.affiliation_id = gate_entry_record.affiliation
		LEFT JOIN gate_center
		ON gate_center.center_id = gate_entry_record.center
		LEFT JOIN gate_USC
		ON gate_USC.USC_id = gate_entry_record.USC
		LEFT JOIN gate_department
		ON gate_department.department_id = gate_entry_record.department
		WHERE gate_entry_record.entry_datetime BETWEEN ? AND ?'''

	getAllDoors = '''SELECT door_id AS id, door_name AS name FROM gate_door;'''

	getAllAffiliations = '''SELECT affiliation_id AS id, affiliation_name AS name FROM gate_affiliation;'''

	getAllCenters = '''SELECT center_id AS id, center_name AS name FROM gate_center;'''

	getAllDepartments = '''SELECT department_id AS id, department_name AS name FROM gate_department;'''

	getAllUSCs = '''SELECT USC_id AS id, USC_name AS name FROM gate_USC;'''
}
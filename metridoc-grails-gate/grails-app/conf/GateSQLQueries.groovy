gateSQLQueries{
	getRecordsByAffiliation = '''
		SELECT gd.door_name, ga.affiliation_name, COUNT(*)
		FROM gate_entry_record ger
		LEFT JOIN gate_door gd
		ON gd.door_id = ger.door
		LEFT JOIN gate_affiliation ga
		ON ga.affiliation_id = ger.affiliation
		WHERE ger.entry_datetime BETWEEN {start_datetime} AND {end_datetime}
		GROUP BY gd.door_name, ga.affiliation_name;
	'''

	getRecordsByCenter = '''
		SELECT gd.door_name, gc.center_name, COUNT(*)
		FROM gate_entry_record ger
		LEFT JOIN gate_door gd
		ON gd.door_id = ger.door
		LEFT JOIN gate_center gc
		ON gc.center_id = ger.center
		WHERE ger.entry_datetime BETWEEN {start_datetime} AND {end_datetime}
		GROUP BY gd.door_name, gc.center_name;
	'''

	getRecordsByDepartment = '''
		SELECT gd.door_name, gde.department_name, COUNT(*)
		FROM gate_entry_record ger
		LEFT JOIN gate_door gd
		ON gd.door_id = ger.door
		LEFT JOIN gate_department gde
		ON gde.department_id = ger.department
		WHERE ger.entry_datetime BETWEEN {start_datetime} AND {end_datetime}
		GROUP BY gd.door_name, gde.department_name;
	'''

	getRecordsByUSC = '''
		SELECT gd.door_name, gu.USC_name, COUNT(*)
		FROM gate_entry_record ger
		LEFT JOIN gate_door gd
		ON gd.door_id = ger.door
		LEFT JOIN gate_USC gu
		ON gu.USC_id = ger.USC
		WHERE ger.entry_datetime BETWEEN {start_datetime} AND {end_datetime}
		GROUP BY gd.door_name, gu.USC_name;
	'''

	getAllRecords = '''
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
	'''

	getAllDoors = '''SELECT door_id AS id, door_name AS name FROM gate_door;'''

	getAllAffiliations = '''SELECT affiliation_id AS id, affiliation_name AS name FROM gate_affiliation;'''

	getAllCenters = '''SELECT center_id AS id, center_name AS name FROM gate_center;'''

	getAllDepartments = '''SELECT department_id AS id, department_name AS name FROM gate_department;'''

	getAllUSCs = '''SELECT USC_id AS id, USC_name AS name FROM gate_USC;'''
}
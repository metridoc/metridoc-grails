gateSQLQueries{
	getCountBody = ''' FROM gate_entry_record
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

	selectByAffiliation = '''SELECT gate_door.short_name AS door_name, IFNULL(gate_affiliation.affiliation_name, "Total") AS affiliation_name, COUNT(*) AS count'''

	groupByAffiliation = ''' GROUP BY gate_door.short_name, gate_affiliation.affiliation_name WITH ROLLUP'''

	selectByCenter = '''SELECT gate_door.short_name AS door_name, IFNULL(gate_center.center_name, "Total") AS center_name, COUNT(*) AS count'''

	groupByCenter = ''' GROUP BY gate_door.short_name, gate_center.center_name WITH ROLLUP'''

	selectByDepartment = '''SELECT gate_door.short_name AS door_name, IFNULL(gate_department.department_name, "Total") AS department_name, COUNT(*) AS count'''

	groupByDepartment = ''' GROUP BY gate_door.short_name, gate_department.department_name WITH ROLLUP'''

	selectByUSC = '''SELECT gate_door.short_name AS door_name, IFNULL(gate_USC.usc_name, "Total") AS usc_name, COUNT(*) AS count'''

	groupByUSC = ''' GROUP BY gate_door.short_name, gate_USC.USC_name WITH ROLLUP'''

	selectAll = '''SELECT gate_entry_record.entry_datetime, gate_door.short_name AS door_name, gate_affiliation.affiliation_name, gate_center.center_name, gate_USC.USC_name, gate_department.department_name'''

	getAllDoors = '''SELECT door_id AS id, short_name AS name FROM gate_door;'''

	getAllAffiliations = '''SELECT affiliation_id AS id, affiliation_name AS name FROM gate_affiliation;'''

	getAllCenters = '''SELECT center_id AS id, center_name AS name FROM gate_center;'''

	getAllDepartments = '''SELECT department_id AS id, department_name AS name FROM gate_department;'''

	getAllUSCs = '''SELECT USC_id AS id, USC_name AS name FROM gate_USC;'''
}
package metridoc.gate

import groovy.sql.Sql
import org.slf4j.LoggerFactory

import javax.sql.DataSource
import java.sql.ResultSet
import java.text.SimpleDateFormat
import java.text.DateFormat

class gateService {
	DataSource dataSource;
	def grailsApplication;

	private getSql() {
		return new Sql(dataSource);
	}

	ConfigObject getConfig() {
        grailsApplication.config
    }

    def getAllDoors() {
    	Sql sql = getSql();
    	return sql.rows(config.gateSQLQueries.getAllDoors);
    }

    def getAllAffiliations() {
    	Sql sql = getSql();
    	return sql.rows(config.gateSQLQueries.getAllAffiliations);	
    }

    def getAllCenters() {
    	Sql sql = getSql();
    	return sql.rows(config.gateSQLQueries.getAllCenters);
    }

    def getAllDepartments() {
    	Sql sql = getSql();
    	return sql.rows(config.gateSQLQueries.getAllDepartments);
    }

    def getAllUSCs() {
    	Sql sql = getSql();
    	return sql.rows(config.gateSQLQueries.getAllUSCs);
    }

    def query(Map params) {
    	def sqlParams = [];

    	Map timeParams = formatTime(params);

    	String startDatetime = timeParams.startDatetime;
    	String endDatetime = timeParams.endDatetime;

    	sqlParams.push(startDatetime);
    	sqlParams.push(endDatetime);

    	Map clauses = prepareQueryClauses(params, sqlParams);
    	String sqlQueryBody = config.gateSQLQueries.getCountBody;
    	clauses.each{ k, v -> 
    		if(k != 'sqlParams'){
    			sqlQueryBody += v 
    		}
    	};

    	Sql sql = getSql();
    	Map completeQueries = completeQueryBody(sqlQueryBody);
    	return [
    		startDatetime: startDatetime,
    		endDatetime: endDatetime,
    		countByAffiliation: sql.rows(completeQueries.affiliationQuery, sqlParams),
    		countByCenter: sql.rows(completeQueries.centerQuery, sqlParams),
    		countByUSC: sql.rows(completeQueries.uscQuery, sqlParams)
    	];
    }

    def completeQueryBody(sqlQueryBody){
    	return [
    		affiliationQuery: config.gateSQLQueries.selectByAffiliation + sqlQueryBody + config.gateSQLQueries.groupByAffiliation,
    		centerQuery: config.gateSQLQueries.selectByCenter + sqlQueryBody + config.gateSQLQueries.groupByCenter,
    		uscQuery: config.gateSQLQueries.selectByUSC + sqlQueryBody + config.gateSQLQueries.groupByUSC,
    		departmentQuery: config.gateSQLQueries.selectByDepartment + sqlQueryBody + config.gateSQLQueries.groupByDepartment
    	]
    }

    def prepareQueryClauses(Map params, sqlParams){
        String doorClause = andClause('gate_door', params.gateDoorSearch, sqlParams);
        String centerClause = andClause('gate_center', params.gateCenterSearch, sqlParams);
        String affiliationClause = andClause('gate_affiliation', params.gateAffiliationSearch, sqlParams);
        String departmentClause = andClause('gate_department', params.gateDepartmentSearch, sqlParams);
        String uscClause = andClause('gate_USC', params.gateUSCSearch, sqlParams);

        return [
        	doorClause: doorClause,
        	centerClause: centerClause,
        	affiliationClause : affiliationClause,
        	departmentClause: departmentClause,
        	uscClause: uscClause,
        	sqlParams: sqlParams
        ];
    }

    def andClause(tableName, value, sqlParams){
    	if(value != '0'){
    		sqlParams.push(value);
    		return " AND " + tableName + "." + tableName.substring(5) + "_name = ?";
    	}else{
    		return "";
    	}
    }

    def formatTime(Map params) {
    	Date startDate = new SimpleDateFormat("MM/dd/yyyy").parse(params.dateOfEntryRecord_start);
        Date endDate = new SimpleDateFormat("MM/dd/yyyy").parse(params.dateOfEntryRecord_end);
        DateFormat targetDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedStartDate = targetDateFormat.format(startDate);
        String formattedEndDate = targetDateFormat.format(endDate);

        DateFormat targetTimeFormat = new SimpleDateFormat('HH:mm');
        String formattedStartTime = "";
        String formattedEndTime = "";
        if(params.timeOfEntryRecord_start){
        	Date startTime = new SimpleDateFormat('HH:mm').parse(params.timeOfEntryRecord_start);
        	formattedStartTime = targetTimeFormat.format(startTime);
        }else{
        	formattedStartTime = "00:00";
        }

        if(params.timeOfEntryRecord_end){
	        Date endTime = new SimpleDateFormat('HH:mm').parse(params.timeOfEntryRecord_end);
	        formattedEndTime = targetTimeFormat.format(endTime);
        }else{
        	formattedEndTime = "00:00";
        }

        String startDatetime = formattedStartDate + " " + formattedStartTime;
        String endDatetime = formattedEndDate + " " + formattedEndTime;

        return [startDatetime:startDatetime, endDatetime:endDatetime];
    }

}
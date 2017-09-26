package metridoc.gate

import groovy.sql.Sql
import org.slf4j.LoggerFactory
import org.apache.poi.ss.usermodel.*
import org.apache.poi.ss.util.CellReference
import org.codehaus.groovy.grails.io.support.ClassPathResource
import org.codehaus.groovy.grails.web.servlet.FlashScope
import org.springframework.web.multipart.MultipartFile

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


        String doorClause = andClause('gate_door', checkAndConvertToList(params.gateDoorSearch), sqlParams);
        String centerClause = andClause('gate_center', checkAndConvertToList(params.gateCenterSearch), sqlParams);
        String affiliationClause = andClause('gate_affiliation', checkAndConvertToList(params.gateAffiliationSearch), sqlParams);
        String departmentClause = andClause('gate_department', checkAndConvertToList(params.gateDepartmentSearch), sqlParams);
        String uscClause = andClause('gate_USC', checkAndConvertToList(params.gateUSCSearch), sqlParams);

        return [
        	doorClause: doorClause,
        	centerClause: centerClause,
        	affiliationClause : affiliationClause,
        	departmentClause: departmentClause,
        	uscClause: uscClause,
        	sqlParams: sqlParams
        ];
    }

    def checkAndConvertToList(val){
    	if (!val.class.isArray()){
    		return [val];
    	}
    	return val;
    }

    def andClause(tableName, value, sqlParams){
    	if(value[0] != '0'){
    		if(value.size() > 1){
    			def clause = " AND (";
    			for(def i = 0; i < value.size(); i++){
    				sqlParams.push(value[i]);
    				clause += tableName + "." + tableName.substring(5) + "_name = ?";
    				if(i != value.size()-1){
    					clause += " OR ";
					}
    			}
    			return clause + ")";
			}else{
	    		sqlParams.push(value[0]);
	    		return " AND " + tableName + "." + tableName.substring(5) + "_name = ?";
			}
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

    def exportAsFile(data){
    	ClassPathResource resource = new ClassPathResource('spreadsheet/Transaction_List.xlsx')
        Workbook wb = WorkbookFactory.create(resource.getFile().newInputStream())
        Sheet sheet = wb.getSheetAt(0)
        wb.removeSheetAt(1)
        wb.removeSheetAt(1)

        CellStyle red_bold = wb.createCellStyle()
        Font ft = wb.createFont()
        ft.boldweight = Font.BOLDWEIGHT_BOLD
        ft.color = Font.COLOR_RED
        red_bold.font = ft

        int rowNum = 0
       
       	def doorHeaders = [];
       	data.allDoorNames.each{
       		doorHeaders.push(it.name);
       	}
        def consHeaders = [""] + doorHeaders + ["Total"];
        def headerLength = data.allDoorNames.size() + 1;
        Row row = sheet.createRow(rowNum++)
        def cellnum = 0
        for (h in consHeaders){
            row.createCell(cellnum).setCellValue(h)
            cellnum++
        }

        row = sheet.createRow(rowNum++);
        row.createCell(0).setCellValue("Affiliation Summary");
        rowNum = populateCells(data.allAffiliationData, row, rowNum, sheet, headerLength);

        rowNum ++;
        row = sheet.createRow(rowNum++);
        row.createCell(0).setCellValue("Center Summary");
        rowNum = populateCells(data.allCenterData, row, rowNum, sheet, headerLength);

        rowNum ++;
        row = sheet.createRow(rowNum++);
        row.createCell(0).setCellValue("USC Summary");
        row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
        populateCells(data.allUSCData, row, rowNum, sheet, headerLength);

        return wb;
    }

    def populateCells(data, row, rowNum, sheet, headerLength) {
		for (item in data) {
            if (item.key != "Total"){
            	row = sheet.createRow(rowNum++)
            	row.createCell(0).setCellValue(item.key);
            }else{
            	continue;
            }

            for (int i = 0; i<headerLength; i++){
            	row.createCell(i+1).setCellValue(item.value[i]);
            }
            row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
            for (int c = 1; c < headerLength + 1; c++)
                row.getCell(c).setCellType(Cell.CELL_TYPE_NUMERIC);
        }
        row = sheet.createRow(rowNum++);
        row.createCell(0).setCellValue("Total");
        for (int i = 0; i<headerLength; i++){
        	row.createCell(i+1).setCellValue(data["Total"].value[i]);
        }
        row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
        for (int c = 1; c < headerLength + 1; c++)
            row.getCell(c).setCellType(Cell.CELL_TYPE_NUMERIC);

        return rowNum;
    }

}
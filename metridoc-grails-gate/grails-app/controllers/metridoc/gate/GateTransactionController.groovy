package metridoc.gate

import grails.converters.JSON
import org.apache.poi.openxml4j.exceptions.InvalidFormatException
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook
import org.apache.shiro.SecurityUtils
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.multipart.commons.CommonsMultipartFile
import java.util.regex.Matcher
import java.util.regex.Pattern

import java.text.SimpleDateFormat

class GateTransactionController {
	//This application's title and description on front page
	static homePage = [title: "Library Gate Traffic Information",
                       description: "Look up data of number and time of people entering libraries"]

    static boolean isProtected = true;

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"];

    def gateService;

    private static downloadableData = [:];

    def index() {
    	def allDoorNames = createNameArray(gateService.getAllDoors());
    	def allAffiliationNames = createNameArray(gateService.getAllAffiliations());
    	def allCenterNames = createNameArray(gateService.getAllCenters());
    	def allDepartmentNames = createNameArray(gateService.getAllDepartments());
    	def allUSCNames = createNameArray(gateService.getAllUSCs());
    	render(view: "index", 
    		   model: [
    		     allDoorNames: allDoorNames, 
    		     allAffiliationNames: allAffiliationNames,
    		     allCenterNames: allCenterNames,
    		     allDepartmentNames: allDepartmentNames,
    		     allUSCNames: allUSCNames]);
	    session.setAttribute("prev", new String("index"));
	}

	def back() {
		redirect(action: 'index');
	}

	def query() {
		def result = gateService.query(params);
		def allDoorNames = gateService.getAllDoors();

		downloadableData = [
			   	 allDoorNames : allDoorNames,
			     startDatetime: result.startDatetime,
			   	 endDatetime: result.endDatetime,
			     allAffiliationData: processTableData(result.countByAffiliation, allDoorNames, 'affiliation_name'),
			     allCenterData: processTableData(result.countByCenter, allDoorNames, 'center_name'),
			     allUSCData: processTableData(result.countByUSC, allDoorNames, 'usc_name')];

		render(view: "searchResult",
			   model: downloadableData);
	}

	def createNameArray(objArray){
		//extract names from [id, name] array
		def nameArray = [];
		objArray.each{obj->
			nameArray.push(obj.name);
		}
		return nameArray.sort();
	}



	def processTableData(rawData, allDoorNames, key) {
		Map returnMap = [:];
    	if(rawData.size() == 0){
			returnMap.put("Total", [0] * (allDoorNames.size()+1));
		}else{
	    	rawData.each{ it->
				if(!returnMap.containsKey(it[key])){
					returnMap.put(it[key], [0] * (allDoorNames.size()+1));
				}
				
				allDoorNames.each{ door ->
					if(door.name == it.door_name){
						def pos = door.id;
						(returnMap.get(it[key]))[pos] = it.count;
					}
				}
			}
			returnMap.each{ k, v -> 
	    		v[allDoorNames.size()] = v.sum();
	    	};
		}

		return returnMap;
	}

	def export() {
        Workbook wb = gateService.exportAsFile(downloadableData);

        try {
            response.setContentType('application/vnd.openxmlformats-officedocument.spreadsheetml.sheet')
            response.setHeader("Content-disposition",
                    "filename=metridocGateSearchResult" + new Date().format("MMddyyyy-HHmmss") + ".xlsx")
            wb.write(response.outputStream) // Performing a binary stream copy
        } catch (Exception e) {
            flash.alerts << e.message
        }
		render(view: "searchResult",
			   model: downloadableData);
        
    }
}
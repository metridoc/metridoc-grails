package metridoc.gate

import grails.converters.JSON
import org.apache.poi.openxml4j.exceptions.InvalidFormatException
import org.apache.poi.ss.usermodel.Workbook
import org.apache.shiro.SecurityUtils
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.multipart.commons.CommonsMultipartFile
import java.util.regex.Matcher
import java.util.regex.Pattern

import java.text.SimpleDateFormat

class GateTransactionController {
	static homePage = [title: "Library Gate Traffic Information",
                       description: "Look up data of number and time of people entering libraries"]

    static boolean isProtected = true;

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"];

    def gateService;

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

	def query() {
		def result = gateService.query(params);
		def allDoorNames = gateService.getAllDoors();
		Map allAffiliationData = [:];
		result.countByAffiliation.each{ it->
			if(!allAffiliationData.containsKey(it.affiliation_name)){
				allAffiliationData.put(it.affiliation_name, [0] * allDoorNames.size()+1);
			}
			
			allDoorNames.each{ door ->
				if(door.name == it.door_name){
					def pos = door.id;
					(allAffiliationData.get(it.affiliation_name))[pos] = it.count;
				}
			}
		}
		
		allAffiliationData.each{ k, v -> 
    		v[allDoorNames.size()] = v.sum() - 1;
    	};

		print(result.countByAffiliation);
		print("111111111111111111111111111111111111111111111111111111111111111111111111111");
		print(allAffiliationData);
		render(view: "searchResult",
			   model: [
			   	 allDoorNames : allDoorNames,
			     startDatetime: result.startDatetime,
			   	 endDatetime: result.endDatetime,
			     countByAffiliation: result.countByAffiliation,
			     countByCenter: result.countByCenter,
			     countByUSC: result.countByUSC]);
	}

	def createNameArray(objArray){
		def nameArray = [];
		objArray.each{obj->
			nameArray.push(obj.name);
		}
		return nameArray.sort();
	}
}
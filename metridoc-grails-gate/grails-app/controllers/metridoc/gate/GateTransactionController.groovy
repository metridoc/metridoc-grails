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
		render(view: "searchResult",
			   model: [result: result]);
	}

	def createNameArray(objArray){
		def nameArray = [];
		objArray.each{obj->
			nameArray.push(obj.name);
		}
		return nameArray.sort();
	}
}
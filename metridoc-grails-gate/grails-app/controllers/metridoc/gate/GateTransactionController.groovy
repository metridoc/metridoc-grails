package metridoc.gate

import grails.converters.JSON
import org.apache.poi.openxml4j.exceptions.InvalidFormatException
import org.apache.poi.ss.usermodel.Workbook
import org.apache.shiro.SecurityUtils
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.web.multipart.MultipartFile
import java.util.regex.Matcher
import java.util.regex.Pattern

import java.text.SimpleDateFormat

class GateTransactionController {
	static homePage = [title: "Library Gate Traffic Information",
                       description: "Import/Export and look up data of number and time of people entering libraries"]

    static boolean isProtected = true

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
    	render(view:'/gateTransaction/index')
    }
}
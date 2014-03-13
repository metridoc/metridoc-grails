package metridoc.funds

import org.codehaus.groovy.grails.web.context.ServletContextHolder
import org.codehaus.groovy.grails.web.servlet.GrailsApplicationAttributes
import org.springframework.context.ApplicationContext

import java.text.ParseException
import java.text.SimpleDateFormat

import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet

/**
*
* @author Narine Ghochikyan
*
*/
class FundsUtil {
	
	private static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MMM-yyyy");//"yyyy-MM-dd");

	static String getMessage(String code){
        ApplicationContext applicationContext = (ApplicationContext) ServletContextHolder.getServletContext().getAttribute(GrailsApplicationAttributes.APPLICATION_CONTEXT);
        return applicationContext.getMessage(code, null, null);
	}
	static String getMonthString(int month){
		return getMessage("month."+month);
	}
	
	public static Date getDate(String dateStr, String pattern){
		Date date = null;
		try{
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			date = sdf.parse(dateStr);
		}catch(ParseException ex){
		}
		return date;
	}
	
//	static getDateRangeData(startDate, endDate){
//		def (startYear, startMonth) = getYearMonth(startDate);
//		def (endYear, endMonth) = getYearMonth(endDate);	
//	}
//	
	static getYearMonth(Date date){
		def startCal = Calendar.getInstance();
		startCal.setTime(date);
		
		return [ startCal.get(Calendar.YEAR), 
				 startCal.get(Calendar.MONTH)]
	}
	
	public static Date getDateEndOfDay(date){
		def calendar = Calendar.getInstance()
		calendar.setTime(date);
		calendar.set(Calendar.HOUR, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		return calendar.getTime()
	}
	
	static String getDateRangeString(startDate, endDate){
		return DATE_FORMAT.format(startDate) + " to " + DATE_FORMAT.format(endDate)
	}
//	static Date getDate(String strDate){
//		return DATE_FORMAT.parse(strDate)
//	}
	static void createHeader(String[] headers, Sheet sh, int rowIndex, int startColumnIndex){
		Row row = sh.createRow(rowIndex);
		for (int i = 0; i < headers.length; i++) {
			Cell cell = row.createCell(startColumnIndex + i);
				cell.setCellValue(headers[i]);
		}
	}
	
	static getValue(Object obj){
		if(obj instanceof Date){
			return DATE_FORMAT.format((Date)obj);
		}else{
			return obj != null ? obj : "";
		}
	}
}

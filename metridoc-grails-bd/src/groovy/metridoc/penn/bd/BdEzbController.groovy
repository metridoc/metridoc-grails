/*
  *Copyright 2013 Trustees of the University of Pennsylvania. Licensed under the
  *	Educational Community License, Version 2.0 (the "License"); you may
  *	not use this file except in compliance with the License. You may
  *	obtain a copy of the License at
  *
  *http://www.osedu.org/licenses/ECL-2.0
  *
  *	Unless required by applicable law or agreed to in writing,
  *	software distributed under the License is distributed on an "AS IS"
  *	BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
  *	or implied. See the License for the specific language governing
  *	permissions and limitations under the License.  */

package metridoc.penn.bd

import edu.upennlib.collmanagement.BucketService
import edu.upennlib.collmanagement.CallNoService.CallNoCounts
import grails.util.Holders
import groovy.sql.GroovyRowResult
import metridoc.penn.util.DateUtil
import org.codehaus.groovy.grails.validation.Validateable

class BdEzbController {
	def grailsApplication
    def borrowDirectService
	def serviceKey = BorrowDirectService.BD_SERVICE_KEY;
    private static final BORROW_DIRECT_INST_BLACK_LIST = ["CRL"]
	
	private getIndexPageModel(){
		return [sortByOptions:LibReportCommand.sortByOptions, 
				showTopLinks:true, 
				currentFiscalYear: DateUtil.getCurrentFiscalYear(), 
				libraries: borrowDirectService.getLibraryList(serviceKey),
				serviceKey:serviceKey]
	}
	
    def index(){ 
		def model = getIndexPageModel() 
		model.minCalYear = grailsApplication.config.datafarm.minCalYear
        if(serviceKey == BorrowDirectService.BD_SERVICE_KEY) {
            List libraries = model.libraries
            model.libraries = libraries.findAll {GroovyRowResult result ->
                !BORROW_DIRECT_INST_BLACK_LIST.contains(result.institution)
            }
        }
        render(view:'/bd_ezb/index', model:model)
	}
	
	def data_dump( DataDumpCommand cmd ){
		if(!cmd.hasErrors()){
			def library_id = cmd.library				
			def dateFrom = DateUtil.getDateStartOfDay(cmd.dd_from_year, cmd.dd_from_month-1, cmd.dd_from_day)
			def dateTo = DateUtil.getDateEndOfDay(cmd.dd_to_year, cmd.dd_to_month-1, cmd.dd_to_day)
			response.setHeader("Content-Disposition", "attachment;filename=\"my_library_dump.csv\"");
			response.setContentType("text/csv")
			borrowDirectService.dumpDataLibrary(library_id, dateFrom, dateTo, response.outputStream, serviceKey)	
			return null
		}else{
			request.dataDumpCommand = cmd
			render(view:'/bd_ezb/index', model:getIndexPageModel())
		}
	}
	
	def data_dump_mult( DataDumpMultCommand cmd ){
		if(!cmd.hasErrors()){
			def dateFrom = DateUtil.getDateStartOfDay(cmd.ddm_from_year, cmd.ddm_from_month-1, cmd.ddm_from_day)
			def dateTo = DateUtil.getDateEndOfDay(cmd.ddm_to_year, cmd.ddm_to_month-1, cmd.ddm_to_day)
						
			response.setHeader("Content-Disposition", "attachment;filename=\"multiple_items.xlsx\"");
			response.setContentType("application/vnd.ms-excel")
			borrowDirectService.dumpDataMultipleItems(dateFrom, dateTo, cmd.itemTimes, response.outputStream, serviceKey)
			return null
		}else{
			request.dataDumpMultCommand = cmd
			render(view:'/bd_ezb/index', model:getIndexPageModel())
		}
	}
	def summary() {
		if(params.submitBtn == 'Historical'){
			forward(action:'historical_summary', params: params);
		}else{
		def libId = params.library != null? params.long("library"):null;
		def reportNamePrefix = libId != null?borrowDirectService.getLibraryById(serviceKey, libId).institution + " : ":"";
		
		
			def fiscalYear = params.fiscalYear != null ? params.int('fiscalYear'):null;
			def selectedLibIds = getSelectedLibs(params.list('lIds'));
			def data = borrowDirectService.getSummaryDashboardData(libId, fiscalYear, serviceKey, selectedLibIds)
			data.displayMonthsOrder = borrowDirectService.getMonthsInDisplayOrder(data.currentMonth)
			def model = [summaryData: data, 
						reportName:reportNamePrefix + "Summary for fiscal year " + data.fiscalYear,
						libraries: borrowDirectService.getLibraryList(serviceKey, selectedLibIds),
						serviceKey:serviceKey,
						libraryId: libId,
						isSelection: selectedLibIds != null && selectedLibIds.size()>0]
			render(view:'/bd_ezb/summary', model:model)
		}
	}
	
	private getSelectedLibs(libraryIds){
		if(libraryIds != null && libraryIds.size() > 0){
			try{
				return libraryIds*.toInteger();
			}catch(Exception ex){
				log.warn("Invalid library ids: " + ex.getMessage());
			}
		}
		return null;
	}
	def lc_report(){
		def fiscalYear = params.fiscalYear != null ? params.int('fiscalYear'):null;
		def libId = params.library != null ? params.int('library'):null;
		
		def data =  borrowDirectService.getRequestedCallNoCounts(libId, serviceKey, fiscalYear)
		CallNoCounts counts = data.counts
		boolean isHistorical = (data.reportFiscalYear != data.currentFiscalYear)
		def model = [callNoCounts:counts!=null?counts.getCountPerBucket():[:], 
			    callNoCountPerType:counts!=null?counts.getCountPerType():[:],
				bucketItems: BucketService.getInstance().getBucketItems(), 
				//reportName:"LC report for fiscal year " + data.reportFiscalYear, 
				currentFiscalYear: data.currentFiscalYear,
				minFiscalYear: data.minFiscalYear,
				reportFiscalYear: data.reportFiscalYear,
				isHistorical:isHistorical,
				serviceKey:serviceKey]
		
		if(libId != null){
			def libName = borrowDirectService.getLibraryById(serviceKey, libId).institution
			model.libName = libName;
			model.libId = libId;
		}
		
		render(view:'/bd_ezb/lc_report', model:model)
	}
	
	def lib_data_summary(LibReportCommand cmd){			
			if(cmd.reportType == LibReportCommand.SUMMARY){
				if(params.submitBtn == 'Historical'){
					forward(action:'historical_summary', params: params);
				}else{
					forward(action:'summary', params: params);
				}
			}else if(cmd.reportType == LibReportCommand.LC_CLASS){
				def libId = cmd.library
				def fiscalYear = null;
				if(params.submitBtn == 'Historical'){
					fiscalYear = DateUtil.getCurrentFiscalYear() - 1;
					params.fiscalYear = fiscalYear;
				}				
				forward(action:'lc_report', params: params);
			}else if(cmd.hasErrors()){
				request.libReportCommand = cmd
				render(view:'/bd_ezb/index', model:getIndexPageModel())
			}else{
				def dateFrom = DateUtil.getDateStartOfDay(cmd.lr_from_year, cmd.lr_from_month-1, cmd.lr_from_day)
				def dateTo = DateUtil.getDateEndOfDay(cmd.lr_to_year, cmd.lr_to_month-1, cmd.lr_to_day)

				def data = borrowDirectService.getUnfilledRequests(dateFrom, dateTo, cmd.library, cmd.sortBy, serviceKey)
				def libName = borrowDirectService.getLibraryById(serviceKey, cmd.library).institution//Library.read(cmd.library).getCatalogCodeDesc()
				def reportHeader = 'Unfilled requests for ' + libName + ' : ' + ReportGeneratorHelper.getStringValue(dateFrom) + ' - ' + ReportGeneratorHelper.getStringValue(dateTo) 
				render(view:'/bd_ezb/unfilled_requests', model:[reportData: data, reportName:reportHeader, serviceKey:serviceKey])
			}
			return null
	}
	def historical_summary(){
		def libId = params.library != null? params.long("library"):null;
		def libName = libId != null?borrowDirectService.getLibraryById(serviceKey, libId).institution:"";
		def selectedLibIds = getSelectedLibs(params.list('lIds'));
		def data = borrowDirectService.getHistoricalData(serviceKey, selectedLibIds, libId)
		render(view:'/bd_ezb/historical_summary', model:[reportData: data,
			libraries: borrowDirectService.getLibraryList(serviceKey, selectedLibIds), 
			libraryId: libId,
			libName: libName,
			serviceKey:serviceKey, 
			selectedLibIds: selectedLibIds])
	}
}

@Validateable
class DataDumpCommand {
	int library = -1
	int dd_from_year = -1
	int dd_from_month = -1
	int dd_from_day = -1
	
	int dd_to_year = -1
	int dd_to_month = -1
	int dd_to_day = -1
	
	def dd_from_value;
	def dd_to_value;
	
	//String password
	
	static constraints = {
		library(min:0)
		dd_from_year(min:0)
		dd_from_month(min:1, max:12)
		dd_from_day(min:1, max:31)
		
		dd_to_year(min:0)
		dd_to_month(min:1, max:12)
		dd_to_day(min:1, max:31)

//		password(validator: { val, obj ->
//			def realPassword = ConfigurationHolder.config.passwords[obj.library]
//			!StringUtils.isEmpty(val) && !StringUtils.isEmpty(realPassword) && DigestUtils.md5Hex(val) == realPassword
//		})
	}
}

@Validateable
class DataDumpMultCommand {
	int ddm_from_year = -1
	int ddm_from_month = -1
	int ddm_from_day = -1
	
	int ddm_to_year = -1
	int ddm_to_month = -1
	int ddm_to_day = -1
	
	int itemTimes = 1
	
	def ddm_from_value;
	def ddm_to_value;
	
	static constraints = {
		ddm_from_year(min:0)
		ddm_from_month(min:1, max:12)
		ddm_from_day(min:1, max:31)
		
		ddm_to_year(min:0)
		ddm_to_month(min:1, max:12)
		ddm_to_day(min:1, max:31)
		itemTimes(min:0)
	}
}

@Validateable
class LibReportCommand {
	public static final int SUMMARY = 0;
	public static final int LC_CLASS = 1;
	public static final int UNFILLED_REQUESTS = 2;

	static config = Holders.applicationContext.grailsApplication.config
	public static sortByOptions = [config.borrowdirect.db.column.title,
		config.borrowdirect.db.column.callNo,
		config.borrowdirect.db.column.publicationYear,
		config.borrowdirect.db.column.isbn]
	
	int library = -1
	int lr_from_year = -1
	int lr_from_month = -1
	int lr_from_day = -1
	
	int lr_to_year = -1
	int lr_to_month = -1
	int lr_to_day = -1
	
	int reportType = 0
	String sortBy
	
	def lr_from_value;
	def lr_to_value;
	
	static constraints = {
		library(min:0)
		reportType(min:0, max:2)
		
		lr_from_year(validator: { val, obj -> 
			return validateDateFields(val ,obj, 0, Integer.MAX_VALUE)
		})
		lr_from_month(validator: { val, obj -> 
			return validateDateFields(val ,obj, 1, 12)
		})
		lr_from_day(validator: { val, obj -> 
			return validateDateFields(val ,obj, 1, 31)
		})
		
		lr_to_year(validator: { val, obj -> 
			return validateDateFields(val ,obj, 0, Integer.MAX_VALUE)
		})
		lr_to_month(validator: { val, obj -> 
			return validateDateFields(val ,obj, 1, 12)
		})
		lr_to_day(validator: { val, obj -> 
			return validateDateFields(val ,obj, 1, 31)
		})
		sortBy(validator: { val, obj -> 
			if(obj.reportType == LibReportCommand.UNFILLED_REQUESTS){
				return val != null && sortByOptions.contains(val)
			}
			return true
		})
	}
	
	static boolean validateDateFields(val ,obj, min, max){
		if(obj.reportType == LibReportCommand.UNFILLED_REQUESTS){
			return val >= min && val <= max
		}
		return true
	}
}
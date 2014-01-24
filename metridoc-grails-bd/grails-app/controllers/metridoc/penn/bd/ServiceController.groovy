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

import metridoc.penn.util.DateUtil;

class ServiceController {
	def borrowDirectService
	
	private static final DATE_FORMAT = "yyyyMMdd";

	def dataDump = {
		def library_id = params.int("libraryId")
		def dateFrom = DateUtil.getDate(params.dateFrom, DATE_FORMAT)
		def dateTo = DateUtil.getDate(params.dateTo, DATE_FORMAT)
		if(dateFrom != null && dateTo != null && library_id != null){
			def library = borrowDirectService.getLibraryById(params.serviceKey, library_id)
			def libname = library != null?library.institution:""
			response.setHeader("Content-Disposition", "attachment;filename=\""+libname+"_data_dump_"+params.dateFrom+"-"+params.dateTo+".xlsx\"");
			response.setContentType("application/vnd.ms-excel")
			//we added 1 since dateTo without time will convert to 12am of the last day, thus skipping all data for
            //the last day
            borrowDirectService.dumpDataLibrary(library_id, dateFrom, dateTo + 1, response.outputStream, params.serviceKey)
		}else{
			render("Incorrect parameters");
		}
	}
}

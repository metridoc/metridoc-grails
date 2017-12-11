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

import groovy.sql.GroovyResultSetExtension;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;


/**
 * 
 * @author Narine Ghochikyan
 *
 */
class LibraryDataReportGenerator {
			
	private static String[] BIBLIOGRAPHY_DUMP_FIELD_HEADERS = ["BORROWER",
		"LENDER",
		"REQUEST NUMBER",
		"PICK UP LOCATION",
		"REQUEST DATE",
		"SHIP DATE",
		"RECEIVED DATE",
		"STATUS",
		"SHELVING LOCATION",
		"PATRON TYPE",
		"AUTHOR",
		"TITLE",
		"PUBLISHER",
		"PUBLICATION PLACE",
		"PUBLICATION YEAR",
		"ISBN",
		"OCLC",
		"LCCN",
		"CALL NUMBER",
        "LOCAL_ITEM_FOUND"];

    StringBuilder sb = new StringBuilder();
	
	public LibraryDataReportGenerator(){
		for(int i=0; i<BIBLIOGRAPHY_DUMP_FIELD_HEADERS.length; i++){
			sb.append(BIBLIOGRAPHY_DUMP_FIELD_HEADERS[i]);
			sb.append(",");
		}
		sb.append("\n");
	}
	
	def write(OutputStream out) throws IOException{
		out.write(sb.toString().getBytes());
		out.flush();
		out.close();
	}
	def addCell(cellData){
		return ReportGeneratorHelper.getStringValue(cellData).replaceAll('"', '""');
	}

	def addRowData(currentRowData){
		//borrower
		sb.append("\"");
		sb.append(addCell(currentRowData.borrower));
		sb.append("\", \"");	
		//lender
		sb.append(addCell(currentRowData.lender));
		sb.append("\", \"");		
		//request number
		sb.append(addCell(currentRowData.requestNumber));
		sb.append("\", \"");	
		//pickup location
		sb.append(addCell(currentRowData.pickupLocation));
		sb.append("\", \"");	
		//request date
		sb.append(addCell(currentRowData.requestDate));
		sb.append("\", \"");	
		//ship date
		sb.append(addCell(currentRowData.shipDate));
		sb.append("\", \"");	
		//received date
		sb.append(addCell(currentRowData.processDate));
		sb.append("\", \"");	
		//status
		sb.append(addCell(ReportGeneratorHelper.getStatus(currentRowData.isUnfilled)));
		sb.append("\", \"");	//currentRowData.supplierCode)));

		//supplier code - do not include List exhausted
		sb.append(addCell(currentRowData.isUnfilled?"":currentRowData.supplierCode));
		sb.append("\", \"");	
		//patron type
		sb.append(addCell(currentRowData.patronType));
		sb.append("\", \"");	
		//author
		sb.append(addCell(currentRowData.author));
		sb.append("\", \"");	
		//title
		sb.append(addCell(currentRowData.title));
		sb.append("\", \"");	
		//publisher
		sb.append(addCell(currentRowData.publisher));
		sb.append("\", \"");	
		//publication place
		sb.append(addCell(currentRowData.publicationPlace));
		sb.append("\", \"");	
		//publication year
		sb.append(addCell(currentRowData.publicationYear));
		sb.append("\", \"");	
		//isbn
		sb.append(addCell(currentRowData.isbn));
		sb.append("\", \"");	
		//oclc
		sb.append(addCell(currentRowData.oclc));
		sb.append("\", \"");	
		//lccn
		sb.append(addCell(currentRowData.lccn));
		sb.append("\", \"");	
		//call number
		sb.append(addCell(currentRowData.isUnfilled == 0 ? currentRowData.callNumber:currentRowData.callNumberUnf));
		sb.append("\", \"");	
        //local item found
        sb.append(addCell(currentRowData.localItemFound));
        sb.append("\"\n");
	}
}

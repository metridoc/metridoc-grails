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
import java.io.File;

import org.grails.plugins.csv.CSVWriter;
import org.apache.commons.io.FileUtils;

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
	
    def sw;
    def b;
	
	public LibraryDataReportGenerator(){
		sw = new StringWriter();
		b = new CSVWriter(sw, {
			col1:"BORROWER" {it.val1}
			col2:"LENDER" {it.val2}
			col3:"REQUEST NUMBER" {it.val3}
			col4:"PICK UP LOCATION" {it.val4}
			col5:"REQUEST DATE" {it.val5}
			col6:"SHIP DATE" {it.val6}
			col7:"RECEIVED DATE" {it.val7}
			col8:"STATUS" {it.val8}
			col9:"SHELVING LOCATION" {it.val9}
			col10:"PATRON TYPE" {it.val10}
			col11:"AUTHOR" {it.val11}
			col12:"TITLE" {it.val12}
			col13:"PUBLISHER" {it.val13}
			col14:"PUBLICATION PLACE" {it.val14}
			col15:"PUBLICATION YEAR" {it.val15}
			col16:"ISBN" {it.val16}
			col17:"OCLC" {it.val17}
			col18:"LCCN" {it.val18}
			col19:"CALL NUMBER" {it.val19}
			col20:"LOCAL_ITEM_FOUND" {it.val20}
			})
	}
	
	def write(OutputStream out) throws IOException{
		out << b.writer.toString();
		out.flush();
		out.close();
	}
	def processCellData(cellData){
		return ReportGeneratorHelper.getStringValue(cellData);
		
	}
	def addRowData(currentRowData){
		def rowMap = [:];
		//borrower
		rowMap.put('val1', processCellData(currentRowData.borrower));
		//lender
		rowMap.put('val2', processCellData(currentRowData.lender));
		//request number
		rowMap.put('val3', processCellData(currentRowData.requestNumber));
		//pickup location
		rowMap.put('val4', processCellData(currentRowData.pickupLocation));
		//request date
		rowMap.put('val5', processCellData(currentRowData.requestDate));
		//ship date
		rowMap.put('val6', processCellData(currentRowData.shipDate));
		//received date
		rowMap.put('val7', processCellData(currentRowData.processDate));
		//status
		rowMap.put('val8', ReportGeneratorHelper.getStatus(currentRowData.isUnfilled));
		//supplier code - do not include List exhausted
		if(currentRowData.isUnfilled){
			rowMap.put('val9', processCellData(""));
		}else{
			rowMap.put('val9', processCellData(currentRowData.supplierCode));
		}
		//patron type
		rowMap.put('val10', processCellData(currentRowData.patronType));
		//author
		rowMap.put('val11', processCellData(currentRowData.author));
		//title
		rowMap.put('val12', processCellData(currentRowData.title));
		//publisher
		rowMap.put('val13', processCellData(currentRowData.publisher));
		//publication place
		rowMap.put('val14', processCellData(currentRowData.publicationPlace));
		//publication year
		rowMap.put('val15', processCellData(currentRowData.publicationYear));
		//isbn
		rowMap.put('val16', processCellData(currentRowData.isbn));
		//oclc
		rowMap.put('val17', processCellData(currentRowData.oclc));
		//lccn
		rowMap.put('val18', processCellData(currentRowData.lccn));
		//call number
		if(currentRowData.isUnfilled == 0){
			rowMap.put('val19', processCellData(currentRowData.callNumber));
		}else{
			rowMap.put('val19', processCellData(currentRowData.callNumberUnf));
		}
        //local item found
        rowMap.put('val20', processCellData(currentRowData.localItemFound));
		
		b << rowMap;
	}
}

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
class MultipleItemsDataReportGenerator {	
	private static String[] BIBLIOGRAPHY_DUMP_FIELD_HEADERS = [
		"BORROWER",
		"TITLE",
		"CALLNO",
		"PUBLICATION YEAR",
		"ISBN",
		"STATUS",
		"COUNT"];
	
	private Workbook workbook;
	private Sheet sheet;
	private int currentRowIndex;
	private int currentCellIndex;
	
	public MultipleItemsDataReportGenerator(){
		workbook = new SXSSFWorkbook();
		sheet = workbook.createSheet();
		ReportGeneratorHelper.createBibliographyDumpHeader(BIBLIOGRAPHY_DUMP_FIELD_HEADERS, sheet, 0, 0);
		currentRowIndex = 1;
	}
	
	def write(OutputStream out) throws IOException{
		workbook.write(out);
		out.flush();
		out.close();
	}
	def addCell(row, cellData){
		Cell cell = row.createCell(currentCellIndex);
		cell.setCellValue(ReportGeneratorHelper.getStringValue(cellData));
		currentCellIndex++;
		
	}
	def addRowData(currentRowData){
		Row row = sheet.createRow(currentRowIndex);
		currentCellIndex = 0;
		//borrower
		addCell(row, currentRowData.borrower);	
		//title
		addCell(row, currentRowData.title);	
		//call number
		addCell(row, currentRowData.isUnfilled == 0 ? currentRowData.callNumber:currentRowData.callNumberUnf);
		//publication year
		addCell(row, currentRowData.publicationYear);
		//isbn
		addCell(row, currentRowData.isbn);
		//status
		addCell(row, ReportGeneratorHelper.getStatus(currentRowData.isUnfilled));
		// itemTimes
		addCell(row, currentRowData.itemTimes);
		currentRowIndex++;
	}
}
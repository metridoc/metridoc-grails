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

import java.text.SimpleDateFormat;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;

/**
*
* @author Narine
*
*/
class ReportGeneratorHelper {
	
	private static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	private static String STATUS_UNFILLED = "UNF";
	private static String STATUS_FILLED = "REC";
	
	static String getStatus(isUnfilled){
		return isUnfilled>0?STATUS_UNFILLED:STATUS_FILLED
	}
	
	static void createBibliographyDumpHeader(String[] headers, Sheet sh, int rowIndex, int startColumnIndex){
		Row row = sh.createRow(rowIndex);
		for (int i = 0; i < headers.length; i++) {
			Cell cell = row.createCell(startColumnIndex + i);
				cell.setCellValue(headers[i]);
		}
	}
	
	static String getStringValue(Object obj){
		if(obj instanceof Date){
			return DATE_FORMAT.format((Date)obj);
		}else{
			return obj != null ? obj.toString() : "";
		}
	}
}

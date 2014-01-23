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

package metridoc.penn.util

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

class DateUtil {
	public static FY_START_MONTH = Calendar.JULY;
	
	public static Date getDate(int year, int month, int day, int hourOfDay, int minute, int second){
		def calendar = Calendar.getInstance()
		calendar.set(year, month, day, hourOfDay, minute, second)
		return calendar.getTime()//new Date(calendar.getTimeInMillis())
	}
	
	public static Date getDateStartOfDay(year, month, day){
		return DateUtil.getDate(year, month, day, 0, 0, 0)
	}
	
	public static Date getDateEndOfDay(int year, int month, int day){
		return DateUtil.getDate(year, month, day, 23, 59, 59)
	}
	
	public static int getFiscalYear(int year, int month){
		return month < FY_START_MONTH?year:year+1
	}
	public static int getLastDayOfMonth(int year, int month){
		def calendar = Calendar.getInstance()
		calendar.set(Calendar.YEAR, year)
		calendar.set(Calendar.MONTH, month)
		//Reset current day of month in case today is 31st
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		return calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
	}
	
	public static int getCurrentFiscalYear(){
		def currentDate = Calendar.getInstance();
		return DateUtil.getFiscalYear(currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH))
	}
	
	private static Date getFiscalYearStartDate(int fiscalYear){
		return getDateStartOfDay(fiscalYear - 1, FY_START_MONTH, 1)
	}
	public static int getFiscalYearEndMonth(){
		return FY_START_MONTH - 1;
	}
	
	private static Date getFiscalYearEndDate(int fiscalYear){
		def endMonth = getFiscalYearEndMonth();
		return getDateEndOfDay(fiscalYear, endMonth, getLastDayOfMonth(fiscalYear, endMonth))
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
	
	public static Date getDateEndOfDay(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return getDateEndOfDay(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
	}
}

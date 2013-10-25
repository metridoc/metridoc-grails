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

package metridoc.utils

/**
 * Created with IntelliJ IDEA on 9/9/13
 * @author Tommy Barker
 */
class DateUtil {
    public static FY_START_MONTH = Calendar.JULY;
    public static final long ONE_DAY = 1000 * 60 * 60 * 24

    public static Date getDate(int year, int month, int day, int hourOfDay, int minute, int second) {
        def calendar = Calendar.getInstance()
        calendar.set(year, month, day, hourOfDay, minute, second)
        return calendar.getTime()//new Date(calendar.getTimeInMillis())
    }

    public static Date getDateStartOfDay(year, month, day) {
        return getDate(year, month, day, 0, 0, 0)
    }

    public static Date getDateEndOfDay(int year, int month, int day) {
        return getDate(year, month, day, 23, 59, 59)
    }

    public static int getFiscalYear(int year, int month) {
        return month < FY_START_MONTH || FY_START_MONTH == Calendar.JANUARY ? year : year + 1
    }

    public static int getLastDayOfMonth(int year, int month) {
        def calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month)
        //Reset current day of month in case today is 31st
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
    }

    public static int getCurrentFiscalYear() {
        def currentDate = Calendar.getInstance();
        return getFiscalYear(currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH))
    }

    public static Date getFiscalYearStartDate(int fiscalYear) {
        return getDateStartOfDay(fiscalYear - 1, FY_START_MONTH, 1)
    }

    public static int getFiscalYearEndMonth() {
        return FY_START_MONTH - 1;
    }

    public static Date getFiscalYearEndDate(int fiscalYear) {
        def endMonth = getFiscalYearEndMonth();
        return getDateEndOfDay(fiscalYear, endMonth, getLastDayOfMonth(fiscalYear, endMonth))
    }

    public static Double differenceByDays(Date left, Date right) {
        if (datesNotNull(left, right)) {
            long leftLong = left.time
            long rightLong = right.time

            return (leftLong - rightLong) / ONE_DAY
        } else {
            return null
        }
    }

    private static boolean datesNotNull(Date dateOne, Date dateTwo) {
        dateOne != null && dateTwo != null
    }

    static String getFiscalMonth() {
        getMonthName(FY_START_MONTH)
    }

    static String getMonthName(int monthNumber) {
        switch(monthNumber) {
            case Calendar.JANUARY:
                return "January"
            case Calendar.FEBRUARY:
                return "February"
            case Calendar.MARCH:
                return "March"
            case Calendar.APRIL:
                return "April"
            case Calendar.MAY:
                return "May"
            case Calendar.JUNE:
                return "June"
            case Calendar.JULY:
                return "July"
            case Calendar.AUGUST:
                return "August"
            case Calendar.SEPTEMBER:
                return "September"
            case Calendar.OCTOBER:
                return "October"
            case Calendar.NOVEMBER:
                return "November"
            case Calendar.DECEMBER:
                return "December"
        }
        throw new IllegalArgumentException("Could not convert [$monthNumber] to a month")
    }
}


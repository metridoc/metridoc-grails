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

import spock.lang.Specification

/**
 * Created with IntelliJ IDEA on 9/13/13
 * @author Tommy Barker
 */
class DateUtilSpec extends Specification {

    void "test converting month number to month text"() {
        expect:
        a == DateUtil.getMonthName(b)

        where:
        a           | b
        "January"   | Calendar.JANUARY
        "February"  | Calendar.FEBRUARY
        "March"     | Calendar.MARCH
        "April"     | Calendar.APRIL
        "May"       | Calendar.MAY
        "June"      | Calendar.JUNE
        "July"      | Calendar.JULY
        "August"    | Calendar.AUGUST
        "September" | Calendar.SEPTEMBER
        "October"   | Calendar.OCTOBER
        "November"  | Calendar.NOVEMBER
        "December"  | Calendar.DECEMBER
    }

    void "bad month throws an error"() {
        when:
        DateUtil.getMonthName(100)

        then:
        thrown(IllegalArgumentException)
    }
}

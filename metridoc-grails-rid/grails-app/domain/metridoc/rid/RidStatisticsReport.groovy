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
  *	permissions and limitations under the License.
  */

package metridoc.rid

class RidStatisticsReport {
    ArrayList months = new ArrayList()

    Integer totalTransactions
    Integer yearTransactions
    ArrayList monthTransactions = new ArrayList()

    Integer avgInteractOccurrences
    Integer totalInteractOccurences = 0
    Integer yearInteractOccurences = 0
    ArrayList monthInteractOccurences = new ArrayList()

    Integer avgPrepTime
    Integer totalPrepTime = 0
    Integer yearPrepTime = 0
    ArrayList monthPrepTime = new ArrayList()

    Integer avgEventLength
    Integer totalEventLength = 0
    Integer yearEventLength = 0
    ArrayList monthEventLength = new ArrayList()

    String staffPennkey
    Integer pennkeyMax

    ArrayList departments = new ArrayList()
    ArrayList totalDepartments = new ArrayList()
    ArrayList yearDepartments = new ArrayList()
    ArrayList<ArrayList> monthDepartments = new ArrayList()
    ArrayList topFiveDepartments = new ArrayList()
    ArrayList topFiveTotalDepartments = new ArrayList()
    ArrayList topFiveYearDepartments = new ArrayList()
    ArrayList<ArrayList> topFiveMonthDepartments = new ArrayList()

    ArrayList courses = new ArrayList()
    Integer coursesAdded = 0
    ArrayList totalCourses = new ArrayList()
    ArrayList yearCourses = new ArrayList()
    ArrayList<ArrayList> monthCourses = new ArrayList()
    ArrayList topFiveCourses = new ArrayList()
    ArrayList topFiveTotalCourses = new ArrayList()
    ArrayList topFiveYearCourses = new ArrayList()
    ArrayList<ArrayList> topFiveMonthCourses = new ArrayList()

    ArrayList ranks = new ArrayList()
    ArrayList totalRanks = new ArrayList()
    ArrayList yearRanks = new ArrayList()
    ArrayList<ArrayList> monthRanks = new ArrayList()

    ArrayList userGoals = new ArrayList()
    ArrayList totalUserGoals = new ArrayList()
    ArrayList yearUserGoals = new ArrayList()
    ArrayList<ArrayList> monthUserGoals = new ArrayList()

    ArrayList modeOfConsultation = new ArrayList()
    ArrayList totalModeOfConsultation = new ArrayList()
    ArrayList yearModeOfConsultation = new ArrayList()
    ArrayList<ArrayList> monthModeOfConsultation = new ArrayList()

    ArrayList schools = new ArrayList()
    ArrayList totalSchools = new ArrayList()
    ArrayList yearSchools = new ArrayList()
    ArrayList<ArrayList> monthSchools = new ArrayList()

    ArrayList courseSponsors = new ArrayList()
    ArrayList totalCourseSponsors = new ArrayList()
    ArrayList yearCourseSponsors = new ArrayList()
    ArrayList<ArrayList> monthCourseSponsors = new ArrayList()

    ArrayList serviceProvided = new ArrayList()
    ArrayList totalServiceProvided = new ArrayList()
    ArrayList yearServiceProvided = new ArrayList()
    ArrayList<ArrayList> monthServiceProvided = new ArrayList()

    ArrayList libraryUnits = new ArrayList()
    ArrayList totalLibraryUnits = new ArrayList()
    ArrayList yearLibraryUnits = new ArrayList()
    ArrayList<ArrayList> monthLibraryUnits = new ArrayList()


}

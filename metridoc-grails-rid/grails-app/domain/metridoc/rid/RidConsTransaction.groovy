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

class RidConsTransaction extends RidConsTransactionBase {

    static belongsTo = [
            department        : RidDepartment,
            courseSponsor     : RidCourseSponsor,
            userGoal          : RidUserGoal,
            modeOfConsultation: RidModeOfConsultation,
            rank              : RidRank,
            serviceProvided   : RidServiceProvided,
            school            : RidSchool,
            expertise         : RidExpertise,
            ridLibraryUnit    : RidLibraryUnit]

    static transients = ['otherRank', 'otherUserGoal', 'otherModeOfConsultation', 'otherSchool',
                         'otherCourseSponsor','otherExpertise', 'otherService']

    String spreadsheetName

    static constraints = {
        // STATEMENT OF WORK
        userQuestion(blank: true, nullable: true, maxSize: 500)
        interactOccurrences(nullable: false, min: 0, max: 1023)
        prepTime(nullable: false, min: 0)
        eventLength(nullable: false, min: 0)
        notes(blank: true, nullable: true, maxSize: 500)
        staffPennkey(blank: false, nullable: false, maxSize: 100)
//        testField(blank:true,nullable: true ,maxSize:100)

        // ROLES
        facultySponsor(blank: true, nullable: true, maxSize: 100)
        courseName(blank: true, nullable: true, maxSize: 100)
        courseNumber(blank: true, nullable: true, maxSize: 100)
        otherRank(blank: true, nullable: true, maxSize: 50)
        otherUserGoal(blank: true, nullable: true, maxSize: 50)
        otherModeOfConsultation(blank: true, nullable: true, maxSize: 50)
        school(nullable: true)
        otherSchool(blank: true, nullable: true, maxSize: 50)
        department(nullable: true)
        userName(blank: true, nullable: true, maxSize: 50)
        courseSponsor(nullable: true)
        otherCourseSponsor(blank: true, nullable: true, maxSize: 50)

        // DESCRIPTION
        serviceProvided(nullable: false)
        otherService(blank: true, nullable: true, maxSize: 100)
        modeOfConsultation(nullable: false)
        userGoal(nullable: true)
        expertise(nullable: true, blank: true)
        otherExpertise(nullable: true, blank: true)
        ridLibraryUnit(nullable: false)
        spreadsheetName(nullable: true, blank: true)
    }
}

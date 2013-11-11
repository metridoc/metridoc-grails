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

class RidInsTransaction extends RidInsTransactionBase {

    static belongsTo = [
            department: RidDepartment,
//            rank: RidRank,
            school: RidSchool,
            ridLibraryUnit: RidLibraryUnit,
            location: RidLocation,
            sessionType: RidSessionType,
            audience: RidAudience,
            instructionalMaterials: RidInstructionalMaterials]

    static transients = ['otherSchool', 'otherLocation',
            'otherSessionType', 'otherAudience', 'otherInstructionalMaterials']

    String spreadsheetName

    static constraints = {
        // STATEMENT OF WORK
        prepTime(nullable: false, min: 0)
        eventLength(nullable: false, min: 0)
        notes(blank: true, nullable: true, maxSize: 500)
        sessionDescription(blank: true, nullable: true, maxSize: 500)
        instructorPennkey(blank: false, nullable: false, maxSize: 100)
        coInstructorPennkey(blank: true, nullable: true, maxSize: 100)
        sequenceName(blank: true, nullable: true, maxSize: 100)
        sequenceUnit(validator: { value ->
            return ((!value) || (value > 0)) }, nullable: true)
        location(blank: false, nullable: false)
        otherLocation(blank: true, nullable: true, maxSize: 50)

        // ROLES
        facultySponsor(blank: true, nullable: true, maxSize: 100)
        courseName(blank: true, nullable: true, maxSize: 100)
        courseNumber(blank: true, nullable: true, maxSize: 100)
        //otherRank(blank: true, nullable: true, maxSize: 50)
        school(nullable: true)
        otherSchool(blank: true, nullable: true, maxSize: 50)
        department(nullable: true)
        //userName(blank: true, nullable: true, maxSize: 50)
        requestor(blank: true, nullable: true, maxSize: 50)

        // DESCRIPTION
        ridLibraryUnit(nullable: false)
        attendanceTotal(blank: false, nullable: false, min: 0)
        spreadsheetName(nullable: true, blank: true)
        sessionType(nullable: false, blank: false)
        otherSessionType(nullable: true, blank: true)
        audience(nullable: true, blank: true)
        otherAudience(nullable: true, blank: true)
        instructionalMaterials(blank: true, nullable: true, maxSize: 50)
        otherInstructionalMaterials(blank: true, nullable: true, maxSize: 50)
    }
}

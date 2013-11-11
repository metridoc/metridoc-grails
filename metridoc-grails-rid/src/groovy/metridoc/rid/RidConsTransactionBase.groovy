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

abstract class RidConsTransactionBase {

    // statement of work
    String userQuestion
    Integer interactOccurrences = 1
    Integer prepTime = 0
    Integer eventLength = 1
    String notes
    String staffPennkey

    // roles
    String userName
    String facultySponsor
    String courseName
    String courseNumber
    String otherRank
    String otherUserGoal
    String otherModeOfConsultation
    String otherSchool
    String otherCourseSponsor

    // description
    String otherService
    // Calendar dateOfConsultation = Calendar.getInstance()
    Date dateOfConsultation = new Date()

    String toString() {
        String userQ = new String(userQuestion)
        if (userQ != null && userQ.length() > 32)
            userQ = userQ.substring(0, 32) + "..."
        return "ID: ${id}; userQuestion: ${userQ}"
    }
}

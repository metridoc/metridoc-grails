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

class RidTest {
    static hasMany = [ridConsTransaction: RidConsTransaction,
                      ridInsTransaction: RidInsTransaction]
    String test;

    String toString() {
        return "${test}"
    }

    static constraints = {
        test(blank: false, nullable: false, unique: true, maxSize: 150)
        ridConsTransaction(nullable: true)
        ridInsTransaction(nullable: true)
    }
}

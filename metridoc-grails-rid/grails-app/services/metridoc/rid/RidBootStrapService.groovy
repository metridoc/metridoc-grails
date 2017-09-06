/*
  *Copyright 2013 Trustees of the University of Pennsylvania. Licensed under the
  * Educational Community License, Version 2.0 (the "License"); you may
  * not use this file except in compliance with the License. You may
  * obtain a copy of the License at
  *
  *http://www.osedu.org/licenses/ECL-2.0
  *
  * Unless required by applicable law or agreed to in writing,
  * software distributed under the License is distributed on an "AS IS"
  * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
  * or implied. See the License for the specific language governing
  * permissions and limitations under the License.
  */

package metridoc.rid

import grails.util.Environment
import org.apache.commons.lang.RandomStringUtils

class RidBootStrapService {

    def testDataService

    static final UPENN_DATA = "metridoc.rid.ingestUpennData"
    static final TEST_DATA = "metridoc.rid.ingestTestData"

    def ingestTestDataCheck(environment){

        switch (environment) {

            case Environment.PRODUCTION:
                //Default values are false
                if(!System.properties.containsKey(TEST_DATA) || System?.getProperty(TEST_DATA)=="false"){
                    System.setProperty(TEST_DATA,"false")
                }
                else{
                    System.setProperty(TEST_DATA, "true")
                    System.setProperty(UPENN_DATA, "true")//Automatically true if ingestTestData is true
                }
                if(!System.properties.containsKey(UPENN_DATA) || System?.getProperty(UPENN_DATA)=="false"){
                    System.setProperty(UPENN_DATA,"false")
                }
                else{
                    System.setProperty(UPENN_DATA, "true")
                }

                break

            default:
                //Default values are true
                if(System.properties.containsKey(TEST_DATA) && System?.getProperty(TEST_DATA)=="false"){
                    System.setProperty(TEST_DATA,"false")
                }
                else{
                    System.setProperty(TEST_DATA, "true")
                    System.setProperty(UPENN_DATA, "true")//Automatically true if ingestTestData is true
                }

                if(System.properties.containsKey(UPENN_DATA) && System?.getProperty(UPENN_DATA)=="false"){
                    System.setProperty(UPENN_DATA,"false")
                }
                else{
                    System.setProperty(UPENN_DATA, "true")
                }

                break
        }
    }


    //I'm keeping this method separate just in case anything else ever needs to be bootstrapped
    def bootStrapContents() {

        if(!System.properties.containsKey(UPENN_DATA) || !System.properties.containsKey(TEST_DATA))
            ingestTestDataCheck(Environment.current)

        if(System.getProperty(TEST_DATA)=="true"){
            testDataService.initialization()
            // testDataService.populateTestFields()
            testDataService.createTestTransactions()
        }
        else if(System.getProperty(UPENN_DATA)=="true"){
            // testDataService.populateTestFields()
            testDataService.initialization()
        }

    }
}


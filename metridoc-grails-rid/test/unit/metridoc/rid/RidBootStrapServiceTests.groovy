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

import grails.test.mixin.TestFor
import org.apache.poi.ss.usermodel.*
import org.codehaus.groovy.grails.io.support.ClassPathResource
import org.junit.Before
import org.junit.Test
import grails.test.mixin.Mock
import grails.test.MockUtils.*
import metridoc.rid.TestDataService

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(RidBootStrapService)
@Mock([RidAudience, RidConsTransaction, RidCourseSponsor, RidDepartment, RidInsTransaction, RidInstructionalMaterials, RidLibraryUnit, RidLocation, RidModeOfConsultation, RidRank, RidSchool, RidServiceProvided, RidSessionType, RidUserGoal])
class RidBootStrapServiceTests {

    void clearProperties(){
        if (System.properties.containsKey("metridoc.rid.ingestUpennData")){
            System.properties.remove("metridoc.rid.ingestUpennData")
        }
        if (System.properties.containsKey("metridoc.rid.ingestTestData")){
            System.properties.remove("metridoc.rid.ingestTestData")
        }

    }

    @Test
    void "Test bootstrapContents- no flags"() {
        clearProperties()
        //Default values are to make test data
        service.testDataService = new TestDataService()

        service.bootStrapContents()
        assert System.getProperty("metridoc.rid.ingestUpennData")== "true"
        assert System.getProperty("metridoc.rid.ingestTestData")== "true"

        assert RidLibraryUnit.list().size() != 0
        assert RidConsTransaction.list().size() != 0
    }

    @Test
    void "Test production environment- no flags"() {
        clearProperties()
        service.testDataService = new TestDataService()

        service.ingestTestDataCheck("production")
        assert System.getProperty("metridoc.rid.ingestUpennData")== "false"
        assert System.getProperty("metridoc.rid.ingestTestData")== "false"

        service.bootStrapContents()
        assert RidLibraryUnit.list().size() == 0
        assert RidConsTransaction.list().size() == 0
    }

    @Test
    void "Test both ingest flags true- default env"(){
        clearProperties()
        service.testDataService = new TestDataService()
        System.setProperty("metridoc.rid.ingestUpennData", "true")
        System.setProperty("metridoc.rid.ingestTestData", "true")

        service.ingestTestDataCheck("test")
        service.bootStrapContents()
        assert RidLibraryUnit.list().size() != 0
        assert RidConsTransaction.list().size() != 0
    }

    @Test
    void "Test just upenn ingest flag true- default env"(){
        clearProperties()
        service.testDataService = new TestDataService()
        System.setProperty("metridoc.rid.ingestUpennData", "true")
        System.setProperty("metridoc.rid.ingestTestData", "false")

        service.ingestTestDataCheck("test")
        service.bootStrapContents()
        assert RidLibraryUnit.list().size() != 0
        assert RidConsTransaction.list().size() == 0
    }

    @Test
    void "Test just test flag true- default env"(){
        clearProperties()
        service.testDataService = new TestDataService()
        System.setProperty("metridoc.rid.ingestUpennData", "false")
        System.setProperty("metridoc.rid.ingestTestData", "true")

        service.ingestTestDataCheck("test")
        assert System.getProperty("metridoc.rid.ingestUpennData") //Should be set to true because needed for test data
        service.bootStrapContents()
        assert RidLibraryUnit.list().size() != 0
        assert RidConsTransaction.list().size() != 0
    }

    @Test
    void "Test both ingest flags true- production env"(){
        clearProperties()
        service.testDataService = new TestDataService()
        System.setProperty("metridoc.rid.ingestUpennData", "true")
        System.setProperty("metridoc.rid.ingestTestData", "true")

        service.ingestTestDataCheck("production")
        service.bootStrapContents()
        assert RidLibraryUnit.list().size() != 0
        assert RidConsTransaction.list().size() != 0
    }

    @Test
    void "Test just upenn ingest flag true- production env"(){
        clearProperties()
        service.testDataService = new TestDataService()
        System.setProperty("metridoc.rid.ingestUpennData", "true")
        System.setProperty("metridoc.rid.ingestTestData", "false")

        service.ingestTestDataCheck("production")
        service.bootStrapContents()
        assert RidLibraryUnit.list().size() != 0
        assert RidConsTransaction.list().size() == 0
    }

    @Test
    void "Test just test flag true- production env"(){
        clearProperties()
        service.testDataService = new TestDataService()
        System.setProperty("metridoc.rid.ingestUpennData", "false")
        System.setProperty("metridoc.rid.ingestTestData", "true")

        service.ingestTestDataCheck("production")
        assert System.getProperty("metridoc.rid.ingestUpennData") //Should be set to true because needed for test data
        service.bootStrapContents()
        assert RidLibraryUnit.list().size() != 0
        assert RidConsTransaction.list().size() != 0
    }
}
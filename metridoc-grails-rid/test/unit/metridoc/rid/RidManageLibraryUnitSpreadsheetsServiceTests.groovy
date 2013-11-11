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
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import org.springframework.core.io.ClassPathResource

@TestFor(RidManageLibraryUnitSpreadsheetsService)
class RidManageLibraryUnitSpreadsheetsServiceTests {

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder()

    @Test
    void "SpreadsheetService method transferSpreadsheets should transfer spreadsheets into local hidden directory metridoc under subdirectory files"() {
        assert new ClassPathResource("spreadsheet/Transaction_List.xlsx").exists()

        File dest = temporaryFolder.newFolder("dest")
        assert dest.exists()
        service.unitSpreadsheetDir = dest
        service.transferSpreadsheets()
        assert new File(temporaryFolder.getRoot().canonicalPath + "/dest/Transaction_List.xlsx").exists()
    }
/* TODO: Make an integration test for this
    @Test
    void "SpreadsheetService method download should move spreadsheets from local hidden directory metridoc under subdirectory files"() {
        assert new ClassPathResource("spreadsheet/Transaction_List.xlsx").exists()

        File dest = temporaryFolder.newFolder("dest")
        File targ1 = temporaryFolder.newFolder("targ1")
        File targ2 = temporaryFolder.newFolder("targ2")
        assert dest.exists()
        service.unitSpreadsheetDir = dest
        service.transferSpreadsheets()
        assert new File(temporaryFolder.getRoot().canonicalPath + "/dest/Transaction_List.xlsx").exists()

        def luc = new RidAdminLibraryUnitController()
        luc.params.sname = "CDM_Bulkload_Schematic.xlsx"
        service.unitSpreadsheetDir = targ1
        service.DEFAULT_SPREADSHEET_DIRECTORY = dest
        service.download(luc.response,  luc.flash, luc.params)
        assert new File(temporaryFolder.getRoot().canonicalPath + "/targ1/CDM_Bulkload_Schematic.xlsx").exists()


    }
    */

}


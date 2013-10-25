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


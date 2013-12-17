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



import grails.test.mixin.*
import org.apache.poi.ss.usermodel.*
import org.codehaus.groovy.grails.io.support.ClassPathResource
import org.codehaus.groovy.grails.web.servlet.FlashScope
import org.junit.*
import org.junit.rules.TemporaryFolder

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(ValidateSpreadsheetService)
class ValidateSpreadsheetServiceTests {

    Workbook blankWB
    Workbook badWB
    Workbook goodWB

    @Test
    void "Test checkSpreadsheetFormat"() {
        assert !service.checkSpreadsheetFormat(blankWB)
        assert service.checkSpreadsheetFormat(badWB)//Should pass since format is correct
        assert service.checkSpreadsheetFormat(goodWB)
    }

    @Test
    void "Test checkFileType"() {
        assert !service.checkFileType("application/msword")
        assert service.checkFileType("application/x-excel")
    }

    //checkValid() and checkValidWithType() tested in SpreadSheetServiceTests because they depend on a method in there

    @Before
    void setup() {

        //TODO: Refactor the test sheet creation code. Not sure what a good refactor location would be though
        //Moved to bottom for readability purposes

        ClassPathResource resource0 = new ClassPathResource("spreadsheet/Transaction_List.xlsx")//WorkbookFactory needs a file, and this file is pretty much blank anyway

        blankWB = WorkbookFactory.create(resource0.getFile().newInputStream())
        Sheet sheetBlank = blankWB.getSheetAt(0)

        badWB = WorkbookFactory.create(resource0.getFile().newInputStream())
        Sheet sheetBad = badWB.getSheetAt(0)

        Row row = sheetBad.createRow(5)
        row.createCell(1).setCellValue("Library Unit")
        row.getCell(1).setCellType(Cell.CELL_TYPE_STRING)
        row.createCell(2).setCellValue("Fake Library Unit")
        row.getCell(2).setCellType(Cell.CELL_TYPE_STRING)

        row = sheetBad.createRow(7)
        row.createCell(1).setCellValue("Date of Consultation (mm/dd/yyyy)")
        row.getCell(1).setCellType(Cell.CELL_TYPE_STRING)
        row.createCell(2).setCellValue("1/1/13")
        row.getCell(2).setCellType(Cell.CELL_TYPE_STRING)

        row = sheetBad.createRow(9)
        row.createCell(1).setCellValue("Staff Pennkey")
        row.getCell(1).setCellType(Cell.CELL_TYPE_STRING)
        row.createCell(2).setCellValue("test")
        row.getCell(2).setCellType(Cell.CELL_TYPE_STRING)

        row = sheetBad.createRow(11)
        row.createCell(1).setCellValue("Consultation Mode")
        row.getCell(1).setCellType(Cell.CELL_TYPE_STRING)
        row.createCell(2).setCellValue("Email")
        row.getCell(2).setCellType(Cell.CELL_TYPE_STRING)

        row = sheetBad.createRow(13)
        row.createCell(1).setCellValue("Service Provided")
        row.getCell(1).setCellType(Cell.CELL_TYPE_STRING)
        row.createCell(2).setCellValue("Tour")
        row.getCell(2).setCellType(Cell.CELL_TYPE_STRING)

        row = sheetBad.createRow(15)
        row.createCell(1).setCellValue("User Goal")
        row.getCell(1).setCellType(Cell.CELL_TYPE_STRING)
        row.createCell(2).setCellValue("Research Paper")
        row.getCell(2).setCellType(Cell.CELL_TYPE_STRING)

        row = sheetBad.createRow(17)
        row.createCell(1).setCellValue("Prep Time (enter in minutes)")
        row.getCell(1).setCellType(Cell.CELL_TYPE_STRING)
        row.createCell(2).setCellValue("10")
        row.getCell(2).setCellType(Cell.CELL_TYPE_STRING)

        row = sheetBad.createRow(19)
        row.createCell(1).setCellValue("Event Length (enter in minutes)")
        row.getCell(1).setCellType(Cell.CELL_TYPE_STRING)
        row.createCell(2).setCellValue("10")
        row.getCell(2).setCellType(Cell.CELL_TYPE_STRING)

        row = sheetBad.createRow(21)
        row.createCell(1).setCellValue("User Name")
        row.getCell(1).setCellType(Cell.CELL_TYPE_STRING)
        row.createCell(2).setCellValue("Tester")
        row.getCell(2).setCellType(Cell.CELL_TYPE_STRING)

        row = sheetBad.createRow(23)
        row.createCell(1).setCellValue("Rank")
        row.getCell(1).setCellType(Cell.CELL_TYPE_STRING)
        row.createCell(2).setCellValue("Grad Student")
        row.getCell(2).setCellType(Cell.CELL_TYPE_STRING)

        row = sheetBad.createRow(25)
        row.createCell(1).setCellValue("School")
        row.getCell(1).setCellType(Cell.CELL_TYPE_STRING)
        row.createCell(2).setCellValue("SEAS")
        row.getCell(2).setCellType(Cell.CELL_TYPE_STRING)

        row = sheetBad.createRow(27)
        row.createCell(1).setCellValue("Interact Occurrences")
        row.getCell(1).setCellType(Cell.CELL_TYPE_STRING)
        row.createCell(2).setCellValue("1")
        row.getCell(2).setCellType(Cell.CELL_TYPE_STRING)

        row = sheetBad.createRow(29)
        row.createCell(1).setCellValue("Course Name")
        row.getCell(1).setCellType(Cell.CELL_TYPE_STRING)
        row.createCell(2).setCellValue("Test")
        row.getCell(2).setCellType(Cell.CELL_TYPE_STRING)

        row = sheetBad.createRow(31)
        row.createCell(1).setCellValue("Department")
        row.getCell(1).setCellType(Cell.CELL_TYPE_STRING)
        row.createCell(2).setCellValue("CIS")
        row.getCell(2).setCellType(Cell.CELL_TYPE_STRING)

        row = sheetBad.createRow(33)
        row.createCell(1).setCellValue("Course Number")
        row.getCell(1).setCellType(Cell.CELL_TYPE_STRING)
        row.createCell(2).setCellValue("123")
        row.getCell(2).setCellType(Cell.CELL_TYPE_STRING)

        row = sheetBad.createRow(35)
        row.createCell(1).setCellValue("Faculty Sponsor")
        row.getCell(1).setCellType(Cell.CELL_TYPE_STRING)
        row.createCell(2).setCellValue("Tester")
        row.getCell(2).setCellType(Cell.CELL_TYPE_STRING)

        row = sheetBad.createRow(37)
        row.createCell(1).setCellValue("Course Sponsor")
        row.getCell(1).setCellType(Cell.CELL_TYPE_STRING)
        row.createCell(2).setCellValue("SEAS")
        row.getCell(2).setCellType(Cell.CELL_TYPE_STRING)

        row = sheetBad.createRow(39)
        row.createCell(1).setCellValue("User Question")
        row.getCell(1).setCellType(Cell.CELL_TYPE_STRING)
        row.createCell(2).setCellValue("test")
        row.getCell(2).setCellType(Cell.CELL_TYPE_STRING)

        row = sheetBad.createRow(41)
        row.createCell(1).setCellValue("Notes")
        row.getCell(1).setCellType(Cell.CELL_TYPE_STRING)
        row.createCell(2).setCellValue("test")
        row.getCell(2).setCellType(Cell.CELL_TYPE_STRING)

        goodWB = WorkbookFactory.create(resource0.getFile().newInputStream())
        Sheet sheetGood = goodWB.getSheetAt(0)

        row = sheetGood.createRow(5)
        row.createCell(1).setCellValue("library unit") //Case shouldn't matter
        row.getCell(1).setCellType(Cell.CELL_TYPE_STRING)
        row.createCell(2).setCellValue("Science Libraries")
        row.getCell(2).setCellType(Cell.CELL_TYPE_STRING)

        row = sheetGood.createRow(7)
        row.createCell(1).setCellValue("Date of Consultation (mm/dd/yyyy)")
        row.getCell(1).setCellType(Cell.CELL_TYPE_STRING)
        row.createCell(2).setCellValue("1/1/13")
        row.getCell(2).setCellType(Cell.CELL_TYPE_STRING)

        row = sheetGood.createRow(9)
        row.createCell(1).setCellValue("Staff Pennkey")
        row.getCell(1).setCellType(Cell.CELL_TYPE_STRING)
        row.createCell(2).setCellValue("test")
        row.getCell(2).setCellType(Cell.CELL_TYPE_STRING)

        row = sheetGood.createRow(11)
        row.createCell(1).setCellValue("Consultation Mode")
        row.getCell(1).setCellType(Cell.CELL_TYPE_STRING)
        row.createCell(2).setCellValue("Email")
        row.getCell(2).setCellType(Cell.CELL_TYPE_STRING)

        row = sheetGood.createRow(13)
        row.createCell(1).setCellValue("Service Provided")
        row.getCell(1).setCellType(Cell.CELL_TYPE_STRING)
        row.createCell(2).setCellValue("Tour")
        row.getCell(2).setCellType(Cell.CELL_TYPE_STRING)

        row = sheetGood.createRow(15)
        row.createCell(1).setCellValue("User Goal")
        row.getCell(1).setCellType(Cell.CELL_TYPE_STRING)
        row.createCell(2).setCellValue("Research Paper")
        row.getCell(2).setCellType(Cell.CELL_TYPE_STRING)

        row = sheetGood.createRow(17)
        row.createCell(1).setCellValue("Prep Time (enter in minutes)")
        row.getCell(1).setCellType(Cell.CELL_TYPE_STRING)
        row.createCell(2).setCellValue("10")
        row.getCell(2).setCellType(Cell.CELL_TYPE_STRING)

        row = sheetGood.createRow(19)
        row.createCell(1).setCellValue("Event Length (enter in minutes)")
        row.getCell(1).setCellType(Cell.CELL_TYPE_STRING)
        row.createCell(2).setCellValue("10")
        row.getCell(2).setCellType(Cell.CELL_TYPE_STRING)

        row = sheetGood.createRow(21)
        row.createCell(1).setCellValue("User Name")
        row.getCell(1).setCellType(Cell.CELL_TYPE_STRING)
        row.createCell(2).setCellValue("Tester")
        row.getCell(2).setCellType(Cell.CELL_TYPE_STRING)

        row = sheetGood.createRow(23)
        row.createCell(1).setCellValue("Rank")
        row.getCell(1).setCellType(Cell.CELL_TYPE_STRING)
        row.createCell(2).setCellValue("Grad Student")
        row.getCell(2).setCellType(Cell.CELL_TYPE_STRING)

        row = sheetGood.createRow(25)
        row.createCell(1).setCellValue("School")
        row.getCell(1).setCellType(Cell.CELL_TYPE_STRING)
        row.createCell(2).setCellValue("SEAS")
        row.getCell(2).setCellType(Cell.CELL_TYPE_STRING)

        row = sheetGood.createRow(27)
        row.createCell(1).setCellValue("Interact Occurrences")
        row.getCell(1).setCellType(Cell.CELL_TYPE_STRING)
        row.createCell(2).setCellValue("1")
        row.getCell(2).setCellType(Cell.CELL_TYPE_STRING)

        row = sheetGood.createRow(29)
        row.createCell(1).setCellValue("Course Name")
        row.getCell(1).setCellType(Cell.CELL_TYPE_STRING)
        row.createCell(2).setCellValue("Test")
        row.getCell(2).setCellType(Cell.CELL_TYPE_STRING)

        row = sheetGood.createRow(31)
        row.createCell(1).setCellValue("Department")
        row.getCell(1).setCellType(Cell.CELL_TYPE_STRING)
        row.createCell(2).setCellValue("CIS")
        row.getCell(2).setCellType(Cell.CELL_TYPE_STRING)

        row = sheetGood.createRow(33)
        row.createCell(1).setCellValue("Course Number")
        row.getCell(1).setCellType(Cell.CELL_TYPE_STRING)
        row.createCell(2).setCellValue("123")
        row.getCell(2).setCellType(Cell.CELL_TYPE_STRING)

        row = sheetGood.createRow(35)
        row.createCell(1).setCellValue("Faculty Sponsor")
        row.getCell(1).setCellType(Cell.CELL_TYPE_STRING)
        row.createCell(2).setCellValue("Tester")
        row.getCell(2).setCellType(Cell.CELL_TYPE_STRING)

        row = sheetGood.createRow(37)
        row.createCell(1).setCellValue("Course Sponsor")
        row.getCell(1).setCellType(Cell.CELL_TYPE_STRING)
        row.createCell(2).setCellValue("SEAS")
        row.getCell(2).setCellType(Cell.CELL_TYPE_STRING)

        row = sheetGood.createRow(39)
        row.createCell(1).setCellValue("User Question")
        row.getCell(1).setCellType(Cell.CELL_TYPE_STRING)
        row.createCell(2).setCellValue("test")
        row.getCell(2).setCellType(Cell.CELL_TYPE_STRING)

        row = sheetGood.createRow(41)
        row.createCell(1).setCellValue("Notes")
        row.getCell(1).setCellType(Cell.CELL_TYPE_STRING)
        row.createCell(2).setCellValue("test")
        row.getCell(2).setCellType(Cell.CELL_TYPE_STRING)


    }
}
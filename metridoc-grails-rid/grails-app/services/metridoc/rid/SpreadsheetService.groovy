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

import org.apache.poi.ss.usermodel.*
import org.apache.poi.ss.util.CellReference
import org.codehaus.groovy.grails.io.support.ClassPathResource
import org.codehaus.groovy.grails.web.servlet.FlashScope
import org.springframework.web.multipart.MultipartFile

import java.text.SimpleDateFormat

class SpreadsheetService extends ValidateSpreadsheetService{


    //Workbook generated using convertToWorkbook in ValidateSpreadsheetService
    def getInstancesFromSpreadsheet(Workbook wb, FlashScope flash) {

        Sheet sheet = wb.getSheetAt(0)
        int colNum = 1
        Boolean iterNext = Boolean.TRUE
        List<List<String>> allInstances = new ArrayList<ArrayList<String>>()

        while (iterNext && ++colNum) {
            int emptyCount = 0
            List<String> instance = new ArrayList<String>()
            for (int rowNum = 5; rowNum < 42; rowNum += 2) {
                Row row = sheet.getRow(rowNum)
                if (!row) {
                    iterNext = Boolean.FALSE
                    break
                }
                Cell cell = row.getCell(colNum)
                if (!cell) {
                    emptyCount++
                    instance.add("")
                    continue
                }

                switch (cell.getCellType()) {
                    case Cell.CELL_TYPE_STRING:
                        instance.add(cell.getRichStringCellValue().getString())
                        break
                    case Cell.CELL_TYPE_NUMERIC:
                        if (DateUtil.isCellDateFormatted(cell)) {
                            instance.add(cell.getDateCellValue().format("MM/dd/yyyy"))
                        } else {
                            instance.add(cell.getNumericCellValue().toInteger().toString())
                        }
                        break
                    case Cell.CELL_TYPE_FORMULA:
                        instance.add(cell.getCellFormula().trim())
                        break
                    case Cell.CELL_TYPE_BLANK:
                        emptyCount++
                        instance.add("")
                        break
                    default:
                        flash.alerts << "Undefined Cell Type at " + new CellReference(rowNum, colNum).formatAsString()
                        println "CELL_TYPE_DEFAULT at " + new CellReference(rowNum, colNum).formatAsString()
                }
            }

            if (emptyCount == 19) iterNext = Boolean.FALSE
            if (iterNext) {
                if (checkValid(instance, allInstances.size(), flash))
                    allInstances.add(instance)
                else {
                    allInstances.clear()
                    return allInstances
                }
            }
        }

        if (!allInstances.size())
            flash.alerts << "No Instance in the Spreadsheet Uploaded!"
        return allInstances
    }



    def saveToDatabase(List<List<String>> allInstances, String spreadsheetName, FlashScope flash) {

        for (List<String> instance in allInstances) {
            def type = RidLibraryUnit.findByName(instance.get(0))
            def t = new RidConsTransaction(staffPennkey: instance.get(2), userQuestion: instance.get(17),
                    dateOfConsultation: new SimpleDateFormat("MM/dd/yyyy").parse(instance.get(1)),
                    interactOccurrences: Integer.valueOf(instance.get(11)).intValue(),
                    prepTime: Integer.valueOf(instance.get(6)).intValue(),
                    eventLength: Integer.valueOf(instance.get(7)).intValue(),
                    notes: instance.get(18), facultySponsor: instance.get(15), courseName: instance.get(12),
                    courseNumber: instance.get(14), userName: instance.get(8),
                    department: RidDepartment.findByName(instance.get(13)),
                    courseSponsor: RidCourseSponsor.findByName(instance.get(16)),
                    userGoal: RidUserGoal.findByNameAndRidLibraryUnit(instance.get(5), type),
                    modeOfConsultation: RidModeOfConsultation.findByNameAndRidLibraryUnit(instance.get(3), type),
                    rank: RidRank.findByName(instance.get(9)),
                    serviceProvided: RidServiceProvided.findByNameAndRidLibraryUnit(instance.get(4), type),
                    school: RidSchool.findByName(instance.get(10)),
                    ridLibraryUnit: type,
                    spreadsheetName: spreadsheetName
            )

            try {
                if (!t.save(flush: true)) {
                    flash.alerts << t.errors
                    return false
                }
            } catch (Exception e) {
                flash.alerts << e.message
                return false
            }
        }

        return true
    }

    def exportAsFile(List ridTransactionList, String transType) {

        ClassPathResource resource = new ClassPathResource('spreadsheet/Transaction_List.xlsx')
        Workbook wb = WorkbookFactory.create(resource.getFile().newInputStream())
        Sheet sheet = wb.getSheetAt(0)

        CellStyle red_bold = wb.createCellStyle()
        Font ft = wb.createFont()
        ft.boldweight = Font.BOLDWEIGHT_BOLD
        ft.color = Font.COLOR_RED
        red_bold.font = ft

        int rowNum = 1
        if (transType.equals("consultation")) {
            for (RidConsTransaction rid in ridTransactionList) {
                Row row = sheet.createRow(rowNum++)
                row.createCell(0).setCellValue(rid.ridLibraryUnit.name)
                row.getCell(0).setCellStyle(red_bold)
                row.createCell(1).setCellValue(rid.dateOfConsultation.format("MM/dd/yyyy"))
                row.createCell(2).setCellValue(rid.staffPennkey)
                row.createCell(3).setCellValue(rid.modeOfConsultation.name)
                row.createCell(4).setCellValue(rid.serviceProvided.name)
                row.createCell(5).setCellValue(rid.userGoal.name)
                row.createCell(6).setCellValue(String.valueOf(rid.prepTime))
                row.createCell(7).setCellValue(String.valueOf(rid.eventLength))
                row.createCell(8).setCellValue(rid.userName)
                row.createCell(9).setCellValue(rid.rank.name)
                row.createCell(10).setCellValue(rid.school.name)
                row.createCell(11).setCellValue(String.valueOf(rid.interactOccurrences))
                row.createCell(12).setCellValue(rid.courseName)
                row.createCell(13).setCellValue(rid.department.name)
                row.createCell(14).setCellValue(rid.courseNumber)
                row.createCell(15).setCellValue(rid.facultySponsor)
                row.createCell(16).setCellValue(rid.courseSponsor.name)
                row.createCell(17).setCellValue(rid.userQuestion)
                row.createCell(18).setCellValue(rid.notes)

                for (int c = 0; c < 19; c++)
                    row.getCell(c).setCellType(Cell.CELL_TYPE_STRING)
            }
        } else {
            //TODO: Add spreadsheet for InsTransactions
            //Placeholder spreadsheet generation
            for (RidInsTransaction rid in ridTransactionList) {
                Row row = sheet.createRow(rowNum++)
                row.createCell(0).setCellValue(rid.ridLibraryUnit.name)
                row.getCell(0).setCellStyle(red_bold)
                row.createCell(1).setCellValue(rid.dateOfInstruction.format("MM/dd/yyyy"))
                row.createCell(2).setCellValue(rid.staffPennkey)
                row.createCell(3).setCellValue(String.valueOf(rid.prepTime))
                row.createCell(4).setCellValue(String.valueOf(rid.eventLength))
                row.createCell(5).setCellValue(rid.userName)
                row.createCell(6).setCellValue(rid.rank.name)
                row.createCell(7).setCellValue(rid.school.name)
                row.createCell(8).setCellValue(String.valueOf(rid.attendanceTotal))
                row.createCell(9).setCellValue(rid.courseName)
                row.createCell(10).setCellValue(rid.department.name)
                row.createCell(11).setCellValue(rid.courseNumber)
                row.createCell(12).setCellValue(rid.facultySponsor)
                row.createCell(13).setCellValue(rid.notes)

                for (int c = 0; c < 14; c++)
                    row.getCell(c).setCellType(Cell.CELL_TYPE_STRING)
            }
        }
        return wb
    }
}

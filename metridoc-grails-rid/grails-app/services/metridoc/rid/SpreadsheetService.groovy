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

class SpreadsheetService extends ValidateSpreadsheetService {
    static final def CONS_ROW_NUM = 44
    final def INS_ROW_NUM = 46

    //Workbook generated using convertToWorkbook in ValidateSpreadsheetService
    def getInstancesFromSpreadsheet(Workbook wb, FlashScope flash, String type) {

        Sheet sheet
        int colNum = 1
        Boolean iterNext = Boolean.TRUE
        ArrayList<ArrayList<String>> allInstances = new ArrayList<ArrayList<String>>()
        def rowMax
        if (type == "cons") {
            rowMax = CONS_ROW_NUM
            sheet = wb.getSheetAt(0)

        } else {
            rowMax = INS_ROW_NUM;
            sheet = wb.getSheetAt(1)
        }
        while (iterNext && ++colNum) {
            int emptyCount = 0
            ArrayList<String> instance = new ArrayList<String>()
            for (int rowNum = 5; rowNum < rowMax; rowNum += 2) {
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

            if (emptyCount == (rowMax - 4) / 2) iterNext = Boolean.FALSE
            if (iterNext) {
                def validity
                if (type == "cons") {
                    validity = checkValidCons(instance, allInstances.size(), flash)
                } else {
                    validity = checkValidIns(instance, allInstances.size(), flash)
                }
                if (validity == "empty") {
                    allInstances.clear()
                    return allInstances
                } else if (validity) {
                    allInstances.add(instance)
                } else {
                    allInstances.clear()
                    return allInstances
                }
            }
        }

        return allInstances
    }

    def getAllInstances(Workbook wb, FlashScope flash, skipCons, skipIns) {
        def consInstances = null
        if (!skipCons) {
            consInstances = getInstancesFromSpreadsheet(wb, flash, "cons")

        }
        def insInstances = null
        if (!skipIns) {
            insInstances = getInstancesFromSpreadsheet(wb, flash, "ins")
        }
        def allInstances = new TreeMap<String, ArrayList<ArrayList<String>>>()
        if (!consInstances?.size() && !insInstances?.size()) {
            allInstances = null
            if (!skipCons) flash.alerts << "Invalid consultation transactions found"
            if (!skipIns) flash.alerts << "Invalid instructional transactions found"
            if (!skipCons && !skipIns) flash.alerts << "No Instances in the Spreadsheet Uploaded!"
        } else if (!insInstances?.size()) {
            allInstances.put("cons", consInstances)
            if (!skipIns) flash.alerts << "Invalid instructional transactions found"
        } else if (!consInstances?.size()) {
            allInstances.put("ins", insInstances)
            if (!skipCons) flash.alerts << "Invalid consultation transactions found"
        } else {
            allInstances.put("cons", consInstances)
            allInstances.put("ins", insInstances)
        }
        return allInstances


    }


    def saveToDatabase(TreeMap<String, ArrayList<ArrayList<String>>> allInstances, String spreadsheetName, FlashScope flash) {
        def consInstances = allInstances.get("cons") ?: null
        def insInstances = allInstances.get("ins") ?: null
        if (consInstances) {
            for (ArrayList<String> instance in consInstances) {
                def type = RidLibraryUnit.findByName(instance.get(0))
                def t = new RidConsTransaction(staffPennkey: instance.get(2), userQuestion: instance.get(18),
                        expertise: RidExpertise.findByName(instance.get(17)),
                        dateOfConsultation: new SimpleDateFormat("MM/dd/yyyy").parse(instance.get(1)),
                        interactOccurrences: Integer.valueOf(instance.get(11)).intValue(),
                        prepTime: Integer.valueOf(instance.get(6)).intValue(),
                        eventLength: Integer.valueOf(instance.get(7)).intValue(),
                        notes: instance.get(19), facultySponsor: instance.get(15), courseName: instance.get(12),
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
        }

        if (insInstances) {
            for (ArrayList<String> instance in insInstances) {
                def type = RidLibraryUnit.findByName(instance.get(0))
                def t = new RidInsTransaction(
                        dateOfInstruction: new SimpleDateFormat("MM/dd/yyyy").parse(instance.get(1)),
                        instructorPennkey: instance.get(2),
                        coInstructorPennkey: instance.get(3),
                        sessionType: RidSessionType.findByNameAndRidLibraryUnit(instance.get(4), type),
                        instructionalMaterials: RidInstructionalMaterials.findByNameAndRidLibraryUnit(instance.get(5), type),
                        location: RidLocation.findByName(instance.get(6)),
                        prepTime: Integer.valueOf(instance.get(7)).intValue(),
                        eventLength: Integer.valueOf(instance.get(8)).intValue(),
                        userName: instance.get(9),
                        school: RidSchool.findByName(instance.get(10)),
                        attendanceTotal: Integer.valueOf(instance.get(11)).intValue(),
                        sequenceName: instance.get(12),
                        sequenceUnit: Integer.valueOf(instance.get(13)).intValue(),
                        courseName: instance.get(14),
                        department: RidDepartment.findByName(instance.get(15)),
                        courseNumber: instance.get(16),
                        facultySponsor: instance.get(17),
                        requestor: instance.get(18),
                        sessionDescription: instance.get(19),
                        notes: instance.get(20),
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

        int rowNum = 0
        if (transType.equals("consultation")) {
            def consHeaders = ["Library Unit", "Date of Consultation (mm/dd/yyyy)", "Staff Pennkey", "Consultation Mode",
                               "Service Provided", "User Goal", "Prep Time (min)", "Event Length (min)", "User Name",
                               "Rank", "School", "Interact Occurrences", "Course Name", "Course Number", "Department",
                               "Faculty Sponsor", "Course Sponsor", "Expertise", "User Question", "Notes"]
            Row row = sheet.createRow(rowNum++)
            def cellnum = 0
            for (h in consHeaders) {
                row.createCell(cellnum).setCellValue(h)
                cellnum++
            }
            for (RidConsTransaction rid in ridTransactionList) {
                row = sheet.createRow(rowNum++)
                row.createCell(0).setCellValue(rid.ridLibraryUnit.name)
                row.getCell(0).setCellStyle(red_bold)
                row.createCell(1).setCellValue(rid.dateOfConsultation.format("MM/dd/yyyy"))
                row.createCell(2).setCellValue(rid.staffPennkey)
                row.createCell(3).setCellValue(rid.modeOfConsultation.name)
                row.createCell(4).setCellValue(rid.serviceProvided.name)
                row.createCell(5).setCellValue(rid?.userGoal?.name ?: "")
                row.createCell(6).setCellValue(String.valueOf(rid.prepTime))
                row.createCell(7).setCellValue(String.valueOf(rid.eventLength))
                row.createCell(8).setCellValue(rid?.userName ?: "")
                row.createCell(9).setCellValue(rid.rank.name)
                row.createCell(10).setCellValue(rid?.school?.name ?: "")
                row.createCell(11).setCellValue(String.valueOf(rid.interactOccurrences))
                row.createCell(12).setCellValue(rid?.courseName ?: "")
                row.createCell(13).setCellValue(rid?.courseNumber ?: "")
                row.createCell(14).setCellValue(rid?.department?.name ?: "")
                row.createCell(15).setCellValue(rid?.facultySponsor ?: "")
                row.createCell(16).setCellValue(rid?.courseSponsor?.name ?: "")
                row.createCell(17).setCellValue(rid?.expertise?.name ?: "")
                row.createCell(18).setCellValue(rid?.userQuestion ?: "")
                row.createCell(19).setCellValue(rid?.notes ?: "")

                for (int c = 0; c < 19; c++)
                    row.getCell(c).setCellType(Cell.CELL_TYPE_STRING)
            }
        } else {
            def insHeaders = ["Library Unit", "Date of Instruction (mm/dd/yyyy)", "Instructor Pennkey", "Co-instructor Pennkey",
                              "Session Type", "Instructional Materials", "Location", "Prep Time (min)", "Event Length (min)",
                              "School", "Attendance", "Sequence Name", "Module Number", "Course Name", "Course Number", "Department",
                              "Faculty Sponsor", "Requestor", "Session Description", "Notes"]
            Row row = sheet.createRow(rowNum++)
            def cellnum = 0
            for (h in insHeaders) {
                row.createCell(cellnum).setCellValue(h)
                cellnum++
            }
            for (RidInsTransaction rid in ridTransactionList) {
                row = sheet.createRow(rowNum++)
                row.createCell(0).setCellValue(rid.ridLibraryUnit.name)
                row.getCell(0).setCellStyle(red_bold)
                row.createCell(1).setCellValue(rid.dateOfInstruction.format("MM/dd/yyyy"))
                row.createCell(2).setCellValue(rid.instructorPennkey)
                row.createCell(3).setCellValue(rid?.coInstructorPennkey ?: "")
                row.createCell(4).setCellValue(rid.sessionType.name)
                row.createCell(5).setCellValue(rid?.instructionalMaterials?.name ?: "")
                row.createCell(6).setCellValue(rid.location.name)
                row.createCell(7).setCellValue(String.valueOf(rid.prepTime))
                row.createCell(8).setCellValue(String.valueOf(rid.eventLength))
                row.createCell(9).setCellValue(rid?.userName ?: "")
                row.createCell(10).setCellValue(rid?.school?.name ?: "")
                row.createCell(11).setCellValue(String.valueOf(rid.attendanceTotal))
                row.createCell(12).setCellValue(rid?.sequenceName ?: "")
                row.createCell(13).setCellValue(String.valueOf(rid?.sequenceUnit ?: ""))
                row.createCell(14).setCellValue(rid?.courseName ?: "")
                row.createCell(15).setCellValue(rid?.department?.name ?: "")
                row.createCell(16).setCellValue(rid?.courseNumber ?: "")
                row.createCell(17).setCellValue(rid?.facultySponsor ?: "")
                row.createCell(18).setCellValue(rid?.requestor ?: "")
                row.createCell(19).setCellValue(rid?.sessionDescription ?: "")
                row.createCell(20).setCellValue(rid?.notes ?: "")

                for (int c = 0; c < 20; c++)
                    row.getCell(c).setCellType(Cell.CELL_TYPE_STRING)
            }
        }
        return wb
    }
}

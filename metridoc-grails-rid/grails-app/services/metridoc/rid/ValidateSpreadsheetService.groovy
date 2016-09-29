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
import org.codehaus.groovy.grails.web.servlet.FlashScope
import org.springframework.web.multipart.MultipartFile

import java.text.SimpleDateFormat

class ValidateSpreadsheetService {

    def convertToWorkbook(MultipartFile uploadedFile) {
        Workbook wb = WorkbookFactory.create(uploadedFile.inputStream)
        return wb
    }

    def checkSpreadsheetFormat(Workbook wb) {

        Sheet sheet = wb.getSheetAt(0)
        int colNum = 1

        List<String> itemNames = new ArrayList<String>()
        for (int rowNum = 5; rowNum < 42; rowNum += 2) {
            Row row = sheet.getRow(rowNum)
            if (!row) return false
            Cell cell = row.getCell(colNum)
            if (!cell) return false

            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_STRING:
                    itemNames.add(cell.getRichStringCellValue().getString())
                    break
                default:
                    println "CELL_TYPE_DEFAULT at " + new CellReference(rowNum, colNum).formatAsString()
                    return false
            }
        }

        List<String> validNames = Arrays.asList('Library Unit', 'Date of Consultation (mm/dd/yyyy)', 'Staff Pennkey',
                'Consultation Mode', 'Service Provided', 'User Goal', 'Prep Time (enter in minutes)',
                'Event Length (enter in minutes)', 'User Name', 'Rank', 'School', 'Interact Occurrences', 'Course Name',
                'Department', 'Course Number', 'Faculty Sponsor', 'Course Sponsor', 'User Question',
                'Notes'
        )

        if (validNames.size() != itemNames.size()) return false
        for (int i = 0; i < itemNames.size(); i++) {
            if (!itemNames.get(i).trim().toLowerCase().equals(validNames.get(i).trim().toLowerCase())) return false
        }
        return true
    }


    def checkValidCons(List<String> instance, int count, FlashScope flash) {
        boolean noErrors = true
        boolean allEmpty = true
        ArrayList spreadsheetErrors = new ArrayList()
        def numberNotBlank = 0
        for (int i = 0; i < instance.size(); i++) {

            CellReference cellRef = new CellReference(5 + i * 2, count + 2)
            switch (i) {
                case 0:
                    if (instance.get(i).trim().empty || instance.get(i).trim() == "free text") {
                        spreadsheetErrors.add("Cons instance ${count+1}: Library Unit Cannot be Empty at " + cellRef.formatAsString())
                        noErrors = false
                        break

                    }
                    if (!RidLibraryUnit.findByName(instance.get(i).trim())) {
                        spreadsheetErrors.add("Cons instance ${count+1}: Invalid Library at " + cellRef.formatAsString())
                        noErrors = false
                        allEmpty = false
                    }
                    break

                case 1:
                    if (instance.get(i).trim().empty || instance.get(i).trim() == "free text") {
                        spreadsheetErrors.add("Cons instance ${count+1}: Date of Consultation Cannot be Empty at " + cellRef.formatAsString())
                        noErrors = false
                        break
                    }
                    def sheetDate = instance.get(i).trim()
                    def reformattedSheetDate
                    try {
                        reformattedSheetDate = sheetDate.substring(0, 2) + sheetDate.substring(3, 5) + sheetDate.substring(6)
                        new SimpleDateFormat("MM/dd/yyyy").parse(instance.get(i).trim())

                    } catch (Exception e) {
                        spreadsheetErrors.add("Cons instance ${count+1}: Invalid Date Format at " + cellRef.formatAsString())
                        noErrors = false
                        allEmpty = false
                        break
                    }
                    if(validateDate(reformattedSheetDate)!="good"){
                        spreadsheetErrors.add("Cons instance ${count+1}: "+validateDate(reformattedSheetDate)+" "+cellRef.formatAsString())
                        noErrors = false
                        allEmpty = false
                    }
                    else{
                        allEmpty = false
                    }
                    break

                case 2:
                    if (instance.get(i).trim().empty || instance.get(i).trim() == "free text") {
                        spreadsheetErrors.add("Cons instance ${count+1}: Stuff Pennkey Cannot be Empty at " + cellRef.formatAsString())
                        noErrors = false
                        break
                    }
                    else if (instance.get(i).trim().length() > 100) {
                        spreadsheetErrors.add("Cons instance ${count+1}: Stuff Pennkey Too Long at " + cellRef.formatAsString())
                        noErrors = false
                        allEmpty = false
                    }
                    else{
                        allEmpty = false
                    }

                    break

                case 3:
                    if (instance.get(i).trim().empty || instance.get(i).trim() == "free text") {
                        spreadsheetErrors.add("Cons instance ${count+1}: Mode of Consultation Cannot be Empty at " + cellRef.formatAsString())
                        noErrors = false
                        break
                    }
                    else if (!RidModeOfConsultation.findByName(instance.get(i).trim())) {
                        spreadsheetErrors.add("Cons instance ${count+1}: Invalid Mode of Consultation at " + cellRef.formatAsString())
                        noErrors = false
                        allEmpty = false
                    }
                    else if (!RidModeOfConsultation.findByNameAndRidLibraryUnit(
                            instance.get(i).trim(), RidLibraryUnit.findByName(instance.get(0).trim()))) {
                        spreadsheetErrors.add("Cons instance ${count+1}: Mode of Consultation at " + cellRef.formatAsString() +
                                " does NOT match the Report Type " + instance.get(0).trim())
                        noErrors = false
                        allEmpty = false
                    }
                    else{
                        allEmpty = false
                    }
                    break

                case 4:
                    if (instance.get(i).trim().empty || instance.get(i).trim() == "free text") {
                        spreadsheetErrors.add("Cons instance ${count+1}: Service Provided Cannot be Empty at " + cellRef.formatAsString())
                        noErrors = false
                        break
                    }
                    else if (!RidServiceProvided.findByName(instance.get(i).trim())) {
                        spreadsheetErrors.add("Cons instance ${count+1}: Invalid Service Provided at " + cellRef.formatAsString() + instance.get(i))
                        noErrors = false
                    }
                    else if (!RidServiceProvided.findByNameAndRidLibraryUnit(
                            instance.get(i).trim(), RidLibraryUnit.findByName(instance.get(0).trim()))) {
                        spreadsheetErrors.add("Cons instance ${count+1}: Service Provided at " + cellRef.formatAsString() +
                                " does NOT match the Report Type " + instance.get(0).trim())
                        noErrors = false
                    }
                    else{
                        allEmpty = false
                    }
                    break

                case 5:
                    if (instance.get(i).trim().empty || instance.get(i).trim() == "free text") {
                        break
                    }
                    else if (!instance.get(i).trim().empty && !RidUserGoal.findByName(instance.get(i).trim())) {
                        spreadsheetErrors.add("Cons instance ${count+1}: Invalid User Goal at " + cellRef.formatAsString())
                        noErrors = false
                        allEmpty = false
                    }
                    else if (!instance.get(i).trim().empty && !RidUserGoal.findByNameAndRidLibraryUnit(
                            instance.get(i).trim(), RidLibraryUnit.findByName(instance.get(0).trim()))) {
                        spreadsheetErrors.add("Cons instance ${count+1}: User Goal at " + cellRef.formatAsString() +
                                " does NOT match the Report Type " + instance.get(0).trim())
                        noErrors = false
                    }
                    else{
                        allEmpty = false
                    }
                    break

                case 6:
                    if (instance.get(i).trim().empty || instance.get(i).trim() == "free text") {
                        spreadsheetErrors.add("Cons instance ${count+1}: Prep Time Cannot be Empty at " + cellRef.formatAsString())
                        noErrors = false
                        break
                    }
                    try {
                        if (Integer.valueOf(instance.get(i).trim()) < 0) {
                            spreadsheetErrors.add("Cons instance ${count+1}: Negative Prep Time at " + cellRef.formatAsString())
                            noErrors = false
                            allEmpty = false
                        }
                    } catch (Exception e) {
                        spreadsheetErrors.add("Cons instance ${count+1}: Invalid Format for Prep Time at " + cellRef.formatAsString())
                        noErrors = false
                        allEmpty = false
                        break
                    }
                    allEmpty = false
                    break

                case 7:
                    if (instance.get(i).trim().empty || instance.get(i).trim() == "free text") {
                        spreadsheetErrors.add("Cons instance ${count+1}: Event Length Cannot be Empty at " + cellRef.formatAsString())
                        noErrors = false
                        break
                    }
                    try {
                        if (Integer.valueOf(instance.get(i).trim()) < 0) {
                            spreadsheetErrors.add("Cons instance ${count+1}: Negative Event Length at " + cellRef.formatAsString())
                            noErrors = false
                            allEmpty = false
                        }
                    } catch (Exception e) {
                        spreadsheetErrors.add("Cons instance ${count+1}: Invalid Format for Event Length at " + cellRef.formatAsString())
                        noErrors = false
                        allEmpty = false
                        break
                    }
                    allEmpty = false
                    break

                case 8:
                    if (instance.get(i).trim().empty || instance.get(i).trim() == "free text"){
                        break
                    }
                    if (instance.get(i).trim().length() > 50) {
                        spreadsheetErrors.add("Cons instance ${count+1}: User Name Too Long at " + cellRef.formatAsString())
                        noErrors = false
                        allEmpty = false
                    }
                    allEmpty = false
                    break

                case 9:
                    if (instance.get(i).trim().empty || instance.get(i).trim() == "free text") {
                        spreadsheetErrors.add("Cons instance ${count+1}: Rank Cannot be Empty at " + cellRef.formatAsString())
                        noErrors = false
                        break
                    }
                    if (!RidRank.findByName(instance.get(i).trim())) {
                        spreadsheetErrors.add("Cons instance ${count+1}: Invalid Rank at " + cellRef.formatAsString())
                        noErrors = false
                        allEmpty = false
                    }
                    allEmpty = false
                    break

                case 10:
                    if (instance.get(i).trim().empty || instance.get(i).trim() == "free text") {
                        break
                    }
                    if (!RidSchool.findByName(instance.get(i).trim())) {
                        spreadsheetErrors.add("Cons instance ${count+1}: Invalid School at " + cellRef.formatAsString())
                        noErrors = false
                        allEmpty = false
                    }
                    allEmpty = false
                    break

                case 11:
                    if (instance.get(i).trim().empty || instance.get(i).trim() == "free text") {
                        spreadsheetErrors.add("Cons instance ${count+1}: Interact Occurrences Cannot be Empty at " + cellRef.formatAsString())
                        noErrors = false
                        break
                    }
                    try {
                        if (Integer.valueOf(instance.get(i).trim()) < 0) {
                            spreadsheetErrors.add("Cons instance ${count+1}: Negative Interact Occurrences at " + cellRef.formatAsString())
                            noErrors = false
                            allEmpty = false
                        }
                        else if (Integer.valueOf(instance.get(i).trim()) > 50) {
                            spreadsheetErrors.add("Cons instance ${count+1}: Interact Occurrences Too Large at " + cellRef.formatAsString())
                            noErrors = false
                            allEmpty = false
                        }
                    } catch (Exception e) {
                        spreadsheetErrors.add("Cons instance ${count+1}: Invalid Format for Interact Occurrences at " + cellRef.formatAsString())
                        noErrors = false
                        allEmpty = false
                        break
                    }
                    allEmpty = false
                    break

                case 12:
                    if (instance.get(i).trim().empty || instance.get(i).trim() == "free text"){
                        break
                    }
                    if (instance.get(i).trim().length() > 100) {
                        spreadsheetErrors.add("Cons instance ${count+1}: Course Name Too Long at " + cellRef.formatAsString())
                        noErrors = false
                        allEmpty = false
                    }
                    allEmpty = false
                    break

                case 13:
                    if (instance.get(i).trim().empty || instance.get(i).trim() == "free text"){
                        break
                    }
                    if (!instance.get(i).trim().empty && !RidDepartment.findByName(instance.get(i).trim())) {
                        spreadsheetErrors.add("Cons instance ${count+1}: Invalid Department at " + cellRef.formatAsString())
                        noErrors = false
                        allEmpty = false
                    }
                    allEmpty = false
                    break

                case 14:
                    if (instance.get(i).trim().empty || instance.get(i).trim() == "free text"){
                        break
                    }
                    if (instance.get(i).trim().length() > 100) {
                        spreadsheetErrors.add("Cons instance ${count+1}: Course Number Too Long at " + cellRef.formatAsString())
                        noErrors = false
                        allEmpty = false
                    }
                    allEmpty = false
                    break

                case 15:
                    if (instance.get(i).trim().empty || instance.get(i).trim() == "free text"){
                        break
                    }
                    if (instance.get(i).trim().length() > 100) {
                        spreadsheetErrors.add("Cons instance ${count+1}: Faculty Sponsor Too Long at " + cellRef.formatAsString())
                        noErrors = false
                        allEmpty = false
                    }
                    allEmpty = false
                    break

                case 16:
                    if (instance.get(i).trim().empty || instance.get(i).trim() == "free text"){
                        break
                    }
                    if (!instance.get(i).trim().empty && !RidCourseSponsor.findByName(instance.get(i).trim())) {
                        spreadsheetErrors.add("Cons instance ${count+1}: Invalid Course Sponsor at " + cellRef.formatAsString())
                        noErrors = false
                        allEmpty = false
                    }
                    allEmpty = false
                    break

                case 17:
                    if (instance.get(i).trim().empty || instance.get(i).trim() == "free text") {
                        break
                    }
                    if (!RidExpertise.findByName(instance.get(i).trim())) {
                        spreadsheetErrors.add("Cons instance ${count+1}: Invalid Expertise at " + cellRef.formatAsString())
                        noErrors = false
                        allEmpty = false
                    }
                    allEmpty = false
                    break

                case 18:
                    if (instance.get(i).trim().empty || instance.get(i).trim() == "free text"){
                        break
                    }
                    if (!instance.get(i).empty && instance.get(i).trim().length() > 500) {
                        spreadsheetErrors.add("Cons instance ${count+1}: User Question Too Long at " + cellRef.formatAsString())
                        noErrors = false
                        allEmpty = false
                    }
                    allEmpty = false
                    break

                case 19:
                    if (instance.get(i).trim().empty || instance.get(i).trim() == "free text"){
                        break
                    }
                    if (instance.get(i).trim().length() > 500) {
                        spreadsheetErrors.add("Cons instance ${count+1}: Notes Too Long at " + cellRef.formatAsString())
                        noErrors = false
                        allEmpty = false
                    }
                    allEmpty = false
                    break
                default:
                    noErrors = false
            }
            if (!allEmpty){
                numberNotBlank=i
            }
        }
        if (allEmpty && numberNotBlank==0){
            return "empty"
        }
        def errorCount = 0
        for (error in spreadsheetErrors) {
            if (errorCount <= 3){
                flash.alerts << error
            }
            errorCount++
        }
        if (errorCount > 3){
            flash.alerts << "And ${errorCount - 3} more errors"
        }
        return noErrors
    }

    def checkValidIns(List<String> instance, int count, FlashScope flash) {
        boolean noErrors = true
        boolean allEmpty = true
        ArrayList spreadsheetErrors = new ArrayList()
        for (int i = 0; i < instance.size(); i++) {
            CellReference cellRef = new CellReference(5 + i * 2, count + 2)
            switch (i) {
                case 0:
                    if (instance.get(i).trim().empty || instance.get(i).trim() == "free text") {
                        spreadsheetErrors.add("Ins instance ${count+1}: Library Unit Cannot be Empty at " + cellRef.formatAsString())
                        noErrors = false
                        break

                    }
                    if (!RidLibraryUnit.findByName(instance.get(i).trim())) {
                        spreadsheetErrors.add("Ins instance ${count+1}: Invalid Library at " + cellRef.formatAsString())
                        noErrors = false
                        allEmpty = false
                    }
                    break

                case 1:
                    if (instance.get(i).trim().empty || instance.get(i).trim() == "free text") {
                        spreadsheetErrors.add("Ins instance ${count+1}: Date of Instruction Cannot be Empty at " + cellRef.formatAsString())
                        noErrors = false
                        break
                    }
                    def sheetDate = instance.get(i).trim()
                    def reformattedSheetDate
                    try {
                        reformattedSheetDate = sheetDate.substring(0, 2) + sheetDate.substring(3, 5) + sheetDate.substring(6)
                        new SimpleDateFormat("MM/dd/yyyy").parse(instance.get(i).trim())

                    } catch (Exception e) {
                        spreadsheetErrors.add("Ins instance ${count+1}: Invalid Date Format at " + cellRef.formatAsString())
                        noErrors = false
                        allEmpty = false
                        break
                    }
                    if(validateDate(reformattedSheetDate)!="good"){
                        spreadsheetErrors.add("Ins instance ${count+1}: "+validateDate(reformattedSheetDate)+" "+cellRef.formatAsString())
                        noErrors = false
                        allEmpty = false
                    }
                    else{
                        allEmpty = false
                    }
                    break

                case 2:
                    if (instance.get(i).trim().empty || instance.get(i).trim() == "free text") {
                        spreadsheetErrors.add("Ins instance ${count+1}: Instructor Pennkey Cannot be Empty at " + cellRef.formatAsString())
                        noErrors = false
                        break
                    }
                    else if (instance.get(i).trim().length() > 100) {
                        spreadsheetErrors.add("Ins instance ${count+1}: Instructor Pennkey Too Long at " + cellRef.formatAsString())
                        noErrors = false
                        allEmpty = false
                    }
                    else{
                        allEmpty = false
                    }

                    break

                case 3:
                    if (instance.get(i).trim().empty || instance.get(i).trim() == "free text"){
                        break
                    }
                    else if (instance.get(i).trim().length() > 100) {
                        spreadsheetErrors.add("Ins instance ${count+1}: Co Instructor Pennkey Too Long at " + cellRef.formatAsString())
                        noErrors = false
                        allEmpty = false
                    }
                    else{
                        allEmpty = false
                    }

                    break

                case 4:
                    if (instance.get(i).trim().empty || instance.get(i).trim() == "free text") {
                        spreadsheetErrors.add("Ins instance ${count+1}: Session Type Cannot be Empty at " + cellRef.formatAsString())
                        noErrors = false
                        break
                    }
                    else if (!RidSessionType.findByName(instance.get(i).trim())) {
                        spreadsheetErrors.add("Ins instance ${count+1}: Invalid Session Type at " + cellRef.formatAsString())
                        noErrors = false
                        allEmpty = false
                    }
                    else if (!RidSessionType.findByNameAndRidLibraryUnit(
                            instance.get(i).trim(), RidLibraryUnit.findByName(instance.get(0).trim()))) {
                        spreadsheetErrors.add("Ins instance ${count+1}: Session Type at " + cellRef.formatAsString() +
                                " does NOT match the Report Type " + instance.get(0).trim())
                        noErrors = false
                        allEmpty = false
                    }
                    else{
                        allEmpty = false
                    }
                    break

                case 5:
                    if (instance.get(i).trim().empty || instance.get(i).trim() == "free text"){
                        break
                    }
                    else if (!RidInstructionalMaterials.findByName(instance.get(i).trim())) {
                        spreadsheetErrors.add("Ins instance ${count+1}: Invalid Instructional Materials at " + cellRef.formatAsString() + instance.get(i))
                        noErrors = false
                    }
                    else if (!RidInstructionalMaterials.findByNameAndRidLibraryUnit(
                            instance.get(i).trim(), RidLibraryUnit.findByName(instance.get(0).trim()))) {
                        spreadsheetErrors.add("Ins instance ${count+1}: Instructional Materials at " + cellRef.formatAsString() +
                                " does NOT match the Report Type " + instance.get(0).trim())
                        noErrors = false
                    }
                    else{
                        allEmpty = false
                    }
                    break



                case 6:
                    if (instance.get(i).trim().empty || instance.get(i).trim() == "free text") {
                        spreadsheetErrors.add("Ins instance ${count+1}: Location Cannot be Empty at " + cellRef.formatAsString())
                        noErrors = false
                        break
                    }
                    else if (!RidLocation.findByName(instance.get(i).trim())) {
                        spreadsheetErrors.add("Ins instance ${count+1}: Invalid Location at " + cellRef.formatAsString())
                        noErrors = false
                        allEmpty = false
                    }
                    else if (!RidLocation.findByNameAndRidLibraryUnit(
                            instance.get(i).trim(), RidLibraryUnit.findByName(instance.get(0).trim()))) {
                        spreadsheetErrors.add("Ins instance ${count+1}: Location " + cellRef.formatAsString() +
                                " does NOT match the Report Type " + instance.get(0).trim())
                        noErrors = false
                        allEmpty = false
                    }
                    else{
                        allEmpty = false
                    }
                    break

                case 7:
                    if (instance.get(i).trim().empty || instance.get(i).trim() == "free text") {
                        spreadsheetErrors.add("Ins instance ${count+1}: Prep Time Cannot be Empty at " + cellRef.formatAsString())
                        noErrors = false
                        break
                    }
                    try {
                        if (Integer.valueOf(instance.get(i).trim()) < 0) {
                            spreadsheetErrors.add("Ins instance ${count+1}: Negative Prep Time at " + cellRef.formatAsString())
                            noErrors = false
                            allEmpty = false
                        }
                    } catch (Exception e) {
                        spreadsheetErrors.add("Ins instance ${count+1}: Invalid Format for Prep Time at " + cellRef.formatAsString())
                        noErrors = false
                        allEmpty = false
                        break
                    }
                    allEmpty = false
                    break

                case 8:
                    if (instance.get(i).trim().empty || instance.get(i).trim() == "free text") {
                        spreadsheetErrors.add("Ins instance ${count+1}: Event Length Cannot be Empty at " + cellRef.formatAsString())
                        noErrors = false
                        break
                    }
                    try {
                        if (Integer.valueOf(instance.get(i).trim()) < 0) {
                            spreadsheetErrors.add("Ins instance ${count+1}: Negative Event Length at " + cellRef.formatAsString())
                            noErrors = false
                            allEmpty = false
                        }
                    } catch (Exception e) {
                        spreadsheetErrors.add("Ins instance ${count+1}: Invalid Format for Event Length at " + cellRef.formatAsString())
                        noErrors = false
                        allEmpty = false
                        break
                    }
                    allEmpty = false
                    break



                case 9:
                    if (instance.get(i).trim().empty || instance.get(i).trim() == "free text") {
                        break
                    }
                    if (!RidSchool.findByName(instance.get(i).trim())) {
                        spreadsheetErrors.add("Ins instance ${count+1}: Invalid School at " + cellRef.formatAsString())
                        noErrors = false
                        allEmpty = false
                    }
                    allEmpty = false
                    break

                case 10:
                    if (instance.get(i).trim().empty || instance.get(i).trim() == "free text") {
                        spreadsheetErrors.add("Ins instance ${count+1}: Total Attendance Cannot be Empty at " + cellRef.formatAsString())
                        noErrors = false
                        break
                    }
                    try {
                        if (Integer.valueOf(instance.get(i).trim()) < 0) {
                            spreadsheetErrors.add("Ins instance ${count+1}: Negative Attendance at " + cellRef.formatAsString())
                            noErrors = false
                            allEmpty = false
                        }
                    } catch (Exception e) {
                        spreadsheetErrors.add("Ins instance ${count+1}: Invalid Format for Attendance at " + cellRef.formatAsString())
                        noErrors = false
                        allEmpty = false
                        break
                    }
                    allEmpty = false
                    break

                case 11:
                    if (instance.get(i).trim().empty || instance.get(i).trim() == "free text"){
                        break
                    }
                    if (instance.get(i).trim().length() > 50) {
                        spreadsheetErrors.add("Ins instance ${count+1}: Sequence Name Too Long at " + cellRef.formatAsString())
                        noErrors = false
                        allEmpty = false
                    }
                    allEmpty = false
                    break

                case 12:
                    if (instance.get(i).trim().empty || instance.get(i).trim() == "free text") {
                        break
                    }
                    try {
                        if (Integer.valueOf(instance.get(i).trim()) < 0) {
                            spreadsheetErrors.add("Ins instance ${count+1}: Negative Module Unit at " + cellRef.formatAsString())
                            noErrors = false
                            allEmpty = false
                        }
                    } catch (Exception e) {
                        spreadsheetErrors.add("Ins instance ${count+1}: Invalid Format for Module Unit Number at " + cellRef.formatAsString())
                        noErrors = false
                        allEmpty = false
                        break
                    }
                    allEmpty = false
                    break

                case 13:
                    if (instance.get(i).trim().empty || instance.get(i).trim() == "free text"){
                        break
                    }
                    if (instance.get(i).trim().length() > 50) {
                        spreadsheetErrors.add("Ins instance ${count+1}: Course Name Too Long at " + cellRef.formatAsString())
                        noErrors = false
                        allEmpty = false
                    }
                    allEmpty = false
                    break

                case 14:
                    if (instance.get(i).trim().empty || instance.get(i).trim() == "free text"){
                        break
                    }
                    if (instance.get(i).trim().length() > 100) {
                        spreadsheetErrors.add("Ins instance ${count+1}: Course Number Too Long at " + cellRef.formatAsString())
                        noErrors = false
                        allEmpty = false
                    }
                    allEmpty = false
                    break

                case 15:
                    if (instance.get(i).trim().empty || instance.get(i).trim() == "free text"){
                        break
                    }
                    if (!instance.get(i).trim().empty && !RidDepartment.findByName(instance.get(i).trim())) {
                        spreadsheetErrors.add("Ins instance ${count+1}: Invalid Department at " + cellRef.formatAsString())
                        noErrors = false
                        allEmpty = false
                    }
                    allEmpty = false
                    break

                case 16:
                    if (instance.get(i).trim().empty || instance.get(i).trim() == "free text"){
                        break
                    }
                    if (instance.get(i).trim().length() > 100) {
                        spreadsheetErrors.add("Ins instance ${count+1}: Faculty Sponsor Too Long at " + cellRef.formatAsString())
                        noErrors = false
                        allEmpty = false
                    }
                    allEmpty = false
                    break

                case 17:
                    if (instance.get(i).trim().empty || instance.get(i).trim() == "free text"){
                        break
                    }
                    if (instance.get(i).trim().length() > 100) {
                        spreadsheetErrors.add("Ins instance ${count+1}: Requestor Too Long at " + cellRef.formatAsString())
                        noErrors = false
                        allEmpty = false
                    }
                    allEmpty = false
                    break

                case 18:
                    if (instance.get(i).trim().empty || instance.get(i).trim() == "free text"){
                        break
                    }
                    if (!instance.get(i).empty && instance.get(i).trim().length() > 500) {
                        spreadsheetErrors.add("Ins instance ${count+1}: Session Description Too Long at " + cellRef.formatAsString())
                        noErrors = false
                        allEmpty = false
                    }
                    allEmpty = false
                    break

                case 19:
                    if (instance.get(i).trim().empty || instance.get(i).trim() == "free text"){
                        break
                    }
                    if (instance.get(i).trim().length() > 500) {
                        spreadsheetErrors.add("Ins instance ${count+1}: Notes Too Long at " + cellRef.formatAsString())
                        noErrors = false
                        allEmpty = false
                    }
                    allEmpty = false
                    break
                default:
                    noErrors = false
            }
        }


        if (allEmpty){
            return "empty"
        }

        def errorCount = 0
        for (error in spreadsheetErrors) {
            if (errorCount <= 3){
                flash.alerts << error
            }
            errorCount++
        }
        if (errorCount > 5){
            flash.alerts << "And ${errorCount-3} more errors"
        }
        return noErrors

    }

    def checkFileType(String fileType) {
        List<String> supportedTypes = Arrays.asList(
                'application/vnd.ms-excel [official]', 'application/msexcel',
                'application/x-msexcel', 'application/x-ms-excel', 'application/vnd.ms-excel',
                'application/x-excel', 'application/x-dos_ms_excel', 'application/xls',
                'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'
        )
        return supportedTypes.contains(fileType)
    }




    def validateDate(String date) {
        def validMonths = ["01": "31", "02": "29", "03": "31", "04": "30", "05": "31", "06": "30", "07": "31", "08": "31", "09": "30", "10": "31", "11": "30", "12": "31"]
        def dateMonth
        def dateDay
        try {
            dateMonth = date.substring(0, 2)
            dateDay = date.substring(2, 4)
        } catch (NullPointerException e) {
            return "Invalid Date Format"
        }

        try{
            if (!(dateDay.toInteger() <= validMonths.get(dateMonth).toInteger())) {
                return "Invalid date"
         }
            else {
                return "good"
            }
        } catch(Exception e){
            return "Invalid date"
        }
    }

    def validateFilename(String filename) {

        //For checking months and days: if day is not in validMonths[month], return false
        def validMonths = ["01": "31", "02": "29", "03": "31", "04": "30", "05": "31", "06": "30", "07": "31", "08": "31", "09": "30", "10": "31", "11": "30", "12": "31"]
        def validName = ~/^[a-zA-Z][a-zA-Z0-9]*_(0[1-9]|1[012])(0[1-9]|[12][0-9]|3[01])[1-9][0-9][0-9][0-9]$/
        def splitFilenameExt = filename.tokenize(".")
        def truncFilename = splitFilenameExt[0]
        def splitFileDate
        def fileDate
        def fileMonth
        def fileDay
        try {
            splitFileDate = filename.tokenize("_")
            fileDate = splitFileDate[1]
            fileMonth = fileDate.substring(0, 2)
            fileDay = fileDate.substring(2, 4)
        } catch (NullPointerException e) {
            return "Invalid File Name. File Names must be of the format Pennkeyname_mmddyyyyy (example: zucca_01262012)"
        }
        if (!(validName.matcher(truncFilename))) {
            return "Invalid File Name. File Names must be of the format Pennkeyname_mmddyyyyy (example: zucca_01262012)"
        }
        else if (this.validateDate(fileDate) != "good"){
            return this.validateDate(fileDate)
        }
        else {
            return "good"
        }

    }


}




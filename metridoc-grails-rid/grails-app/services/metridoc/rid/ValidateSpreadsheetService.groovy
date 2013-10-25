package metridoc.rid

import org.apache.poi.ss.usermodel.*
import org.apache.poi.ss.util.CellReference
import org.codehaus.groovy.grails.io.support.ClassPathResource
import org.codehaus.groovy.grails.web.servlet.FlashScope
import org.springframework.web.multipart.MultipartFile

import java.text.SimpleDateFormat

class ValidateSpreadsheetService {

    def convertToWorkbook(MultipartFile uploadedFile){
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
            if (!itemNames.get(i).trim().equals(validNames.get(i).trim())) return false
        }
        return true
    }



    def checkValid(List<String> instance, int count, FlashScope flash) {

        for (int i = 0; i < instance.size(); i++) {
            CellReference cellRef = new CellReference(5 + i * 2, count + 2)
            switch (i) {
                case 0:
                    if (instance.get(i).trim().empty) {
                        flash.alerts << "Library Unit Cannot be Empty at " + cellRef.formatAsString()
                        return false
                    }
                    if (!RidLibraryUnit.findByName(instance.get(i).trim())) {
                        flash.alerts << "Invalid Library at " + cellRef.formatAsString()
                        return false
                    }
                    break

                case 1:
                    if (instance.get(i).trim().empty) {
                        flash.alerts << "Date of Consultation Cannot be Empty at " + cellRef.formatAsString()
                        return false
                    }
                    try {
                        new SimpleDateFormat("MM/dd/yyyy").parse(instance.get(i).trim())
                    } catch (Exception e) {
                        flash.alerts << "Invalid Date Format at " + cellRef.formatAsString()
                        return false
                    }
                    break

                case 2:
                    if (instance.get(i).trim().empty) {
                        flash.alerts << "Stuff Pennkey Cannot be Empty at " + cellRef.formatAsString()
                        return false
                    }
                    if (instance.get(i).trim().length() > 100) {
                        flash.alerts << "Stuff Pennkey Too Long at " + cellRef.formatAsString()
                        return false
                    }
                    break

                case 3:
                    if (instance.get(i).trim().empty) {
                        flash.alerts << "Mode of Consultation Cannot be Empty at " + cellRef.formatAsString()
                        return false
                    }
                    if (!RidModeOfConsultation.findByName(instance.get(i).trim())) {
                        flash.alerts << "Invalid Mode of Consultation at " + cellRef.formatAsString()
                        return false
                    }
                    if (!RidModeOfConsultation.findByNameAndRidLibraryUnit(
                            instance.get(i).trim(), RidLibraryUnit.findByName(instance.get(0).trim()))) {
                        flash.alerts << "Mode of Consultation at " + cellRef.formatAsString() +
                                " does NOT match the Report Type" + instance.get(0).trim()
                        return false
                    }
                    break

                case 4:
                    if (instance.get(i).trim().empty) {
                        flash.alerts << "Service Provided Cannot be Empty at " + cellRef.formatAsString()
                        return false
                    }
                    if (!RidServiceProvided.findByName(instance.get(i).trim())) {
                        flash.alerts << "Invalid Service Provided at " + cellRef.formatAsString() + instance.get(i)
                        return false
                    }
                    if (!RidServiceProvided.findByNameAndRidLibraryUnit(
                            instance.get(i).trim(), RidLibraryUnit.findByName(instance.get(0).trim()))) {
                        flash.alerts << "Service Provided at " + cellRef.formatAsString() +
                                " does NOT match the Report Type" + instance.get(0).trim()
                        return false
                    }
                    break

                case 5:
                    if (!instance.get(i).trim().empty && !RidUserGoal.findByName(instance.get(i).trim())) {
                        flash.alerts << "Invalid User Goal at " + cellRef.formatAsString()
                        return false
                    }
                    if (!instance.get(i).trim().empty && !RidUserGoal.findByNameAndRidLibraryUnit(
                            instance.get(i).trim(), RidLibraryUnit.findByName(instance.get(0).trim()))) {
                        flash.alerts << "User Goal at " + cellRef.formatAsString() +
                                " does NOT match the Report Type" + instance.get(0).trim()
                        return false
                    }
                    break

                case 6:
                    if (instance.get(i).trim().empty) {
                        flash.alerts << "Prep Time Cannot be Empty at " + cellRef.formatAsString()
                        return false
                    }
                    try {
                        if (Integer.valueOf(instance.get(i).trim()) < 0) {
                            flash.alerts << "Negative Prep Time at " + cellRef.formatAsString()
                            return false
                        }
                    } catch (Exception e) {
                        flash.alerts << "Invalid Format for Prep Time at " + cellRef.formatAsString()
                        return false
                    }
                    break

                case 7:
                    if (instance.get(i).trim().empty) {
                        flash.alerts << "Event Length Cannot be Empty at " + cellRef.formatAsString()
                        return false
                    }
                    try {
                        if (Integer.valueOf(instance.get(i).trim()) < 0) {
                            flash.alerts << "Negative Event Length at " + cellRef.formatAsString()
                            return false
                        }
                    } catch (Exception e) {
                        flash.alerts << "Invalid Format for Event Length at " + cellRef.formatAsString()
                        return false
                    }
                    break

                case 8:
                    if (instance.get(i).trim().length() > 50) {
                        flash.alerts << "User Name Too Long at " + cellRef.formatAsString()
                        return false
                    }
                    break

                case 9:
                    if (instance.get(i).trim().empty) {
                        flash.alerts << "Rank Cannot be Empty at " + cellRef.formatAsString()
                        return false
                    }
                    if (!RidRank.findByName(instance.get(i).trim())) {
                        flash.alerts << "Invalid Rank at " + cellRef.formatAsString()
                        return false
                    }
                    break

                case 10:
                    if (instance.get(i).trim().empty) {
                        flash.alerts << "School Cannot be Empty at " + cellRef.formatAsString()
                        return false
                    }
                    if (!RidSchool.findByName(instance.get(i).trim())) {
                        flash.alerts << "Invalid School at " + cellRef.formatAsString()
                        return false
                    }
                    break

                case 11:
                    if (instance.get(i).trim().empty) {
                        flash.alerts << "Interact Occurrences Cannot be Empty at " + cellRef.formatAsString()
                        return false
                    }
                    try {
                        if (Integer.valueOf(instance.get(i).trim()) < 0) {
                            flash.alerts << "Negative Interact Occurrences at " + cellRef.formatAsString()
                            return false
                        }
                        if (Integer.valueOf(instance.get(i).trim()) > 50) {
                            flash.alerts << "Interact Occurrences Too Large at " + cellRef.formatAsString()
                            return false
                        }
                    } catch (Exception e) {
                        flash.alerts << "Invalid Format for Interact Occurrences at " + cellRef.formatAsString()
                        return false
                    }
                    break

                case 12:
                    if (instance.get(i).trim().length() > 100) {
                        flash.alerts << "Course Name Too Long at " + cellRef.formatAsString()
                        return false
                    }
                    break

                case 13:
                    if (!instance.get(i).trim().empty && !RidDepartment.findByName(instance.get(i).trim())) {
                        flash.alerts << "Invalid Department at " + cellRef.formatAsString()
                        return false
                    }
                    break

                case 14:
                    if (instance.get(i).trim().length() > 100) {
                        flash.alerts << "Course Number Too Long at " + cellRef.formatAsString()
                        return false
                    }
                    break

                case 15:
                    if (instance.get(i).trim().length() > 100) {
                        flash.alerts << "Faculty Sponsor Too Long at " + cellRef.formatAsString()
                        return false
                    }
                    break

                case 16:
                    if (!instance.get(i).trim().empty && !RidCourseSponsor.findByName(instance.get(i).trim())) {
                        flash.alerts << "Invalid Course Sponsor at " + cellRef.formatAsString()
                        return false
                    }
                    break

                case 17:
                    if (!instance.get(i).empty && instance.get(i).trim().length() > 500) {
                        flash.alerts << "User Question Too Long at " + cellRef.formatAsString()
                        return false
                    }
                    break

                case 18:
                    if (instance.get(i).trim().length() > 500) {
                        flash.alerts << "Notes Too Long at " + cellRef.formatAsString()
                        return false
                    }
                    break
                default:
                    return false
            }
        }
        return true
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

    def checkValidWithType(List<String> instance, int count, FlashScope flash, String type) {
        if(type.equals("consultation")){
            for (int i = 0; i < instance.size(); i++) {
                CellReference cellRef = new CellReference(5 + i * 2, count + 2)
                switch (i) {
                    case 0:
                        if (instance.get(i).trim().empty) {
                            flash.alerts << "Library Unit Cannot be Empty at " + cellRef.formatAsString()
                            return false
                        }
                        if (!RidLibraryUnit.findByName(instance.get(i).trim())) {
                            flash.alerts << "Invalid Library at " + cellRef.formatAsString()
                            return false
                        }
                        break

                    case 1:
                        if (instance.get(i).trim().empty) {
                            flash.alerts << "Date of Consultation Cannot be Empty at " + cellRef.formatAsString()
                            return false
                        }
                        try {
                            new SimpleDateFormat("MM/dd/yyyy").parse(instance.get(i).trim())
                        } catch (Exception e) {
                            flash.alerts << "Invalid Date Format at " + cellRef.formatAsString()
                            return false
                        }
                        break

                    case 2:
                        if (instance.get(i).trim().empty) {
                            flash.alerts << "Staff Pennkey Cannot be Empty at " + cellRef.formatAsString()
                            return false
                        }
                        if (instance.get(i).trim().length() > 100) {
                            flash.alerts << "Staff Pennkey Too Long at " + cellRef.formatAsString()
                            return false
                        }
                        break

                    case 3:
                        if (instance.get(i).trim().empty) {
                            flash.alerts << "Mode of Consultation Cannot be Empty at " + cellRef.formatAsString()
                            return false
                        }
                        if (!RidModeOfConsultation.findByName(instance.get(i).trim())) {
                            flash.alerts << "Invalid Mode of Consultation at " + cellRef.formatAsString()
                            return false
                        }
                        if (!RidModeOfConsultation.findByNameAndRidLibraryUnit(
                                instance.get(i).trim(), RidLibraryUnit.findByName(instance.get(0).trim()))) {
                            flash.alerts << "Mode of Consultation at " + cellRef.formatAsString() +
                                    " does NOT match the Report Type" + instance.get(0).trim()
                            return false
                        }
                        break

                    case 4:
                        if (instance.get(i).trim().empty) {
                            flash.alerts << "Service Provided Cannot be Empty at " + cellRef.formatAsString()
                            return false
                        }
                        if (!RidServiceProvided.findByName(instance.get(i).trim())) {
                            flash.alerts << "Invalid Service Provided at " + cellRef.formatAsString()
                            return false
                        }
                        if (!RidServiceProvided.findByNameAndRidLibraryUnit(
                                instance.get(i).trim(), RidLibraryUnit.findByName(instance.get(0).trim()))) {
                            flash.alerts << "Service Provided at " + cellRef.formatAsString() +
                                    " does NOT match the Report Type" + instance.get(0).trim()
                            return false
                        }
                        break

                    case 5:
                        if (!instance.get(i).trim().empty && !RidUserGoal.findByName(instance.get(i).trim())) {
                            flash.alerts << "Invalid User Goal at " + cellRef.formatAsString()
                            return false
                        }
                        if (!instance.get(i).trim().empty && !RidUserGoal.findByNameAndRidLibraryUnit(
                                instance.get(i).trim(), RidLibraryUnit.findByName(instance.get(0).trim()))) {
                            flash.alerts << "User Goal at " + cellRef.formatAsString() +
                                    " does NOT match the Report Type" + instance.get(0).trim()
                            return false
                        }
                        break

                    case 6:
                        if (instance.get(i).trim().empty) {
                            flash.alerts << "Prep Time Cannot be Empty at " + cellRef.formatAsString()
                            return false
                        }
                        try {
                            if (Integer.valueOf(instance.get(i).trim()) < 0) {
                                flash.alerts << "Negative Prep Time at " + cellRef.formatAsString()
                                return false
                            }
                        } catch (Exception e) {
                            flash.alerts << "Invalid Format for Prep Time at " + cellRef.formatAsString()
                            return false
                        }
                        break

                    case 7:
                        if (instance.get(i).trim().empty) {
                            flash.alerts << "Event Length Cannot be Empty at " + cellRef.formatAsString()
                            return false
                        }
                        try {
                            if (Integer.valueOf(instance.get(i).trim()) < 0) {
                                flash.alerts << "Negative Event Length at " + cellRef.formatAsString()
                                return false
                            }
                        } catch (Exception e) {
                            flash.alerts << "Invalid Format for Event Length at " + cellRef.formatAsString()
                            return false
                        }
                        break

                    case 8:
                        if (instance.get(i).trim().length() > 50) {
                            flash.alerts << "User Name Too Long at " + cellRef.formatAsString()
                            return false
                        }
                        break

                    case 9:
                        if (instance.get(i).trim().empty) {
                            flash.alerts << "Rank Cannot be Empty at " + cellRef.formatAsString()
                            return false
                        }
                        if (!RidRank.findByName(instance.get(i).trim())) {
                            flash.alerts << "Invalid Rank at " + cellRef.formatAsString()
                            return false
                        }
                        break

                    case 10:
                        if (instance.get(i).trim().empty) {
                            flash.alerts << "School Cannot be Empty at " + cellRef.formatAsString()
                            return false
                        }
                        if (!RidSchool.findByName(instance.get(i).trim())) {
                            flash.alerts << "Invalid School at " + cellRef.formatAsString()
                            return false
                        }
                        break

                    case 11:
                        if (instance.get(i).trim().empty) {
                            flash.alerts << "Interact Occurrences Cannot be Empty at " + cellRef.formatAsString()
                            return false
                        }
                        try {
                            if (Integer.valueOf(instance.get(i).trim()) < 0) {
                                flash.alerts << "Negative Interact Occurrences at " + cellRef.formatAsString()
                                return false
                            }
                            if (Integer.valueOf(instance.get(i).trim()) > 50) {
                                flash.alerts << "Interact Occurrences Too Large at " + cellRef.formatAsString()
                                return false
                            }
                        } catch (Exception e) {
                            flash.alerts << "Invalid Format for Interact Occurrences at " + cellRef.formatAsString()
                            return false
                        }
                        break

                    case 12:
                        if (instance.get(i).trim().length() > 100) {
                            flash.alerts << "Course Name Too Long at " + cellRef.formatAsString()
                            return false
                        }
                        break

                    case 13:
                        if (!instance.get(i).trim().empty && !RidDepartment.findByName(instance.get(i).trim())) {
                            flash.alerts << "Invalid Department at " + cellRef.formatAsString()
                            return false
                        }
                        break

                    case 14:
                        if (instance.get(i).trim().length() > 100) {
                            flash.alerts << "Course Number Too Long at " + cellRef.formatAsString()
                            return false
                        }
                        break

                    case 15:
                        if (instance.get(i).trim().length() > 100) {
                            flash.alerts << "Faculty Sponsor Too Long at " + cellRef.formatAsString()
                            return false
                        }
                        break

                    case 16:
                        if (!instance.get(i).trim().empty && !RidCourseSponsor.findByName(instance.get(i).trim())) {
                            flash.alerts << "Invalid Course Sponsor at " + cellRef.formatAsString()
                            return false
                        }
                        break

                    case 17:
                        if (!instance.get(i).empty && instance.get(i).trim().length() > 500) {
                            flash.alerts << "User Question Too Long at " + cellRef.formatAsString()
                            return false
                        }
                        break

                    case 18:
                        if (instance.get(i).trim().length() > 500) {
                            flash.alerts << "Notes Too Long at " + cellRef.formatAsString()
                            return false
                        }
                        break
                    default:
                        return false
                }
            }
            return true
        }
        else{
            for (int i = 0; i < instance.size(); i++) {
                CellReference cellRef = new CellReference(5 + i * 2, count + 2)
                switch (i) {
                    case 0:
                        if (instance.get(i).trim().empty) {
                            flash.alerts << "Library Unit Cannot be Empty at " + cellRef.formatAsString()
                            return false
                        }
                        if (!RidLibraryUnit.findByName(instance.get(i).trim())) {
                            flash.alerts << "Invalid Library at " + cellRef.formatAsString()
                            return false
                        }
                        break

                    case 1:
                        if (instance.get(i).trim().empty) {
                            flash.alerts << "Date of Instruction Cannot be Empty at " + cellRef.formatAsString()
                            return false
                        }
                        try {
                            new SimpleDateFormat("MM/dd/yyyy").parse(instance.get(i).trim())
                        } catch (Exception e) {
                            flash.alerts << "Invalid Date Format at " + cellRef.formatAsString()
                            return false
                        }
                        break

                    case 2:
                        if (instance.get(i).trim().empty) {
                            flash.alerts << "Instructor Pennkey Cannot be Empty at " + cellRef.formatAsString()
                            return false
                        }
                        if (instance.get(i).trim().length() > 100) {
                            flash.alerts << "Instructor Pennkey Too Long at " + cellRef.formatAsString()
                            return false
                        }
                        break

                    case 3:
                        if (instance.get(i).trim().length() > 100) {
                            flash.alerts << "Co-Instructor Pennkey Too Long at " + cellRef.formatAsString()
                            return false
                        }
                        break

                    case 4:
                        if (instance.get(i).trim().empty) {
                            flash.alerts << "Event Location Cannot be Empty at " + cellRef.formatAsString()
                            return false
                        }
                        if (!RidLocation.findByName(instance.get(i).trim())) {
                            flash.alerts << "Invalid Location at " + cellRef.formatAsString()
                            return false
                        }
                        break

                    case 5:
                        if (instance.get(i).trim().length() > 100) {
                            flash.alerts << "Sequence Name Too Long at " + cellRef.formatAsString()
                            return false
                        }
                        break

                    case 6:
                        if (instance.get(i).trim().empty) {
                            flash.alerts << "Attendance Cannot be Empty at " + cellRef.formatAsString()
                            return false
                        }
                        try {
                            if (Integer.valueOf(instance.get(i).trim()) < 0) {
                                flash.alerts << "Negative Attendance at " + cellRef.formatAsString()
                                return false
                            }
                        } catch (Exception e) {
                            flash.alerts << "Invalid Format for Attendance at " + cellRef.formatAsString()
                            return false
                        }
                        break

                    case 7:
                        if (instance.get(i).trim().empty) {
                            flash.alerts << "Prep Time Cannot be Empty at " + cellRef.formatAsString()
                            return false
                        }
                        try {
                            if (Integer.valueOf(instance.get(i).trim()) < 0) {
                                flash.alerts << "Negative Prep Time at " + cellRef.formatAsString()
                                return false
                            }
                        } catch (Exception e) {
                            flash.alerts << "Invalid Format for Prep Time at " + cellRef.formatAsString()
                            return false
                        }
                        break

                    case 8:
                        if (instance.get(i).trim().empty) {
                            flash.alerts << "Event Length Cannot be Empty at " + cellRef.formatAsString()
                            return false
                        }
                        try {
                            if (Integer.valueOf(instance.get(i).trim()) < 0) {
                                flash.alerts << "Negative Event Length at " + cellRef.formatAsString()
                                return false
                            }
                        } catch (Exception e) {
                            flash.alerts << "Invalid Format for Event Length at " + cellRef.formatAsString()
                            return false
                        }
                        break

                    case 9:
                        if (instance.get(i).trim().length() > 100) {
                            flash.alerts << "Course Name Too Long at " + cellRef.formatAsString()
                            return false
                        }
                        break

                    case 10:
                        if (instance.get(i).trim().length() > 100) {
                            flash.alerts << "Course Number Too Long at " + cellRef.formatAsString()
                            return false
                        }
                        break

                    case 11:
                        if (!instance.get(i).trim().empty && !RidDepartment.findByName(instance.get(i).trim())) {
                            flash.alerts << "Invalid Department at " + cellRef.formatAsString()
                            return false
                        }
                        break

                    case 12:
                        if (instance.get(i).trim().length() > 100) {
                            flash.alerts << "Faculty Sponsor Too Long at " + cellRef.formatAsString()
                            return false
                        }
                        break

                    case 13:
                        if (instance.get(i).trim().empty) {
                            flash.alerts << "School Cannot be Empty at " + cellRef.formatAsString()
                            return false
                        }
                        if (!RidSchool.findByName(instance.get(i).trim())) {
                            flash.alerts << "Invalid School at " + cellRef.formatAsString()
                            return false
                        }
                        break

                    case 14:
                        if (instance.get(i).trim().empty) {
                            flash.alerts << "Session Type Cannot be Empty at " + cellRef.formatAsString()
                            return false
                        }
                        if (!RidSessionType.findByName(instance.get(i).trim())) {
                            flash.alerts << "Invalid Session Type at " + cellRef.formatAsString()
                            return false
                        }
                        break

                    case 15:
                        if (!RidInstructionalMaterials.findByName(instance.get(i).trim())) {
                            flash.alerts << "Invalid Instructional Material at " + cellRef.formatAsString()
                            return false
                        }
                        break

                    case 16:
                        if (!RidAudience.findByName(instance.get(i).trim())) {
                            flash.alerts << "Invalid Course Sponsor at " + cellRef.formatAsString()
                            return false
                        }
                        break

                    case 17:
                        if (instance.get(i).trim().length() > 50) {
                            flash.alerts << "Requestor Too Long at " + cellRef.formatAsString()
                            return false
                        }
                        break

                    case 18:
                        if (instance.get(i).trim().length() > 500) {
                            flash.alerts << "Description Too Long at " + cellRef.formatAsString()
                            return false
                        }
                        break
                    case 19:
                        if (instance.get(i).trim().length() > 500) {
                            flash.alerts << "Notes Too Long at " + cellRef.formatAsString()
                            return false
                        }
                        break
                    default:
                        return false
                }
            }
            return true
        }
    }


}

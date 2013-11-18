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

import org.apache.poi.ss.usermodel.Workbook
import org.codehaus.groovy.grails.io.support.ClassPathResource
import org.springframework.web.multipart.MultipartFile

class RidAdminLibraryUnitController extends RidAdminBaseController {
    Class domainClass = RidLibraryUnit

    def spreadsheetService
    def ridManageLibraryUnitSpreadsheetsService

    /**
     * Because RidLibraryUnits are connected to other domain objects, and because they have associated spreadsheets
     * this controller overwrites some of the base admin methods
     */
    def save() {
        withForm {
            def ridInstance = new RidLibraryUnit(params)

            if (!ridInstance.save(flush: true)) {
                chain(action: "list", model: [ridDomainClassError: ridInstance])
                return
            }

            // automatically create default values
            new RidUserGoal(name: "", inForm: 1, ridLibraryUnit: ridInstance).save(validate: false)
            new RidServiceProvided(name: "", inForm: 1, ridLibraryUnit: ridInstance).save(validate: false)
            new RidModeOfConsultation(name: "", inForm: 1, ridLibraryUnit: ridInstance).save(validate: false)

            // check and save spreadsheet
            MultipartFile uploadedFile = request.getFile('spreadsheetUpload')
            Workbook wb
            wb= spreadsheetService.convertToWorkbook(uploadedFile)
            if (uploadedFile != null && !uploadedFile.empty) {
                if (!spreadsheetService.checkFileType(uploadedFile.getContentType())) {
                    flash.alerts << "Invalid File Type. Only Excel Files are accepted!"
                    redirect(action: "list")
                    return
                }
                if (uploadedFile.originalFilename != params.name + '_Bulkload_Schematic.xlsx') {
                    flash.alerts << "Invalid File Name. Should be '" + params.name + "_Bulkload_Schematic.xlsx'"
                    redirect(action: "list")
                    return
                }
                if (!spreadsheetService.checkSpreadsheetFormat(wb)) {
                    flash.alerts << "Invalid Spreadsheet Format. Cannot Parse it."
                    redirect(action: "spreadsheetUpload")
                    return
                }
                ClassPathResource resource = new ClassPathResource('grails-app/conf/spreadsheet/' + uploadedFile.originalFilename)
                if (resource.exists()) {
                    try {
                        resource.getFile().delete()
                    } catch (Exception e) {
                        flash.alerts << e.message
                    }
                }
                try {
                    uploadedFile.transferTo(new File(resource.path))
                } catch (Exception e) {
                    flash.alerts << e.message
                }
            }

            flash.message = message(code: 'default.created.message', args: [message(code: 'ridLibraryUnit.label',
                    default: 'RidLibraryUnit'),
                    ridInstance.id])
            redirect(action: "list")
        }.invalidToken {
            flash.alerts << "Don't click the create button more than one time to make duplicated submission!"
            redirect(action: "list")
        }
    }

    def update(Long id, Long version) {
        withForm {
            def ridInstance = RidLibraryUnit.get(id)
            def oldname = ridInstance.name

            if (!ridInstance) {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'ridLibraryUnit.label',
                        default: 'RidLibraryUnit'), id])
                redirect(action: "list")
                return
            }
            if (version != null) {
                if (ridInstance.version > version) {
                    ridInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                            [message(code: 'ridLibraryUnit.label', default: 'RidLibraryUnit')] as Object[],
                            "Another rank has updated this RidLibraryUnit while you were editing")
                    render(view: "list", model: [ridDomainClassError: ridInstance])
                    return
                }
            }

            ridInstance.properties = params

            if (!ridInstance.save(flush: true)) {
                chain(action: "list", model: [ridDomainClassError: ridInstance])
                return
            }

            // check and update spreadsheet file
            MultipartFile uploadedFile = request.getFile('spreadsheetUpload')
            Workbook wb = spreadsheetService.convertToWorkbook(uploadedFile)
            if (uploadedFile != null && !uploadedFile.empty) {
                if (!spreadsheetService.checkFileType(uploadedFile.getContentType())) {
                    flash.alerts << "Invalid File Type. Only Excel Files are accepted!"
                    redirect(action: "list")
                    return
                }
                if (uploadedFile.originalFilename != params.name + '_Bulkload_Schematic.xlsx') {
                    flash.alerts << "Invalid File Name. Should be '" + params.name + "_Bulkload_Schematic.xlsx'"
                    redirect(action: "list")
                    return
                }
                if (!spreadsheetService.checkSpreadsheetFormat(wb)) {
                    flash.alerts << "Invalid Spreadsheet Format. Cannot Parse it."
                    redirect(action: "spreadsheetUpload")
                    return
                }
                ClassPathResource r = new ClassPathResource('grails-app/conf/spreadsheet/' + uploadedFile.originalFilename)
                if (r.exists()) {
                    try {
                        r.getFile().delete()
                    } catch (Exception e) {
                        flash.alerts << e.message
                    }
                }
                try {
                    uploadedFile.transferTo(new File(r.path))
                } catch (Exception e) {
                    flash.alerts << e.message
                }
            }

            flash.message = message(code: 'default.updated.message', args: [message(code: 'ridLibraryUnit.label',
                    default: 'RidLibraryUnit'),
                    ridInstance.id])
            redirect(action: "list")
        }.invalidToken {
            flash.alerts << "Don't click the update button more than one time to make duplicated submission!"
            redirect(action: "list")
        }
    }

    def download() {
        if (ridManageLibraryUnitSpreadsheetsService.download(response, flash, params) == false) {
            redirect(action: "index")
        }
    }

    def spreadsheetUpload() {}
}

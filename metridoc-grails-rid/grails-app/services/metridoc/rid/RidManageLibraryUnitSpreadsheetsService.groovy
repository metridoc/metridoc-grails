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

import org.codehaus.groovy.grails.io.support.ClassPathResource

class RidManageLibraryUnitSpreadsheetsService {

    final def DEFAULT_SPREADSHEET_DIRECTORY = System.getProperty("user.home") + "/.metridoc/files/rid/libraryUnit"
    File unitSpreadsheetDir = new File(DEFAULT_SPREADSHEET_DIRECTORY)

    def download(response, flash, params) {
        //Clunky fix for spreadsheet download problem
        def file
        if (params?.ridLibraryUnit?.name != null)
            file = new File(DEFAULT_SPREADSHEET_DIRECTORY + "/" + params.ridLibraryUnit.name + '_Bulkload_Schematic.xlsx')
        else
            file = new File(DEFAULT_SPREADSHEET_DIRECTORY + "/" + params.sname)

        if (!file.exists()) {
            flash.message = "File not found"
        }
        try {
            response.setContentType('application/vnd.openxmlformats-officedocument.spreadsheetml.sheet')
            response.setHeader("Content-disposition", "filename=${file.name}")
            response.outputStream << file.newInputStream() // Performing a binary stream copy
        } catch (Exception e) {
            flash.alerts << e.message
            return false
        }
    }

    def transferSpreadsheets() {

        unitSpreadsheetDir.mkdirs()

        def resource = new ClassPathResource("spreadsheet")
        if (resource.exists()) {
            def ssDir = resource.getFile()
            ssDir.eachFile {
                //For now, always upload spreadsheet
                if (it.isFile()) {
                    //if (!(new File(unitSpreadsheetDir, it.getName())).exists()) {
                        log.info "Transferring " + it.getName()
                        //it.renameTo(new File(unitSpreadsheetDir, it.getName()))
                        new File(unitSpreadsheetDir, it.getName()).withOutputStream { os ->
                            os << it.newDataInputStream()
                        }
                    //} else {
                    //    log.info it.getName() + " is already in " + unitSpreadsheetDir
                    //}
                }
            }
        } else {
            log.error "Can't find classpath ${resource.path}, so can't transfer spreadsheets to the local directory .metridoc/files: "
        }
    }
}

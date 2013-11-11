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
  *	permissions and limitations under the License.  */

package metridoc.core

import org.apache.commons.lang.StringUtils
import org.apache.commons.lang.exception.ExceptionUtils

import static metridoc.core.CommonService.METRIDOC_CONFIG

class ManageConfigController {

    def commonService
    def generalSettingsService
    def dataSource
    def grailsApplication
    def manageConfigService
    def shiroRememberMeManager

    static accessControl = {
        role(name: "ROLE_ADMIN")
    }

    @SuppressWarnings("GroovyVariableNotAssigned")
    def index() {
        def startFileExistsAndHasText = generalSettingsService.fileExists(generalSettingsService.startFile)
        def workDirectoryFileExistsAndHasText = generalSettingsService.fileExists(generalSettingsService.workDirectoryFile)
        def command
        def workDirectory
        if (startFileExistsAndHasText) {
            command = startFileExistsAndHasText ? generalSettingsService.startFile.text : null
            workDirectory = workDirectoryFileExistsAndHasText ? generalSettingsService.workDirectoryFile.text : null
        }

        [
                command: command,
                workDirectory: workDirectory,
                javaCommand: generalSettingsService.javaCommand(),
                javaVmArguments: generalSettingsService.javaVmArguments(),
                mainCommand: generalSettingsService.mainCommand(),
                dataSourceUrl: dataSource.connection.metaData.getURL(),
                applicationName: grailsApplication.config.metridoc.app.name,
                shiroFilters: grailsApplication.config.security.shiro.filter.filterChainDefinitions,
                reportIssueEmails: manageConfigService.reportIssueEmails,
                metridocConfigExists: commonService.metridocConfig.exists(),
                rememberMeCookieAge:shiroRememberMeManager.cookie.getMaxAge()
        ]
    }

    def upload() {
        String fileContent = request.getFile("metridocConfig").inputStream.getText(CommonService.DEFAULT_ENCODING)
        if (fileContent == null || fileContent == StringUtils.EMPTY) {
            flash.alerts << "No file was provided"
            redirect(action: "index")
            return
        }
        def slurper = new ConfigSlurper()
        try {
            slurper.parse(fileContent)
        } catch (Throwable ex) {
            flash.alerts << "Invalid Config! <br /><pre>${ExceptionUtils.getStackTrace(ex)}</pre>"
            redirect(action: "index")
            return
        }
        commonService.metridocConfig.delete()
        commonService.metridocConfig << fileContent
        flash.infos << "File successfully uploaded"
        redirect(action: "index")
    }

    def download() {
        def config = commonService.metridocConfig
        if (config.exists()) {
            response.setContentType("text/groovy")
            response.setHeader("Content-disposition", "attachment;filename=${METRIDOC_CONFIG}")
            response.outputStream << config.bytes
            return
        }

        flash.alerts << "${config} does not exist"
        redirect(action: "index")
    }

    def updateGeneralSettings(String reportIssueEmails, Integer rememberMeCookieAge) {
        log.debug "reseting notification emails to [$reportIssueEmails]"
        manageConfigService.updateReportUserEmails(reportIssueEmails, flash)
        if (rememberMeCookieAge != null) {
            manageConfigService.updateRememberMeCookieAge(rememberMeCookieAge)
        }

        redirect(action: "index")
    }
}

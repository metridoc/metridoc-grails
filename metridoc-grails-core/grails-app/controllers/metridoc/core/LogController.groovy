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

import grails.web.RequestParameter

class LogController {

    def commonService
    def grailsApplication

    static accessControl = {
        role(name: "ROLE_ADMIN")
    }

    static homePage = [
            title: "Application Log",
            adminOnly: true,
            description: """
                Displays the application log that is normally stored under
                <code>USER_HOME/.metridoc/logs/metridoc.log</code>
            """
    ]

    def index() {
        chain(action: "show")
    }

    @SuppressWarnings('EmptyMethod')
    def show() {
        createModel()
    }

    @SuppressWarnings('EmptyMethod')
    def plain(@RequestParameter("id") String logToDisplay) {
        def model = createModel()
        if (logToDisplay) {
            logToDisplay = logFiles.find { it.contains(logToDisplay) } ?: "${grailsApplication.metadata.getApplicationName()}.log"
            logToDisplay = logToDisplay.contains(".log") ? logToDisplay : logToDisplay + ".log"
            model.put("logToDisplay", logToDisplay)
        }

        return model
    }

    private Map createModel() {

        def files = logFiles

        def initialValue = "${files.find { it.contains("metridoc") }}"

        [logFiles: files,
                initialValue: initialValue,
                logToDisplay: initialValue]
    }

    private Set<String> getLogFiles() {
        def logDir = new File("${commonService.metridocHome}/logs")
        getLogFilesFrom(logDir)
    }

    private static Set<String> getLogFilesFrom(File logDir) {
        logDir.listFiles().findAll { File file -> file.isFile() }.collect { File file -> file.name } as Set
    }
}

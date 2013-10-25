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

package metridoc.illiad

import metridoc.utils.DateUtil

class IlliadController {

    static reportName = "Illiad Dashboards"
    def illiadReportingService

    static homePage = [
            title: "Illiad Dashboard",
            description: """
                Basic borrowing stats from Illiad
            """
    ]

    def index() {
        DateUtil.FY_START_MONTH = IllFiscalStartMonth.first()?.month ?: Calendar.JULY
        def data = IllCache.data
        if(data) {
            data.month = DateUtil.getFiscalMonth()
            return data
        }
        else {
             render(view: "dataNotAvailable")
        }
    }

    def download(String type, Boolean borrowing) {
        if (!type) {
            flash.alerts << "[type] was not specified for file download"
            redirect(action: "index")
        } else {
            borrowing = borrowing != null ? borrowing : false
            response.setContentType("text/csv")
            def fileName = borrowing ? "${type.toLowerCase()}_borrowing.csv" : "${type.toLowerCase()}_lending.csv"
            response.setHeader("Content-Disposition", "attachment;filename=${fileName}")
            illiadReportingService.streamIlliadDataAsCsv(type, borrowing, response.outputStream)
        }
    }
}
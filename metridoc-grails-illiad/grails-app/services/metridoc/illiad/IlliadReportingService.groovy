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

import au.com.bytecode.opencsv.CSVWriter
import au.com.bytecode.opencsv.ResultSetHelperService
import groovy.sql.Sql

import javax.sql.DataSource
import java.sql.ResultSet

class IlliadReportingService {

    static final ENCODING = "utf-8"

    /**
     * doing this instead of using gorm to deal with possible memory issues
     */
    def dataSourceUnproxied
    def selectAllFromIllTransaction = { String type, boolean isBorrowing, String year ->
        def nextYear = Integer.parseInt(year) + 1;
        def processType = isBorrowing ? "Borrowing" : "Lending"
        def fiscalYear = " and transaction_date BETWEEN '" + year + "-07-01' AND '" + nextYear + "-6-31'"
        """
            select t.*, u.nvtgc
            from ill_transaction t
            left join ill_user_info u
            on t.user_id = u.user_id
            where process_type = ${processType}
                and request_type= ${type}
        """ + fiscalYear
    }

    def streamIlliadDataAsCsv(String type, String borrowing, String year, OutputStream outputStream) {
        def sql = new Sql(dataSourceUnproxied as DataSource)
        def resultSetHelperService = new ResultSetHelperService()
        boolean isBorrowing = borrowing == "true" ? true : false;
        sql.query(selectAllFromIllTransaction(type, isBorrowing, year)) { ResultSet resultSet ->
            def columns = resultSetHelperService.getColumnNames(resultSet)
            def columnsWithShortTrans = addShortTransDateColumn(columns)
            def writer = new OutputStreamWriter(outputStream, ENCODING as String)
            def csvWriter = new CSVWriter(writer)
            try {
                csvWriter.writeNext(columnsWithShortTrans)
                while(resultSet.next()) {
                    def line = addShortTransDateValue(resultSet, resultSetHelperService)
                    csvWriter.writeNext(line)
                }
            }
            finally {
                csvWriter.flush()
                csvWriter.close()
            }
        }
    }

    static String[] addShortTransDateColumn(String[] columnsToTransform) {
        def result = []

        result.addAll(columnsToTransform)
        result << "short_trans_date"
        return result as String[]
    }

    static String[] addShortTransDateValue(ResultSet resultSet, ResultSetHelperService resultSetHelperService) {
        def values = resultSetHelperService.getColumnValues(resultSet) as List
        def transactionDate = resultSet.getDate("transaction_date")
        values << transactionDate.format("yyyy-MM-dd")

        return values as String[]
    }
}

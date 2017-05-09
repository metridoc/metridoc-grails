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

package metridoc.penn.bd

import edu.upennlib.collmanagement.CallNoService
import edu.upennlib.collmanagement.CallNoService.CallNoCounts
import groovy.sql.Sql
import metridoc.penn.util.DateUtil
import org.slf4j.LoggerFactory

import javax.sql.DataSource
import java.sql.ResultSet

/**
 *
 * @author Narine
 *
 */
class BorrowDirectService {
    public static String BD_SERVICE_KEY = 'bd'
    public static String EZB_SERVICE_KEY = 'ezb'

    DataSource dataSource
    def grailsApplication

    private getSql() {
        return new Sql(dataSource);
    }

    ConfigObject getConfig() {
        grailsApplication.config
    }

    int getMinFiscalYear() {
        config.datafarm.minFiscalYear
    }

    def dumpDataLibrary(library_id, from, to, outstream, serviceKey) {
        def reportGenerator = new LibraryDataReportGenerator();
        runReport(reportGenerator, prepareQuery(config.queries.borrowdirect.dataDumpByLibrary, serviceKey), [from, to, library_id, library_id], outstream, serviceKey)
    }

    def dumpDataMultipleItems(from, to, minTimes, outstream, serviceKey) {
        def reportGenerator = new MultipleItemsDataReportGenerator();
        runReport(reportGenerator, prepareQuery(config.queries.borrowdirect.dataDumpMultipleItems, serviceKey), [from, to, minTimes], outstream, serviceKey)
    }

    def getSummaryDashboardData(libId, fiscalYear, serviceKey) {
        getSummaryDashboardData(libId, fiscalYear, serviceKey, null)
    }

    def getSummaryDashboardData(libId, fiscalYear, serviceKey, selectedLibIds) {
        Sql sql = getSql();
        def result = [:]
        def currentFiscalYear = fiscalYear;
        Date currentFiscalYearEnd;
        Date lastFiscalYearEnd;
        if (currentFiscalYear == null) {
            def currentDate = Calendar.getInstance();
            def currentYear = currentDate.get(Calendar.YEAR);
            def currentMonth = currentDate.get(Calendar.MONTH);
            currentFiscalYear = DateUtil.getFiscalYear(currentYear, currentMonth)
            result.currentMonth = currentMonth

            lastFiscalYearEnd = DateUtil.getDate(currentYear - 1, currentMonth, currentDate.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
            currentFiscalYearEnd = DateUtil.getDate(currentYear, currentMonth, currentDate.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        }
        else {
            result.currentMonth = DateUtil.getFiscalYearEndMonth();
            currentFiscalYearEnd = DateUtil.getFiscalYearEndDate(currentFiscalYear)
            lastFiscalYearEnd = DateUtil.getFiscalYearEndDate(currentFiscalYear - 1)
        }

        result.fiscalYear = currentFiscalYear
        Date currentFiscalYearStart = DateUtil.getFiscalYearStartDate(currentFiscalYear)

        def dates = [:]
        dates.currentFiscalYear = [currentFiscalYearStart, currentFiscalYearEnd];

        dates.lastFiscalYear = [DateUtil.getFiscalYearStartDate(currentFiscalYear - 1), lastFiscalYearEnd]

        loadDataPerLibrary(sql, true, result, dates, libId, serviceKey, selectedLibIds);
        loadDataPerLibrary(sql, false, result, dates, libId, serviceKey, selectedLibIds);
        if (libId != null) {
            loadPickupData(sql, result, dates.currentFiscalYear, libId, serviceKey)
            loadShelvingData(sql, result, dates.currentFiscalYear, libId, serviceKey)
        }
        log.debug(result)
        return result
    }

    private loadPickupData(sql, result, currentFiscalYearDates, libId, serviceKey) {
        def query = prepareQuery(config.queries.borrowdirect.countsPerPickupLocations, serviceKey)
        def sqlParams = [currentFiscalYearDates[0], currentFiscalYearDates[1], libId]
        result.pickupData = sql.rows(query, sqlParams)
    }

    private loadShelvingData(sql, result, currentFiscalYearDates, libId, serviceKey) {
        def query = prepareQuery(config.queries.borrowdirect.countsPerShelvingLocations, serviceKey)
        def sqlParams = [currentFiscalYearDates[0], currentFiscalYearDates[1], libId]
        result.shelvingData = sql.rows(query, sqlParams)
    }

    def getRequestedCallNoCounts(libId, serviceKey, paramFiscalYear) {
        def currentFiscalYear = DateUtil.getCurrentFiscalYear()
        def fiscalYear = paramFiscalYear != null ? paramFiscalYear : currentFiscalYear;

        Date fiscalYearStart = DateUtil.getFiscalYearStartDate(fiscalYear)
        Date fiscalYearEnd = DateUtil.getFiscalYearEndDate(fiscalYear)

        Sql sql = getSql();
        CallNoCounts counts;
        def sqlParams = [fiscalYearStart, fiscalYearEnd]
        def query = prepareQuery(config.queries.borrowdirect.requestedCallNos, serviceKey)
        if (libId != null) {
            query += " and " + config.borrowdirect.db.column.borrower + " = " + libId
        }
        log.debug("Runnig query for callnos: " + query + "\n params=" + sqlParams)
        ResultSet resultSet = sql.query(query, sqlParams, {
            counts = CallNoService.getCounts(it)
        })
        return [counts: counts,
                minFiscalYear: minFiscalYear,
                reportFiscalYear: fiscalYear,
                currentFiscalYear: currentFiscalYear]
    }

    /**
     * Always call with isBorrowing=true before isBorrowing=false
     */
    private loadDataPerLibrary(sql, isBorrowing, result, dates, libId, tablePrefix, selectedLibIds) {
        def libRoleColumn;
        def keyForSection;
        def additionalCondition = ""
        if (isBorrowing) {
            if (libId != null) {
                additionalCondition = "and " + config.borrowdirect.db.column.borrower + "=" + libId
                libRoleColumn = config.borrowdirect.db.column.lender
            }
            else {
                libRoleColumn = config.borrowdirect.db.column.borrower
            }
            keyForSection = 'borrowing'
        }
        else {
            if (libId != null) {
                additionalCondition = "and " + config.borrowdirect.db.column.lender + "=" + libId
                libRoleColumn = config.borrowdirect.db.column.borrower
            }
            else {
                libRoleColumn = config.borrowdirect.db.column.lender
            }
            keyForSection = 'lending'
        }
        if (selectedLibIds != null) {
            additionalCondition += " and " + libRoleColumn + getInClause(selectedLibIds);
        }

        def query = getAdjustedQuery(config.queries.borrowdirect.countsPerLibraryMonthlyFilled, libRoleColumn, additionalCondition, tablePrefix)

        def allLibDataSection = getLibDataMap(-1l, result).get(keyForSection)
        //currentFiscalYear
        def sqlParams = dates.currentFiscalYear
        log.debug("Runnig query for currentFiscalYear: " + query + " params=" + sqlParams)
        sql.eachRow(query, sqlParams, {
            def libData = getLibDataMap(it.getAt(0), result)
            int currentKey = it.getAt(1) != null ? it.getAt(1) : -1
            libData.get(keyForSection).currentFiscalYear.put(currentKey, it.requestsNum)
            if (it.getAt(0) > 0) {
                //count sum for each month (row All Libraries)
                def prevValue = allLibDataSection.currentFiscalYear.get(currentKey);
                if (prevValue == null) {
                    prevValue = 0;
                }
                allLibDataSection.currentFiscalYear.put(currentKey, it.requestsNum + prevValue)
            }
        })
        //turnaround
        def turnaroundQuery = getAdjustedQuery(config.queries.borrowdirect.turnaroundPerLibrary, libRoleColumn, additionalCondition, tablePrefix)
        log.debug("Runnig query for turnaround: " + turnaroundQuery + " params=" + sqlParams)
        sql.eachRow(turnaroundQuery, sqlParams, {
            def libData = getLibDataMap(it.getAt(0).longValue(), result)
            def currentMap = libData.get(keyForSection)
            currentMap.turnaroundReqRec = it.turnaroundReqRec
            currentMap.turnaroundReqShp = it.turnaroundReqShp
            currentMap.turnaroundShpRec = it.turnaroundShpRec
        })

        boolean notEZBorrow = !EZB_SERVICE_KEY.equals(tablePrefix)
        //fill rates
        if (libId == null) {
            if (notEZBorrow){
                // log.debug("Runnig query for fillRate for ${libRoleColumn}: " + allQuery + " params=" + sqlParams)
                if(isBorrowing){
                    def allQuery = getAdjustedQuery(config.queries.borrowdirect.countsAllPerBorrower,"",additionalCondition,tablePrefix)
                    sql.eachRow(allQuery,
                        sqlParams, {
                        setFillRateBorrowing(it.getAt(0), it.unfilledNum, keyForSection, result)
                    })
                }else{
                    def allQuery = getAdjustedQuery(config.queries.borrowdirect.countsAllPerLender,"",additionalCondition,tablePrefix)
                    sqlParams = [dates.currentFiscalYear[0], dates.currentFiscalYear[1], dates.currentFiscalYear[0], dates.currentFiscalYear[1]]
                    sql.eachRow(allQuery,
                        sqlParams, {
                        setFillRate(it.getAt(0), it.requestsNum, keyForSection, result)
                    })    
                }
                
            }else if (selectedLibIds == null) {
                //*** remove whole else block
                //leniding fill rate for All Libraries is equal to the borrowing fill rate
                //if no subset has been selected, this value is already set, if calcFillRates is true
                def libData = getLibDataMap(-1, result)
                libData.get('lending').yearFillRate = libData.get('borrowing').yearFillRate
            }
        }
        else {
            def allQuery;
            sqlParams = [dates.currentFiscalYear[0], dates.currentFiscalYear[1], libId]
            if (notEZBorrow) { //***
                allQuery = isBorrowing ? config.queries.borrowdirect.countsAllPerLenderToLib : config.queries.borrowdirect.countsAllPerBorrowerFromLib;
                //Total number of items borrowed by selected lib (lended to particular one) or lended(touched) by  selected lib
                allQuery = prepareQuery(allQuery, tablePrefix)
                log.debug("Runnig query for fillRate for ${libRoleColumn} and ${libId}: " + allQuery + " params=" + sqlParams)
                if(isBorrowing){
                    sql.eachRow(allQuery,
                            sqlParams, {
                        setFillRateBorrowing(it.getAt(0), it.unfilledNum, keyForSection, result)
                    })
                }else{
                    allQuery = allQuery.replaceAll("\\{lender_id\\}", libId+"")
                    sql.eachRow(allQuery,
                            sqlParams, {
                        setFillRate(it.getAt(0), it.requestsNum, keyForSection, result)
                    })
                }
                //Calc for row All Library
                allQuery = isBorrowing ? config.queries.borrowdirect.countsAllBorrowedByLib : config.queries.borrowdirect.countsAllTouchedByLib;
                //Total number of items selected library borrowed (borrwing section) or have touched (for lending) section
                allQuery = prepareQuery(allQuery, tablePrefix)
                if(!isBorrowing){
                    allQuery = allQuery.replaceAll("\\{lender_id\\}", libId+"")
                }
                log.debug("Runnig query for fillRate for ${libRoleColumn} and ${libId} - All Libraries row: " + allQuery + " params=" + sqlParams)
                def row = sql.firstRow(allQuery, sqlParams)
                setFillRate(-1, row != null ? row.requestsNum : 0, keyForSection, result)
            }
        }

        //lastFiscalYear
        sqlParams = dates.lastFiscalYear
        log.debug("Runnig query for lastFiscalYear: " + query + " params=" + sqlParams)
        sql.eachRow(query, sqlParams, {
            def libData = getLibDataMap(it.getAt(0), result)
            int currentKey = it.getAt(1) != null ? it.getAt(1) : -1
            libData.get(keyForSection).lastFiscalYear.put(currentKey, it.requestsNum)
            if (it.getAt(0) > -1) {
                def prevValue = allLibDataSection.lastFiscalYear.get(currentKey);
                if (prevValue == null) {
                    prevValue = 0;
                }
                allLibDataSection.lastFiscalYear.put(currentKey, it.requestsNum + prevValue)
            }
        })
        log.debug("Done for " + keyForSection)
        return result
    }

    private static setFillRate(libId, requestsNum, keyForSection, result) {
        if(libId){
            def libData = getLibDataMap(libId, result)
            def currentMap = libData.get(keyForSection)
            if (currentMap.currentFiscalYear.get(-1) == null) {
                currentMap.currentFiscalYear.put(-1, 0);
            }
            // if(libId==-1){
            //     println("\nThis is lending total number")
            //     println(requestsNum)
            //     println("This is lending filled number")
            //     println(currentMap.currentFiscalYear.get(-1))
            // }
            if (requestsNum != 0) {
                def fillrate = (currentMap.currentFiscalYear.get(-1) / (float) requestsNum).round(2)
                if(fillrate > 0 && fillrate < 1){
                    currentMap.yearFillRate = fillrate
                }else{
                    currentMap.yearFillRate = -1
                }
            }else{
                currentMap.yearFillRate = -1
            }
        }
    }

    private static setFillRateBorrowing(libId, unfilledNum, keyForSection, result){
        if(libId){
            def libData = getLibDataMap(libId, result)
            def currentMap = libData.get(keyForSection)
            if (currentMap.currentFiscalYear.get(-1) == null) {
                currentMap.currentFiscalYear.put(-1, 0);
            }
            if(unfilledNum == 0){
                currentMap.yearFillRate = 1
            }else{
                // if(libId==-1){
                //     println("\nTHIS is borrowing total number")
                //     println(currentMap.currentFiscalYear.get(-1) + unfilledNum)
                //     println("This is borrowing filled number")
                //     println(currentMap.currentFiscalYear.get(-1))
                //     println("This is borrowing unfilled number")
                //     println(unfilledNum)
                // }

                def unfilledRate = ((float) unfilledNum / (currentMap.currentFiscalYear.get(-1) + (float) unfilledNum)).round(2)
                def fillrate = 1 - unfilledRate
                if(fillrate > 0 && fillrate < 1){
                    currentMap.yearFillRate = fillrate
                }else{
                    currentMap.yearFillRate = -1
                }
            }
        }
    }


    def getUnfilledRequests(dateFrom, dateTo, libId, orderBy, serviceKey) {
        Sql sql = getSql();
        def query = prepareQuery(config.queries.borrowdirect.libraryUnfilledRequests, serviceKey) + orderBy
        def sqlParams = [dateFrom, dateTo, libId]
        log.debug("Runnig query for unfilled requests " + query + "\nparams = " + sqlParams)
        println("THIS IS THE getUnfilledRequests QUERY")
        println(query)
        return sql.rows(query, sqlParams)
    }

    def getMonthsInDisplayOrder(currentMonth) {
        def monthFrom = currentMonth
        def result = [];
        if (monthFrom < DateUtil.FY_START_MONTH) {
            for (int i = monthFrom; i >= 0; i--) {
                result[result.size()] = i;
            }
            monthFrom = Calendar.DECEMBER
        }
        for (int i = monthFrom; i >= DateUtil.FY_START_MONTH; i--) {
            result[result.size()] = i;
        }
        return result
    }
    /**
     * structure {libId={'borrowing'=[2010=10, 2011=13]}, {'lending'=[2010=10, 2011=13]}},
     *{-1={'borrowing'=[2010=10, 2011=13]}, {'lending'=[2010=10, 2011=13]}}* @param sql
     * @param isBorrowing
     * @param result
     * @param dates
     * @param libId
     * @param tablePrefix
     * @return
     */
    private loadHistoricalDataPerLibrary(sql, isBorrowing, result, tablePrefix, selectedLibIds, libId) {
        def libRoleColumn, keyForSection;
        def additionalCondition = ""
        if (isBorrowing) {
            if (libId != null) {
                additionalCondition = "and " + config.borrowdirect.db.column.borrower + "=" + libId
                libRoleColumn = config.borrowdirect.db.column.lender
            }
            else {
                libRoleColumn = config.borrowdirect.db.column.borrower
            }
            keyForSection = 'borrowing'
        }
        else {
            if (libId != null) {
                additionalCondition = "and " + config.borrowdirect.db.column.lender + "=" + libId
                libRoleColumn = config.borrowdirect.db.column.borrower
            }
            else {
                libRoleColumn = config.borrowdirect.db.column.lender
            }
            keyForSection = 'lending'
        }
        if (selectedLibIds != null) {
            additionalCondition += " and " + libRoleColumn + getInClause(selectedLibIds);
        }
        def query = getAdjustedQuery(config.queries.borrowdirect.historicalCountsPerLibFilled, libRoleColumn, additionalCondition, tablePrefix);
        query = query.replaceAll("\\{fy_start_month\\}", (DateUtil.FY_START_MONTH + 1) + "")
        //change from base 0 to base 1

        log.debug("Runnig query for historical data: " + query)
        sql.eachRow(query, [], {
            def libData = getLibDataMapHistorical(it.getAt(0), result)
            int currentKey = it.getAt(1) != null ? it.getAt(1) : -1
            libData.get(keyForSection).items.put(currentKey, it.requestsNum)
        })

        //Fill Rates

        //Hide some incorrect rates for EZBorrow,
        //because of incorrect library_id field in print_date table
        //delete if statements marked with *** when data is corrected
        boolean notEZBorrow = !EZB_SERVICE_KEY.equals(tablePrefix)

        if (libId == null) {
            //Summary page
            if (notEZBorrow || isBorrowing) { //***
                def allQuery = isBorrowing ? config.queries.borrowdirect.historicalCountsAllPerBorrower :
                        config.queries.borrowdirect.historicalCountsAllPerLender;
                //Total number of items borrowed by lib or lended(touched) by lib
                allQuery = getAdjustedQuery(allQuery, "", additionalCondition, tablePrefix);
                allQuery = allQuery.replaceAll("\\{fy_start_month\\}", (DateUtil.FY_START_MONTH + 1) + "")
                //change from base 0 to base 1

                log.debug("Runnig query for fillRate for ${libRoleColumn}: " + allQuery)
                if(isBorrowing){
                    sql.eachRow(allQuery,
                            [], {
                        setFillRateHistoricalBorrowing(it.getAt(0), it.getAt(1), it.unfilledNum, keyForSection, result)
                    })
                }else{
                    sql.eachRow(allQuery,
                            [], {
                        setFillRateHistorical(it.getAt(0), it.getAt(1), it.requestsNum, keyForSection, result)
                    })
                }
            }
            else if (selectedLibIds == null) {
                //*** delete whole else block (with content)
                //leniding fill rate for All Libraries is equal to the borrowing fill rate
                //when no subset of libs is selected. This will be calculated in if above, when calcFillRates is true
                def libData = getLibDataMapHistorical(-1, result)
                libData.get('lending').fillRates = libData.get('borrowing').fillRates
            }
        }
        else {
            def allQuery;
            //same here, what is the functionality of these two if statements, it seems repetitive to me
            if (notEZBorrow) {
                //Summary page for selected library
                allQuery = isBorrowing ? config.queries.borrowdirect.historicalCountsAllPerLenderToLib :
                        config.queries.borrowdirect.historicalCountsAllPerBorrowerFromLib;
                //Total number of items borrowed by selected lib (lended to particular one) for Borrowing section (isBorrowing=true)
                //Total number of items lended(touched) by  selected lib for Leniding section
                allQuery = prepareHistoricQuery(allQuery, tablePrefix)

                //sqlParams = [dates.currentFiscalYear[0], dates.currentFiscalYear[1], libId]
                log.debug("Runnig query for fillRate for ${libRoleColumn} and ${libId}: " + allQuery)
                if(isBorrowing){
                    sql.eachRow(allQuery,
                            [libId], {
                        setFillRateHistoricalBorrowing(it.getAt(0), it.getAt(1), it.unfilledNum, keyForSection, result)
                    })
                }else{
                    allQuery = allQuery.replaceAll("\\{lender_id\\}", libId+"")
                    sql.eachRow(allQuery,
                            [libId], {
                        setFillRateHistorical(it.getAt(0), it.getAt(1), it.requestsNum, keyForSection, result)
                    })
                }
                //Calc for row All Library
                allQuery = isBorrowing ? config.queries.borrowdirect.historicalCountsAllBorrowedByLib :
                        config.queries.borrowdirect.historicalCountsAllTouchedByLib;
                if(!isBorrowing){
                    allQuery = allQuery.replaceAll("\\{lender_id\\}", libId+"")
                }
                //Total number of items selected library borrowed (borrwing section) or have touched (for lending) section
                allQuery = prepareHistoricQuery(allQuery, tablePrefix)

                log.debug("Runnig query for fillRate for ${libRoleColumn} and ${libId} - All Libraries row: " + allQuery)
                sql.eachRow(allQuery,
                        [libId], {
                    setFillRateHistorical(-1, it.getAt(0), it.requestsNum, keyForSection, result)
                })
            }

        }
    }

    private static setFillRateHistoricalBorrowing(libId, year, unfilledNum, keyForSection, result){
        if(libId != null){
            def libData = getLibDataMapHistorical(libId, result)
            def currentMap = libData.get(keyForSection)
            int currentKey = year != null ? year : -1
            def log = LoggerFactory.getLogger(BorrowDirectService)

            // log.error("""
            //     current key is $currentKey
            //     current map is $currentMap
            //     libId is $libId
            //     requestNum is $unfilledNum
            //     keyForSection is $keyForSection
            //     result is $result
            // """)

            Integer filledReqs = currentMap.items.get(currentKey);
            if (filledReqs == null) {
                filledReqs = 0;
            }

            // if(libId==-1){
            //     println("\nTHIS is borrowing total number")
            //     println(filledReqs + unfilledNum)
            //     println("This is borrowing filled number")
            //     println(filledReqs)
            // }

            def fillrate = (1 - (float) unfilledNum / (filledReqs + unfilledNum)).round(2)
            if(fillrate > 0 && fillrate < 1){
                currentMap.fillRates.put(currentKey, fillrate)
            }else{
                currentMap.fillRates.put(currentKey, -1)
            }
        }
    }

    private static setFillRateHistorical(libId, year, requestsNum, keyForSection, result) {
        if(libId != null){
            def libData = getLibDataMapHistorical(libId, result)
            def currentMap = libData.get(keyForSection)
            int currentKey = year != null ? year : -1
            def log = LoggerFactory.getLogger(BorrowDirectService)

            // log.error("""
            //     current key is $currentKey
            //     current map is $currentMap
            //     libId is $libId
            //     requestNum is $requestsNum
            //     keyForSection is $keyForSection
            //     result is $result
            // """)

            Integer filledReqs = currentMap.items.get(currentKey);
            if (filledReqs == null) {
                filledReqs = 0;
            }
            // if(libId==-1){
            //     println("\nThis is lending total number")
            //     println(requestsNum)
            //     println("This is lending filled number")
            //     println(filledReqs)
            // }
            if (requestsNum != 0) {
                def fillrate = (filledReqs / (float) requestsNum).round(2)
                if(fillrate > 0 && fillrate < 1){
                    currentMap.fillRates.put(currentKey,fillrate)
                }else{
                    currentMap.fillRates.put(currentKey,-1)
                }
            }else{
                currentMap.fillRates.put(currentKey,-1)
            }
        }
    }

    def getHistoricalData(serviceKey, libId) {
        return getHistoricalData(serviceKey, null, libId);
    }

    def getHistoricalData(serviceKey, selectedLibIds, libId) {
        def result = [:];
        def currentFiscalYear = DateUtil.getCurrentFiscalYear()
        //Date currentFiscalYearStart = DateUtil.getFiscalYearStartDate(currentFiscalYear)
        Sql sql = getSql();
        result.minFiscalYear = minFiscalYear;

        loadHistoricalDataPerLibrary(sql, true, result, serviceKey, selectedLibIds, libId);
        loadHistoricalDataPerLibrary(sql, false, result, serviceKey, selectedLibIds, libId);
        result.currentFiscalYear = currentFiscalYear
        log.debug(result)
        return result;
    }

    def getLibraryList(serviceKey) {
        return getLibraryList(serviceKey, null);
    }

    private getInClause(paramList) {
        return " IN (" + paramList.join(',') + ")"
    }

    def getLibraryList(serviceKey, selectedLibIds) {
        Sql sql = getSql();
        def additionalCondition = selectedLibIds != null ? " where library_id " + getInClause(selectedLibIds) : ""
        def query = prepareQuery(config.queries.borrowdirect.libraryList, serviceKey)
        query = query.replaceAll("\\{add_condition\\}", additionalCondition);
        return sql.rows(query, [])
    }

    def getLibraryById(serviceKey, libId) {
        Sql sql = getSql();
        def query = prepareQuery(config.queries.borrowdirect.libraryById, serviceKey)
        return sql.firstRow(query, [libId])
    }

    private String getAdjustedQuery(query, libRoleColumn, additionalCondition, tablePrefix) {
        def result = query.replaceAll("\\{lib_role\\}", libRoleColumn)
        result = result.replaceAll("\\{add_condition\\}", additionalCondition)
        return prepareQuery(result, tablePrefix);
    }

    private String prepareHistoricQuery(query, tablePrefix) {
        def result = prepareQuery(query, tablePrefix);
        return result.replaceAll("\\{fy_start_month\\}", (DateUtil.FY_START_MONTH + 1) + "")
    }

    private String prepareQuery(query, tablePrefix) {
        return query.replaceAll("\\{table_prefix\\}", tablePrefix)
    }

    private void runReport(reportGenerator, query, inputParams, outstream, serviceKey) {
        Sql sql = getSql();
        log.debug("Runnig report query : " + query + "\n params=" + inputParams)
        sql.eachRow(query, inputParams, {
            reportGenerator.addRowData(it)
        })
        reportGenerator.write(outstream);
    }

    private static getLibDataMap(libId, container) {
        libId = libId.longValue()
        if (container.get(libId) == null) {
            container.put(libId, ['borrowing': ['currentFiscalYear': [:], 'lastFiscalYear': [:]],
                    'lending': ['currentFiscalYear': [:], 'lastFiscalYear': [:]]])
        }
        return container.get(libId)
    }

    private static getLibDataMapHistorical(libId, container) {
        libId = libId.longValue()
        if (container.get(libId) == null) {
            container.put(libId, ['borrowing': [items: [:], fillRates: [:]], 'lending': [items: [:], fillRates: [:]]]);
        }
        return container.get(libId)
    }
}

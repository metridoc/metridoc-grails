package metridoc.funds

import groovy.sql.Sql
import metridoc.funds.ExpenditureReportGenerator
import metridoc.funds.SummaryReportGenerator

class FundsService {

    def dataSource
    def grailsApplication
    static transactional = false

    private static final ALL_FUNDS_ID = 1;

    def doSummaryReport(outstream, startDate, endDate, isFilterByFund, filterValue) {
        def row = isFilterByFund?getFundNameById(filterValue):getVendorNameByCode(filterValue);
        def reportGenerator = new SummaryReportGenerator(isFilterByFund, row.name, startDate, endDate);
        runReport(reportGenerator, filterQuery(grailsApplication.config.queries.funds.summary, isFilterByFund, filterValue), [startDate, endDate], outstream);
    }

    def getLedgersAmountArray(container, currentLedger){
        if(container.ledgersAmounts == null){
            container.ledgersAmounts = []
        }
        while(container.ledgersAmounts.size() <= currentLedger){
            container.ledgersAmounts.add(0);
        }
        return container.ledgersAmounts;
    }

    def doExpenditureReport(outstream, selectedLedgers, isFilterByFund, filterValue) {
        def row = isFilterByFund?getFundNameById(filterValue):getVendorNameByCode(filterValue);
        def reportGenerator = new ExpenditureReportGenerator(isFilterByFund, row?row.name:"", selectedLedgers);
        def query = filterQuery(grailsApplication.config.queries.funds.expenditure, isFilterByFund, filterValue)
        def dataContainer = [:];
        Sql sql = new Sql(dataSource)
        def inputParams;
        def selectedLedgersNum = selectedLedgers.size();
        for(int i = 0; i < selectedLedgersNum; i++){
            def ledger = selectedLedgers.get(i);
            inputParams = [ledger.startDate, ledger.endDate];
            log.debug("Runnig report query : " + query + "\n params="+inputParams)
            sql.eachRow(query, inputParams, {
                def sumFundMap = getDataMap(it.sumfund_id, dataContainer);
                sumFundMap.sumfund_name = it.sumfund_name;
                def sumLedgersAmounts = getLedgersAmountArray(sumFundMap, i)

                def allocFundMap = getDataMap(it.alloc_name, getDataMap('allocFunds', sumFundMap));
                allocFundMap.alloc_name = it.alloc_name;
                def allocLedgersAmounts = getLedgersAmountArray(allocFundMap, i)

                def repFundMap = getDataMap(it.repfund_id, getDataMap('repFunds', allocFundMap));
                repFundMap.repfund_name = it.repfund_name;

                def repLedgersAmounts = getLedgersAmountArray(repFundMap, i)

                def itemMap = getDataMap(it.bib_id, getDataMap('items', repFundMap));
                itemMap.title = it.title
                itemMap.publisher = it.publisher
                if(itemMap.ledgers == null){
                    itemMap.ledgers = createLedgersArray(selectedLedgers.size())
                }



                def itemLedgerData = ['status':it.status,
                        'percent':it.percent,
                        'po_no':it.po_no,
                        'quantity':it.quantity,
                        'vendor':it.vendor,
                        'cost':it.cost];
                itemMap.ledgers.get(i).add(itemLedgerData);
                def newSize = itemMap.ledgers.get(i).size();
                if(itemMap.ledgerMaxSize == null || itemMap.ledgerMaxSize < newSize){
                    itemMap.ledgerMaxSize = newSize;
                }

                repLedgersAmounts[i] = repLedgersAmounts[i] + it.cost
                allocLedgersAmounts[i] = allocLedgersAmounts[i] + it.cost
                sumLedgersAmounts[i] = sumLedgersAmounts[i] + it.cost
            })
            log.debug("Done with ledger "+ ledger + " processing.");
        }

        //println dataContainer
        log.debug("Start Excel construction.");
        reportGenerator.addData(dataContainer);
        log.debug("Done with Excel.");
        reportGenerator.write(outstream);
    }
    private createLedgersArray(int ledgersNum){
        def result = [];
        for (int i = 0; i < ledgersNum; i++) {
            result.add([]);
        }
        return result;
    }
    private createLedgersAmountsArray(int ledgersNum){
        def result = [];
        for (int i = 0; i < ledgersNum; i++) {
            result.add(0);
        }
        return result;
    }
    private getDataMap(mapId, container){
        if(container.get(mapId) == null){
            container.put(mapId, [:]);
        }
        return container.get(mapId)
    }

    private String filterQuery(query, isFilterByFund, filterValue){
        def additionalCondition = "";
        if(isFilterByFund){
            additionalCondition = "f.sumfund_id="+filterValue;
        }else{
            additionalCondition = "f.vendor = '"+filterValue+"' and f.sumfund_id != " + ALL_FUNDS_ID;
        }
        return query.replaceAll("\\{add_condition\\}", additionalCondition);
    }

    private void runReport(reportGenerator, query, inputParams, outstream){
        Sql sql = new Sql(dataSource)
        log.debug("Runnig report query : " + query + "\n params="+inputParams)
        sql.eachRow(query, inputParams, {
            reportGenerator.addRowData(it)
        })
        reportGenerator.write(outstream);
    }

    def getVendorList(){
        Sql sql = new Sql(dataSource)
        log.debug("Running query: " + grailsApplication.config.queries.funds.vendorList)
        return sql.rows(grailsApplication.config.queries.funds.vendorList, [])
    }

    def getFundList(){
        Sql sql = new Sql(dataSource)
        log.debug("Running query: " + grailsApplication.config.queries.funds.fundList)
        return sql.rows(grailsApplication.config.queries.funds.fundList, [])
    }

    def getVendorNameByCode(vendorCode){
        Sql sql = new Sql(dataSource)
        log.debug("Running query: " + grailsApplication.config.queries.funds.vendorNameByCode)
        return sql.firstRow(grailsApplication.config.queries.funds.vendorNameByCode, [vendorCode])
    }

    def getFundNameById(fundId){
        if(fundId != ALL_FUNDS_ID){
            Sql sql = new Sql(dataSource)
            log.debug("Running query: " + grailsApplication.config.queries.funds.fundNameById)
            return sql.firstRow(grailsApplication.config.queries.funds.fundNameById, [fundId])
        }else{
            return [name:grailsApplication.config.AppConfig.allFundsNameInReport]
        }
    }

    private getAllocfundDataMap(sumfund, allocfund, repfund, container){
        if(container.get(libId) == null){
            container.put(libId, ['borrowing':['currentFiscalYear':[:], 'lastFiscalYear':[:]],
                    'lending':['currentFiscalYear':[:], 'lastFiscalYear':[:]]])
        }
        return container.get(libId)
    }

    private getCurrentDataMap(sumfund, allocfund, repfund, container){
        if(container.get(libId) == null){
            container.put(libId, ['borrowing':['currentFiscalYear':[:], 'lastFiscalYear':[:]],
                    'lending':['currentFiscalYear':[:], 'lastFiscalYear':[:]]])
        }
        return container.get(libId)
    }
}


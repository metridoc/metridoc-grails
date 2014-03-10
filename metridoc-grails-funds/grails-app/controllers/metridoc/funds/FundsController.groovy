package metridoc.funds

import org.codehaus.groovy.grails.validation.Validateable

class FundsController {

    def fundsService
    def grailsApplication

    static homePage = [
            title: "Voyager Funds",
            description: """
                 Expenditure activity during specified ledger and by materials allocation or vendor
            """
    ]

    static boolean isProtected = true

    private getLedgerOptions(){
        def ledgers = grailsApplication.config.AppConfig.ledgers
        def ledgerOptions = [];
        for(ledger in ledgers){
            def key = ledger.key
            def value = ledger.value
            ledgerOptions.add( ["id":key, "value":
                    (key + ": " + value.startDate + "-" +
                            (value.endDate != null ? value.endDate : "Present"))])
        }
        return ledgerOptions
    }

    private getReportPageModel(){
        def fundList = fundsService.getFundList()
        fundList.add(0, ['sumfund_id':1, 'sumfund_name': 'All Funds']);
        return [sortByOptions:grailsApplication.config.AppConfig.sortByOptions,
                ledgerOptions: getLedgerOptions(),
                vendorList: grailsApplication.config.AppConfig.vendors,
                fundList: fundList]
    }

    def index() {
        def indexModel = getReportPageModel()
        return  indexModel
    }
    private getLedgerDates(ledger){
        def dateFormat = grailsApplication.config.AppConfig.ledgerDateFormat;
        def startDate = FundsUtil.getDate(ledger.startDate, dateFormat)
        def endDate = ledger.endDate != null? FundsUtil.getDate(ledger.endDate + " 23:59:59", dateFormat + " hh:mm:ss"):new Date()
        return [ startDate,
                endDate]
    }
    def fund_report(ReportCommand cmd){
        if(!cmd.hasErrors()){
            boolean isfilterByFund = cmd.filterBy ==ReportCommand.FUND_FILTER_TYPE;
            def filterValue = isfilterByFund?cmd.fundId:cmd.vendorCode;
            if(cmd.reportType == ReportCommand.SUMMARY_REPORT_TYPE){
                def ledger = grailsApplication.config.AppConfig.ledgers.get(cmd.ledgerSumm);
                def (startDate, endDate) = getLedgerDates(ledger);
                def reportName = "funds_summary_"+FundsUtil.getValue(startDate)+"-"+FundsUtil.getValue(endDate);
                response.setHeader("Content-Disposition", "attachment;filename=\""+reportName+".xlsx\"");
                response.setContentType("application/vnd.ms-excel")
                println grailsApplication.config.AppConfig.ledgers.getClass()
                println cmd.ledgerSumm
                fundsService.doSummaryReport(response.outputStream, startDate, endDate, isfilterByFund, filterValue)
            }else{
                def ledgers = grailsApplication.config.AppConfig.ledgers
                def selectedLedgers = []
                def startLedger = -1;
                def endLedger = -1;
                cmd.ledgers.each {
                    def (startDate, endDate) = getLedgerDates(ledgers.get(it));
                    def ledger = ['startDate': startDate, 'endDate':endDate]
                    selectedLedgers.add(0, ledger);
                }
                def ledgerStr = cmd.ledgers.toString();
                ledgerStr = ledgerStr.substring(1, ledgerStr.length()-1);
                ledgerStr = ledgerStr.replaceAll(",[ ]?", "_");
                def reportName = "funds_expenditure_"+ledgerStr;
                response.setHeader("Content-Disposition", "attachment;filename=\""+reportName+".xlsx\"");
                response.setContentType("application/vnd.ms-excel")
                fundsService.doExpenditureReport(response.outputStream, selectedLedgers, isfilterByFund, filterValue)
            }
        }else{
            println "Some errors"
            request.reportCommand = cmd
            def indexModel = getReportPageModel()
            render(view:'index', model:indexModel)
        }
    }
}

@Validateable
class ReportCommand {
    public static final int SUMMARY_REPORT_TYPE = 0;
    public static final int EXPENDITURE_REPORT_TYPE = 1;

    public static final int FUND_FILTER_TYPE = 0;
    public static final int VENDOR_FILTER_TYPE = 1;

    int reportType = SUMMARY_REPORT_TYPE
    int ledgerSumm = -1
    int[] ledgers = [];
    int filterBy = FUND_FILTER_TYPE
    int fundId = -1
    String vendorCode

    static constraints = {
        ledgers(validator: {val, obj ->
            if(obj.reportType == EXPENDITURE_REPORT_TYPE){
                return val != null && val.size() > 0
            }
            return true
        })
    }
}


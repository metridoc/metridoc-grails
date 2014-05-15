package metridoc.funds

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.ss.usermodel.WorkbookFactory

//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

/**
*
* @author Narine Ghochikyan
*
*/
class ExpenditureReportGeneratorCSV {

	private sheet = new TreeMap()
	private int currentRowIndex;
	private int currentCellIndex;

	private selectedLedgers;

	private static final String[] SUB_HEADERS = ['Fund',
		'Allocation',
		'Reporting',
		'Title',
		'Publisher',
		'Bib ID'
    ]
	private static final String[] LEDGER_HEADERS = ['Invoice Date',
		'Split %',
		'P.O',
		'Qty.',
		'Vendor',
		'Amount'
	]

	private static final int COLUMN_OFFSET = SUB_HEADERS.length

	private static final int COLUMN_LEDGER_OFFSET = 2

	private static final SUMMARY_FUND_START_INDEX = 0;
	private static final ALLOCATED_FUND_START_INDEX = 1;
	private static final REPORTING_FUND_START_INDEX = 2;

	public ExpenditureReportGeneratorCSV(filteredByFund, filterLabel, selectedLedgers){
		this.selectedLedgers = selectedLedgers;	
		currentRowIndex = 0;
		String[] firstHeaders = ['Voyager Fund', 'Expenditure', filteredByFund?'Fund':'Vendor', filterLabel];
		FundsUtilCSV.createHeader(firstHeaders, sheet, currentRowIndex, 0);
		currentRowIndex++;
		
		createHeaders();
		
	}
	
	private void createHeaders(){
		currentRowIndex++;
        def totalCol =COLUMN_OFFSET+(COLUMN_LEDGER_OFFSET*LEDGER_HEADERS.length)
        for(int i = 0; i < totalCol; i++){
            sheet.put([currentRowIndex, i],",")
            sheet.put([currentRowIndex-1, i], ",")
        }
		FundsUtilCSV.createHeader(SUB_HEADERS, sheet, currentRowIndex, 0);
		
		def cellIndex = COLUMN_OFFSET;
		selectedLedgers.each { 
			def ledgerStr = FundsUtilCSV.getDateRangeString(it.startDate, it.endDate)
			sheet.put([currentRowIndex-1, cellIndex+COLUMN_LEDGER_OFFSET], ledgerStr+";")
			LEDGER_HEADERS.each {
		    	sheet.put([currentRowIndex-1, cellIndex], it)
				cellIndex++;
			}
		}
		currentRowIndex++;
	}
    //vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv
	def write(OutputStream out) throws IOException{		
		workbook.write(out);
		out.flush();
		out.close();
	}
    //^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

    private addCell(rowIndex, cellData){
		sheet.put([rowIndex, currentCellIndex], FundsUtilCSV.getValue(cellData)+";")
		currentCellIndex++;
	}
	
	private addLedgersAmounts(ledgerAmounts, rowIndex){
		def cellIndex = COLUMN_OFFSET + 5;
		for(int j = 0; j < selectedLedgers.size(); j++){
			sheet.put([rowIndex, cellIndex], ledgerAmounts.size()>j?FundsUtilCSV.getValue(ledgerAmounts.get(j)):0)
			cellIndex += LEDGER_HEADERS.length;
		}
	}
    //vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv

    def addData(data){
		data.each { sumFundId, sumFund ->
			//Row row = sheet.createRow(currentRowIndex);
			currentCellIndex = SUMMARY_FUND_START_INDEX;
            log.warn "${sumFund.sumfund_name}: Row ${currentRowIndex}:"
			addCell(currentRowIndex, sumFund.sumfund_name);
			addLedgersAmounts(sumFund.ledgersAmounts, currentRowIndex)
			currentRowIndex++;
			
			sumFund.allocFunds.each { allocFundId, allocFund -> 
				currentCellIndex = ALLOCATED_FUND_START_INDEX;
				currentRowIndex = sheet.createRow(currentRowIndex);
				addCell(currentRowIndex, allocFund.alloc_name);
				addLedgersAmounts(allocFund.ledgersAmounts, currentRowIndex)
				currentRowIndex++;
				
				allocFund.repFunds.each { repFundId, repFund ->
					currentCellIndex = REPORTING_FUND_START_INDEX;
					currentRowIndex = sheet.createRow(currentRowIndex);
					addCell(currentRowIndex, repFund.repfund_name);
					addLedgersAmounts(repFund.ledgersAmounts, currentRowIndex)
					currentRowIndex++;
					def itemStartColumn = currentCellIndex;
					repFund.items.each { itemBibId, item ->
						for(int i = 0; i < item.ledgerMaxSize; i++){
							currentCellIndex = itemStartColumn;
							currentRowIndex = sheet.createRow(currentRowIndex);
							addCell(currentRowIndex, item.title);
							addCell(currentRowIndex, item.publisher);
							addCell(currentRowIndex, itemBibId);
							for(int j = 0; j < selectedLedgers.size(); j++){
								def ledger = item.ledgers.get(j);
								if(ledger.size() > i){
									currentCellIndex = COLUMN_OFFSET + j*LEDGER_HEADERS.length;
									def ledgerItem = ledger.get(i)
									addCell(currentRowIndex, ledgerItem.status);
									addCell(currentRowIndex, ledgerItem.percent);
									addCell(currentRowIndex, ledgerItem.po_no);
									addCell(currentRowIndex, ledgerItem.quantity);
									addCell(currentRowIndex, ledgerItem.vendor);
									addCell(currentRowIndex, ledgerItem.cost);
								}
							}
							currentRowIndex++;
                            if(currentRowIndex%2500==0){
                                println("On currentRowIndex ${currentRowIndex}")
                            }
						}
					}
					//currentRowIndex++;
				}				
			}
		}
	}
}
package metridoc.funds

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
*
* @author Narine Ghochikyan
*
*/
class ExpenditureReportGenerator {	
	
	private Workbook workbook;
	private Sheet sheet;
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
	private static final REPROTING_FUND_START_INDEX = 2;
	
	public ExpenditureReportGenerator(filteredByFund, filterLabel, selectedLedgers){
		this.selectedLedgers = selectedLedgers;	
		workbook = new XSSFWorkbook();
		sheet = workbook.createSheet();
		currentRowIndex = 0;
		String[] firstHeaders = ['Voyager Fund', 'Expenditure', filteredByFund?'Fund':'Vendor', filterLabel];
		FundsUtil.createHeader(firstHeaders, sheet, currentRowIndex, 0);
		currentRowIndex++;
		
		createHeadres();
		
	}
	
	private void createHeadres(){
		currentRowIndex++;
		FundsUtil.createHeader(SUB_HEADERS, sheet, currentRowIndex, 0);
		
		Row ledgerHeadersRow = sheet.getRow(currentRowIndex);
		Row row = sheet.createRow(currentRowIndex-1);
		def cellIndex = COLUMN_OFFSET;
		selectedLedgers.each { 
			def ledgerStr = FundsUtil.getDateRangeString(it.startDate, it.endDate)
				Cell cell = row.createCell(cellIndex  + COLUMN_LEDGER_OFFSET);
				cell.setCellValue(ledgerStr);
				LEDGER_HEADERS.each { 
					Cell headerCell = ledgerHeadersRow.createCell(cellIndex);
					headerCell.setCellValue(it);
					cellIndex++;
				}	
		}
		currentRowIndex++;
	}
	def write(OutputStream out) throws IOException{		
		workbook.write(out);
		out.flush();
		out.close();
	}
	private addCell(row, cellData){
		Cell cell = row.createCell(currentCellIndex);
		cell.setCellValue(FundsUtil.getValue(cellData));
		currentCellIndex++;
	}
	
	private addLedgersAmounts(ledgerAmounts, row){
		def cellIndex = COLUMN_OFFSET + 5;
		for(int j = 0; j < selectedLedgers.size(); j++){
			Cell cell = row.createCell(cellIndex);
			cell.setCellValue(ledgerAmounts.size()>j?FundsUtil.getValue(ledgerAmounts.get(j)):0);
			cellIndex += LEDGER_HEADERS.length;
		}
	}
	
	def addData(data){
		data.each { sumFundId, sumFund ->
			Row row = sheet.createRow(currentRowIndex);
			currentCellIndex = SUMMARY_FUND_START_INDEX;
			addCell(row, sumFund.sumfund_name);
			addLedgersAmounts(sumFund.ledgersAmounts, row)
			currentRowIndex++;
			
			sumFund.allocFunds.each { allocFundId, allocFund -> 
				currentCellIndex = ALLOCATED_FUND_START_INDEX;
				row = sheet.createRow(currentRowIndex);
				addCell(row, allocFund.alloc_name);
				addLedgersAmounts(allocFund.ledgersAmounts, row)
				currentRowIndex++;
				
				allocFund.repFunds.each { repFundId, repFund ->
					currentCellIndex = REPROTING_FUND_START_INDEX;
					row = sheet.createRow(currentRowIndex);
					addCell(row, repFund.repfund_name);
					addLedgersAmounts(repFund.ledgersAmounts, row)
					currentRowIndex++;
					def itemStartColumn = currentCellIndex;
					repFund.items.each { itemBibId, item ->
						for(int i = 0; i < item.ledgerMaxSize; i++){
							currentCellIndex = itemStartColumn;
							row = sheet.createRow(currentRowIndex);
							addCell(row, item.title);
							addCell(row, item.publisher);
							addCell(row, itemBibId);
							for(int j = 0; j < selectedLedgers.size(); j++){
								def ledger = item.ledgers.get(j);
								if(ledger.size() > i){
									currentCellIndex = COLUMN_OFFSET + j*LEDGER_HEADERS.length;
									def ledgerItem = ledger.get(i)
									addCell(row, ledgerItem.status);
									addCell(row, ledgerItem.percent);
									addCell(row, ledgerItem.po_no);
									addCell(row, ledgerItem.quantity);
									addCell(row, ledgerItem.vendor);
									addCell(row, ledgerItem.cost);
								}
							}
							currentRowIndex++;
						}
					}
					//currentRowIndex++;
				}				
			}
		}
	}
}
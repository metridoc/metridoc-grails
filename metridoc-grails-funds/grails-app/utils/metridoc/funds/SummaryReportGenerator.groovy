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
class SummaryReportGenerator {	
	
	private Workbook workbook;
	private Sheet sheet;
	private int currentRowIndex;
	private int currentCellIndex;
	
	private Date startDate;
	private Date endDate;
	
	private String currentSummaryFund;
	private String currentAllocatedFund;
	private String currentReportingFund;
	
	private dateColumnMap = [:]
	
	
	private static String[] SUB_HEADERS = ['Fund',
		'Allocation',
		'Reporting',
		'Available Balance',
		'Net Allocation',
		'Total Expenditure',
		'Total Commit',
		'Pending Exp',
		'Total Purchase'
    ] 
	
	private static int MONTHS_NUM = 12;
	
	private static int COLUMN_OFFSET = SUB_HEADERS.length
	
	private static final SUMMARY_FUND_START_INDEX = 0;
	private static final ALLOCATED_FUND_START_INDEX = 1;
	private static final REPROTING_FUND_START_INDEX = 2;
	
	private int sumFundRowIndex;
	private int allocFundRowIndex;
	
	private int rfTotalCost = 0;
	private int allocFundTotalCost = 0;
	private totalsPerSumFund;
	
	public SummaryReportGenerator(filteredByFund, filterLabel, startDate, endDate){
		this.startDate = startDate;
		this.endDate = endDate;
		workbook = new XSSFWorkbook();//new SXSSFWorkbook();
		sheet = workbook.createSheet();
		currentRowIndex = 0;
		String[] firstHeaders = ['Voyager Fund', 'Summary', filteredByFund?'Fund':'Vendor', filterLabel];
		FundsUtil.createHeader(firstHeaders, sheet, currentRowIndex, 0);
		currentRowIndex++;
		
		createHeadres();
	}
	
	private void createHeadres(){
		def (startYear, startMonth) = FundsUtil.getYearMonth(startDate);
		def (endYear, endMonth) = FundsUtil.getYearMonth(endDate);
		
		Row row = sheet.createRow(currentRowIndex);
		Cell cell = row.createCell(0);
		cell.setCellValue(FundsUtil.getDateRangeString(startDate, endDate));
		currentRowIndex++;
		
		FundsUtil.createHeader(SUB_HEADERS, sheet, currentRowIndex, 0);
		Row rowYear = sheet.getRow(currentRowIndex - 1);
		Row rowMonth = sheet.getRow(currentRowIndex);
		int column = COLUMN_OFFSET;
		for(int y = startYear; y <= endYear; y++){
			cell = rowYear.createCell(column);
			cell.setCellValue(y);
			def currentStartMonth = y == startYear?startMonth:Calendar.JANUARY; 
			def currentEndMonth = y == endYear?endMonth:Calendar.DECEMBER;
			for(int m = currentStartMonth; m <= currentEndMonth; m++){
				cell = rowMonth.createCell(column);
				cell.setCellValue(FundsUtil.getMonthString(m));
				//month in db is 1 based
				dateColumnMap.putAt(y+"_"+(m+1), column);
				column++;
			}
		}
	}
	
	def write(OutputStream out) throws IOException{
		finishReportingFundRow()
		finishAllocRow()
		finishSummaryRow()
		
		workbook.write(out);
		out.flush();
		out.close();
	}
	private addCell(row, cellData){
		Cell cell = row.createCell(currentCellIndex);
		cell.setCellValue(FundsUtil.getValue(cellData));
		currentCellIndex++;
	}
	
	private doAllocRow(currentRowData){
		finishAllocRow();
		currentRowIndex++;
		currentAllocatedFund = currentRowData.alloc_name.trim()
		allocFundTotalCost = 0;
		allocFundRowIndex = currentRowIndex
		
		Row row = sheet.createRow(currentRowIndex);
		currentCellIndex = ALLOCATED_FUND_START_INDEX;
		addCell(row, currentAllocatedFund);
		
		//Skip one column
		currentCellIndex++;
		
		//Available Balance
		addCell(row, currentRowData.available_bal);
		//Net Allocation
		addCell(row, currentRowData.allo_net);
		//Total Exp
		addCell(row, currentRowData.expend_total);
		//Total Comm
		addCell(row, currentRowData.commit_total);
		//Pending Exp
		addCell(row, currentRowData.pending_expen);
		
		totalsPerSumFund.avbBalance += currentRowData.available_bal;
		totalsPerSumFund.netAlloc += currentRowData.allo_net;
		totalsPerSumFund.totalExpenditure += currentRowData.expend_total;
		totalsPerSumFund.totalCommit += currentRowData.commit_total;
		totalsPerSumFund.pendingExp += currentRowData.pending_expen;
	}
	
	
	private void initTotalsPerSumFund(){
		totalsPerSumFund = ['avbBalance':0,
			'netAlloc':0,
			'totalExpenditure':0,
			'totalCommit':0,
			'pendingExp':0,
			'totalPurchase':0];
	}
	
	private doSummaryRow(currentRowData){
		finishSummaryRow();
		initTotalsPerSumFund();
		
		currentRowIndex++;
		currentSummaryFund = currentRowData.sumfund_name.trim()
		sumFundRowIndex = currentRowIndex
		
		Row row = sheet.createRow(currentRowIndex);
		currentCellIndex = SUMMARY_FUND_START_INDEX;
		addCell(row, currentSummaryFund);
	}
	
	private finishSummaryRow(){
		if(currentSummaryFund != null){
			Row row = sheet.getRow(sumFundRowIndex);
			//skip 2 cols after name
			currentCellIndex = SUMMARY_FUND_START_INDEX + 3;
			addCell(row, totalsPerSumFund.avbBalance);
			addCell(row, totalsPerSumFund.netAlloc);
			addCell(row, totalsPerSumFund.totalExpenditure);
			addCell(row, totalsPerSumFund.totalCommit);
			addCell(row, totalsPerSumFund.pendingExp);
			addCell(row, totalsPerSumFund.totalPurchase);
		}
	}
	
	private finishAllocRow(){
		if(currentAllocatedFund != null){
			Row row = sheet.getRow(allocFundRowIndex);
			currentCellIndex = COLUMN_OFFSET-1;
			addCell(row, allocFundTotalCost);
			totalsPerSumFund.totalPurchase += allocFundTotalCost;
			currentAllocatedFund = null;
		}
	}
	
	private startReportingRow(currentRowData){
		currentRowIndex++;
		currentReportingFund = currentRowData.repfund_name.trim()
		
		Row row = sheet.createRow(currentRowIndex);
		currentCellIndex = REPROTING_FUND_START_INDEX;
		rfTotalCost = 0;
		//rep fund name
		addCell(row, currentReportingFund);
	}
	
	private finishReportingFundRow(){
		if(currentReportingFund != null){
			currentCellIndex=REPROTING_FUND_START_INDEX + 6;
			//fund total purchse
			Row row = sheet.getRow(currentRowIndex);
			addCell(row, rfTotalCost);
			
			allocFundTotalCost += rfTotalCost;
			
			currentReportingFund = null;
		}
	}
	def addRowData(currentRowData){
		if(currentSummaryFund == null || !currentRowData.sumfund_name.trim().equals(currentSummaryFund) ){
			finishReportingFundRow();
			finishAllocRow();
			doSummaryRow(currentRowData);
		}
		
		if(currentAllocatedFund == null || !currentRowData.alloc_name.trim().equals(currentAllocatedFund)){
			finishReportingFundRow();
			doAllocRow(currentRowData);
		}
		
		if(currentReportingFund == null || !currentRowData.repfund_name.trim().equals(currentReportingFund)){
			//finish prev row 
			finishReportingFundRow();
			//start new row
			startReportingRow(currentRowData);
		}
		currentCellIndex = dateColumnMap.getAt(currentRowData.year + "_"+currentRowData.month_int);
		//cost at year/month
		Row row = sheet.getRow(currentRowIndex);
		rfTotalCost += currentRowData.total_cost;
		addCell(row, currentRowData.total_cost);	
	}
	
}
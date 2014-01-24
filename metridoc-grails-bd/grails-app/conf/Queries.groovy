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

queries{
	borrowdirect{
		//--------------1. My Library Data Dump report query--------------
		dataDumpByLibrary = '''
		select bl.request_number as requestNumber,
		bl.pickup_location as pickupLocation,
		bl.request_date as requestDate,
		bl.process_date as processDate,
		pt.patron_type_desc as patronType,
		bl.author,
		bl.title,
		bl.publisher,
		bl.publication_place as publicationPlace,
		bl.publication_year as publicationYear,
		bl.isbn,
		bl.lccn,
		bl.oclc,
		bl.call_number as callNumber,
		cn.call_number as callNumberUnf,
		bl.supplier_code as supplierCode,
		IF(bl.supplier_code = 'List Exhausted', 1, 0) as isUnfilled,
		br.institution as borrower,
		lndr.institution as lender,
		shdl.min_ship_date as shipDate
		from {table_prefix}_bibliography bl
		left join {table_prefix}_min_ship_date shdl on bl.request_number = shdl.request_number
		left join {table_prefix}_patron_type pt on bl.patron_type = pt.patron_type
		left join {table_prefix}_institution br on bl.borrower = br.library_id
		left join {table_prefix}_institution lndr on bl.lender = lndr.library_id
		left join {table_prefix}_call_number cn on bl.request_number = cn.request_number
		where bl.request_date >= ? and bl.request_date < ? and (bl.borrower = ? or bl.lender = ?) and NOT (bl.borrower <=> bl.lender)
		group by bl.request_number
		''' //group by because of non unique {table_prefix}_call_number per request_number 
		
		//-------------2. Multiple Items Data Dump [System-Wide] report query-------------------------
		dataDumpMultipleItems = '''
		select bl.title,
			bl.publication_year as publicationYear,
			bl.isbn,
			bl.call_number as callNumber,
			cn.call_number as callNumberUnf,
			IF(bl.supplier_code = 'List Exhausted', 1, 0) as isUnfilled,
			br.institution as borrower,
			count(distinct bl.request_number) as itemTimes
			from {table_prefix}_bibliography bl
			left join {table_prefix}_institution br on bl.borrower = br.library_id
			left join {table_prefix}_call_number cn on bl.request_number = cn.request_number
			where bl.request_date between ? and ? and NOT (bl.borrower <=> bl.lender)
			group by bl.borrower, bl.call_number, bl.publication_year, bl.isbn, isUnfilled, bl.title 
			having count(distinct bl.request_number) >= ?
		''' /*and cn.holdings_seq=1*/


		//-------------------SUMMARY DASHBOARD reports------------------
		///---If query has {add_condition}, it is used for EZBorrow where there is a possibility 
		//to select subset of libraries for Summary report 
		
		
		countsPerLibraryMonthlyFilled = '''
			select IFNULL({lib_role},-1) as {lib_role}, month(request_date), count(*) as requestsNum from {table_prefix}_bibliography where request_date between ? and ? and NOT (supplier_code <=> 'List Exhausted') and NOT (borrower <=> lender) {add_condition} group by {lib_role}, month(request_date) WITH ROLLUP
		'''
		
		turnaroundPerLibrary = '''
			select IFNULL({lib_role},-1) as {lib_role}, AVG( DATEDIFF(process_date, min_ship_date)) as turnaroundShpRec,
			AVG(DATEDIFF(min_ship_date, request_date))as turnaroundReqShp,
			AVG(DATEDIFF(process_date, request_date)) as turnaroundReqRec
			from {table_prefix}_bibliography bl
			inner join {table_prefix}_min_ship_date bshl on bl.request_number = bshl.request_number
			where request_date between ? and ? and NOT (supplier_code <=> 'List Exhausted') and NOT (bl.borrower <=> bl.lender) {add_condition}
			group by {lib_role} WITH ROLLUP
		'''
		
		/**
		* for fillRate in Borrowing
		*/
		countsAllPerBorrower = '''
		   select IFNULL(borrower,-1) as borrower, count(distinct request_number) as requestsNum from {table_prefix}_bibliography where request_date between ? and ? and NOT (borrower <=> lender) {add_condition} group by borrower WITH ROLLUP
		'''

		/**
		* for fillRate in Leniding, rollup here, does not double count
		* in case of selected sublibs 'All Libraries' row is not the same as for borrowing
		*/
		countsAllPerLender = '''
		   select IFNULL(pd.library_id,-1) as lender, count(distinct bl.request_number) as requestsNum from {table_prefix}_bibliography bl
		   inner join {table_prefix}_print_date pd on bl.request_number = pd.request_number
		   where request_date between ? and ? and borrower != pd.library_id {add_condition} group by pd.library_id WITH ROLLUP
		'''

		
		/**
		* Fill rate in lending section for lib to lib
		*/
		countsAllPerBorrowerFromLib = '''
		   select borrower, count(distinct bl.request_number) as requestsNum from {table_prefix}_bibliography bl left join {table_prefix}_print_date pd on bl.request_number = pd.request_number
		   where request_date between ? and ? and pd.library_id = ? and borrower != pd.library_id and NOT (borrower <=> lender) group by borrower
		'''

		/**
		* Fill rate in borrowing section for lib to lib
		*/
		countsAllPerLenderToLib = '''
			select pd.library_id as lender, count(distinct bl.request_number) as requestsNum from {table_prefix}_bibliography bl inner join {table_prefix}_print_date pd on bl.request_number = pd.request_number
			where request_date between ? and ? and borrower = ? and borrower != pd.library_id and NOT (borrower <=> lender) group by pd.library_id
		'''

		/**
		* for fill rate in All Libraries row -Borrowing
		*/
		countsAllBorrowedByLib = '''
			select count(distinct request_number) as requestsNum from {table_prefix}_bibliography where
			request_date between ? and ? and borrower = ? and NOT (borrower <=> lender)
		'''

	   /**
		* for fill rate in All Libraries row  - Lending
		*/
		countsAllTouchedByLib = '''
			select count(distinct bl.request_number) as requestsNum from {table_prefix}_bibliography bl inner join {table_prefix}_print_date pd on bl.request_number = pd.request_number
			where request_date between ? and ? and pd.library_id = ? and NOT (borrower <=> lender)
		'''
	   
		countsPerPickupLocations = '''
	  		select pickup_location, count(request_number) from {table_prefix}_bibliography where request_date
			between ? and ? and borrower=? and NOT (supplier_code <=> 'List Exhausted') and NOT (borrower <=> lender) group by pickup_location WITH ROLLUP
		'''

		countsPerShelvingLocations = '''
			select supplier_code, count(request_number) from {table_prefix}_bibliography where request_date
			between ? and ? and lender=? and NOT (supplier_code <=> 'List Exhausted') and NOT (borrower <=> lender) group by supplier_code WITH ROLLUP
		'''

		//---------------------LC class dashboard-------------------
		requestedCallNos = '''
			select call_number from {table_prefix}_bibliography where request_date between ? and ? and call_number is not null and NOT (supplier_code <=> 'List Exhausted') and NOT (borrower <=> lender)
		'''

		//--------------------My Unfilled requests report query-----------------------
		libraryUnfilledRequests = '''
		select bl.request_number,
		title, 
		cn.call_number as callNo,
		publication_year as publicationYear, 
		isbn,
		GROUP_CONCAT(DISTINCT lr.institution ORDER BY pd.print_date SEPARATOR ', ') as lender
		from  {table_prefix}_bibliography bl 
		left join {table_prefix}_call_number cn on bl.request_number = cn.request_number
		left join {table_prefix}_print_date pd on bl.request_number = pd.request_number
		left join {table_prefix}_institution lr on pd.library_id = lr.library_id
		where request_date
			between ? and ? and bl.supplier_code = 'List Exhausted' and bl.borrower = ? and NOT (bl.borrower <=> bl.lender) 
			group by bl.request_number order by 
		''' /*and cn.holdings_seq=1*/

		//----------------Historical Summary Dashboard------------------------
		historicalCountsPerLibFilled = '''
		select IFNULL({lib_role},-1) as {lib_role},
		CASE WHEN MONTH(request_date)>={fy_start_month} THEN YEAR(request_date)+1
		ELSE YEAR(request_date) END AS fiscal_year,
		count(*) as requestsNum
		from {table_prefix}_bibliography b where NOT (supplier_code <=> 'List Exhausted') and NOT (borrower <=> lender) 
		{add_condition} group by fiscal_year, b.{lib_role} WITH ROLLUP
		'''
		
		/**
		* for fillRate in Borrowing
		*/
		historicalCountsAllPerBorrower = '''
		   select IFNULL(borrower,-1) as borrower,
			CASE WHEN MONTH(request_date)>={fy_start_month} THEN YEAR(request_date)+1
			ELSE YEAR(request_date) END AS fiscal_year,
			count(distinct request_number) as requestsNum
			from {table_prefix}_bibliography where NOT (borrower <=> lender) 
			{add_condition} group by fiscal_year, borrower WITH ROLLUP
		'''
		
		/**
		* for fillRate in Leniding,rollup here (does not double count),
		* in case of selected sublibs 'All Libraries' row is not the same as for borrowing
		*/
		historicalCountsAllPerLender = '''
		   select IFNULL(pd.library_id,-1) as lender, 
			CASE WHEN MONTH(request_date)>={fy_start_month} THEN YEAR(request_date)+1
			ELSE YEAR(request_date) END AS fiscal_year,
			count(distinct bl.request_number) as requestsNum 
			from {table_prefix}_bibliography bl inner join {table_prefix}_print_date pd on bl.request_number = pd.request_number   
			where borrower != pd.library_id and NOT (borrower <=> lender) 
			{add_condition} group by fiscal_year, pd.library_id WITH ROLLUP
		'''
		
		/**
		* Total num of items borrowed (requested) by selected lib per each lender(toucher) lib for
		* fill rate in borrowing section for lib to lib
		*/
	   historicalCountsAllPerLenderToLib = '''
		   select IFNULL(pd.library_id,-1) as lender,
		   CASE WHEN MONTH(request_date)>={fy_start_month} THEN YEAR(request_date)+1
		   ELSE YEAR(request_date) END AS fiscal_year,
		   count(distinct bl.request_number) as requestsNum
		   from {table_prefix}_bibliography bl inner join {table_prefix}_print_date pd on bl.request_number = pd.request_number
		   where borrower = ? and borrower != pd.library_id and NOT (borrower <=> lender) group by  fiscal_year, pd.library_id;
	   '''
		
		/**
		 * Total num of items lended (toched) by selected lib per each borrowing lib
		 * for fill rate in lending section for lib to lib
		 */
		historicalCountsAllPerBorrowerFromLib = '''
		   select IFNULL(borrower,-1) as borrower,
			CASE WHEN MONTH(request_date)>={fy_start_month} THEN YEAR(request_date)+1
			ELSE YEAR(request_date) END AS fiscal_year,
			count(distinct bl.request_number) as requestsNum from {table_prefix}_bibliography bl inner join {table_prefix}_print_date pd on bl.request_number = pd.request_number
		   where pd.library_id = ? and borrower != pd.library_id and NOT (borrower <=> lender) group by fiscal_year, borrower;
		'''
		
		/**
		 * Total num of items borrowed by selected lib
		 * fill rate in All Libraries row in lib to lib -Borrowing
		 */
		historicalCountsAllBorrowedByLib = '''
			select CASE WHEN MONTH(request_date)>={fy_start_month} THEN YEAR(request_date)+1
			ELSE YEAR(request_date) END AS fiscal_year,
			count(distinct request_number) as requestsNum from {table_prefix}_bibliography where
			borrower = ? and NOT (borrower <=> lender) group by fiscal_year
		'''

	   /**
	    * Total num of items touched by selected lib
		* for fill rate in All Libraries row  - Lending
		*/
		historicalCountsAllTouchedByLib = '''
			select CASE WHEN MONTH(request_date)>={fy_start_month} THEN YEAR(request_date)+1
			ELSE YEAR(request_date) END AS fiscal_year,
			count(distinct bl.request_number) as requestsNum from {table_prefix}_bibliography bl inner join {table_prefix}_print_date pd on bl.request_number = pd.request_number
			where pd.library_id = ? and NOT (borrower <=> lender) group by fiscal_year
		'''
		
		//-----------------General--------------------
		libraryList = '''select * from {table_prefix}_institution {add_condition} order by institution'''
		libraryById = '''select * from {table_prefix}_institution where library_id=?'''
	}
}

borrowdirect.db.column.borrower = 'borrower'
borrowdirect.db.column.lender = 'lender'

borrowdirect.db.column.title = 'title'
borrowdirect.db.column.callNo = 'callNo'
borrowdirect.db.column.publicationYear = 'publication_year'
borrowdirect.db.column.isbn = 'isbn'

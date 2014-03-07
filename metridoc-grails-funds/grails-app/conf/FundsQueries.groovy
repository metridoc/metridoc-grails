queries{
    funds{
        summary = '''
			 select
      sumfund_name, alloc_name, repfund_name, allo_net, sum(cost) as total_cost,
      commit_total, expend_total, available_bal, pending_expen, MONTH(status) as month_int,
      YEAR(status) as year
    from funds_load f
    where
      f.status between ? and ?
       and {add_condition}
    group by
      sumfund_name, alloc_name, repfund_name, allo_net, commit_total,
      expend_total, available_bal, pending_expen, month_int, year
      order by sumfund_name, alloc_name, repfund_name
		'''
        expenditure = '''
		select
      f.sumfund_id, f.sumfund_name, f.alloc_name,f.repfund_id, f.repfund_name, f.title, f.status, f.percent,
      f.po_no, f.quantity, f.cost, v.vendor_name as vendor, f.publisher, f.bib_id
    from funds_load f left join vendor v on f.vendor = v.vendor_code
    where
      f.status between ? and ?
      and {add_condition} order by sumfund_name, alloc_name, repfund_name
		'''
        vendorList = '''
		 	select vendor_code, vendor_name from vendor
		 '''
        /*fundList = '''
            select distinct sumfund_id, sumfund_name from funds_load where sumfund_id != 1 order by sumfund_name;
        '''*/
        fundList = '''
		 	select sumfund_id, sumfund_name from funds_list order by sumfund_name;
		 '''
        vendorNameByCode = '''
		 	select vendor_name as name from vendor where vendor_code=?
		 '''
        fundNameById = '''
		 	select sumfund_name as name from funds_list where sumfund_id=? limit 1;
		 '''
        /*fundNameById = '''
            select sumfund_name as name from funds_load where sumfund_id=? limit 1;
        '''*/
    }
    //whereClause
    //"and f.sumfund_id = '$fund'"
    //"and f.vendor = '$vendor'"
}
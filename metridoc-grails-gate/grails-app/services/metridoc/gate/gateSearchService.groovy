package metridoc.gate

import java.text.SimpleDateFormat

class GateSearchService {
	def searchWithFilters(Map params){
		Date startDatetime = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss").parse(params.startDatetime)
		Date endDatetime = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss").parse(params.endDatetime)
		
	}
}
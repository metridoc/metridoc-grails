<md:report>
    <div class="md-application-content">
        <div id="result-gateRecord" class="content scaffold-search" role="main" style="display: 'table-header-group'">
        	<table class="list summary" cellspacing="0">
        		<thead>
				    <tr>
				      <th class="mainColHeader" rowspan="2">Date Time</th>
				      <th colspan="3" rowspan="2">Entry Point</th>
				      <th colspan="2" rowspan="2">Affiliation</th>
				      <th colspan="2" rowspan="2">Center</th>
				      <th colspan="2" rowspan="2">USC</th>
				      <th colspan="2" rowspan="2">Department</th>
				    </tr>
			    </thead>
			    <tbody>
			    	<g:each in="${result}" var="row">
			    	  <tr>
			    	  	<td colspan="2">${row.entry_datetime}</td>
			    	  	<td colspan="2">${row.door_name}</td>
			    	  	<td colspan="2">${row.affiliation_name}</td>
			    	  	<td colspan="2">${row.center_name}</td>
			    	  	<td colspan="2">${row.USC_name}</td>
			    	  	<td colspan="2">${row.department_name}</td>
			    	  </tr>
			    	</g:each>
			    </tbody>
        	</table>
        </div>
    </div>
</md:report>
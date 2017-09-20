<md:report>
    <div class="md-application-content">
        <div id="result-gateRecord" class="content scaffold-search" role="main" style="display: 'table-header-group'">
        	<table class="list summary" cellspacing="0">
        		<thead>
				    <tr>
				      <g:each in="${allDoorNames}" var="door">
				      	<th colspan="1" rowspan="1">${door.name.substring(16)}</th>
				      </g:each>
				      <th colspan="1" rowspan="1">Total</th>
				    </tr>
			    </thead>
			    <tbody>
			    	<!-- <g:each in="${countByAffiliation}" var="row">
			    	  <tr>
			    	  </tr>
			    	</g:each> -->
			    </tbody>
        	</table>
        </div>
    </div>
</md:report>
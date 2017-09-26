<md:report>
    <div class="md-application-content">
        <div id="result-gateRecord" class="content scaffold-search" role="main" style="overflow-x:auto;">
        	<a name="top"></a>
        	<fieldset class="buttons">
				<a href="#AffiliationSummary">Affiliation Summary</a><br>
				<a href="#CenterSummary">Center Summary</a><br>
				<a href="#USCSummary">USC Summary</a>
                <g:link action="back" style="float: right;"> 
				   <input type="button" class="btn btn-danger" value="Back" class="button"/> 
				</g:link>
				<g:link action="export" style="float: right;"> 
				   <input type="button" class="btn btn-success" value="Download Data" class="button"/> 
				</g:link>
            </fieldset>

        	<!-- Affiliation Table -->
        	<a name="AffiliationSummary"></a>
        	<h3>Affiliation Summary From ${startDatetime} to ${endDatetime}</h3>
        	<table class="list summary" cellspacing="0">
        		<thead>
				    <tr>
				      <th>Affiliation</th>
				      <g:each in="${allDoorNames}" var="door">
				      	<th class="gate_summary_header">${door.name}</th>
				      </g:each>
				      <th>Total</th>
				    </tr>
			    </thead>
			    <tbody>
			    	<g:each in="${allAffiliationData}" var="row">
			    		<g:if test="${row.key != 'Total'}">
				    		<tr>
				    			<td>${row.key}</td>
				    			<g:each in="${row.value}" var="cell">
				    			<td>${cell}</td>
				    			</g:each> 
				    		</tr>
			    		</g:if>
			    	</g:each>
			    		<tr>
			    			<td>Total</td>
			    			<g:each in="${allAffiliationData['Total'].value}" var="cell">
			    				<td>${cell}</td>
			    			</g:each> 
			    		</tr>
			    </tbody>
        	</table>
        	<a href="#Top">Back to Top</a>

        	<!-- Center Table -->
        	<a name="CenterSummary"></a>
    		<h3>Center Summary From ${startDatetime} to ${endDatetime}</h3>
        	<table class="list summary" cellspacing="0">
        		<thead>
				    <tr>
				      <th>Center</th>
				      <g:each in="${allDoorNames}" var="door">
				      	<th class="gate_summary_header">${door.name}</th>
				      </g:each>
				      <th>Total</th>
				    </tr>
			    </thead>
			    <tbody>
			    	<g:each in="${allCenterData}" var="row">
			    		<g:if test="${row.key != 'Total'}">
				    		<tr>
				    			<td>${row.key}</td>
				    			<g:each in="${row.value}" var="cell">
				    			<td>${cell}</td>
				    			</g:each> 
				    		</tr>
			    		</g:if>
			    	</g:each>
			    		<tr>
			    			<td>Total</td>
			    			<g:each in="${allCenterData['Total'].value}" var="cell">
			    				<td>${cell}</td>
			    			</g:each> 
			    		</tr>
			    </tbody>
        	</table>
        	<a href="#Top">Back to Top</a>

        	<!-- USC Table -->
        	<a name="USCSummary"></a>
        	<h3>USC Summary From ${startDatetime} to ${endDatetime}</h3>
        	<table class="list summary" cellspacing="0">
        		<thead>
				    <tr>
				      <th>USC</th>
				      <g:each in="${allDoorNames}" var="door">
				      	<th class="gate_summary_header">${door.name}</th>
				      </g:each>
				      <th>Total</th>
				    </tr>
			    </thead>
			    <tbody>
			    	<g:each in="${allUSCData}" var="row">
			    		<g:if test="${row.key != 'Total'}">
				    		<tr>
				    			<td>${row.key}</td>
				    			<g:each in="${row.value}" var="cell">
				    			<td>${cell}</td>
				    			</g:each> 
				    		</tr>
			    		</g:if>
			    	</g:each>
			    		<tr>
			    			<td>Total</td>
			    			<g:each in="${allUSCData['Total'].value}" var="cell">
			    				<td>${cell}</td>
			    			</g:each> 
			    		</tr>
			    </tbody>
        	</table>
        	<a href="#Top">Back to Top</a>
        </div>
    </div>
</md:report>
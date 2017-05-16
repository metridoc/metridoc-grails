%{--
  - Copyright 2013 Trustees of the University of Pennsylvania. Licensed under the
  - 	Educational Community License, Version 2.0 (the "License"); you may
  - 	not use this file except in compliance with the License. You may
  - 	obtain a copy of the License at
  - 
  - http://www.osedu.org/licenses/ECL-2.0
  - 
  - 	Unless required by applicable law or agreed to in writing,
  - 	software distributed under the License is distributed on an "AS IS"
  - 	BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
  - 	or implied. See the License for the specific language governing
  - 	permissions and limitations under the License.  --}%

<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="metridoc.penn.util.DateUtil"%>
<%@page import="metridoc.penn.bd.LibReportCommand"%>
<%@page import="metridoc.penn.bd.DataDumpMultCommand"%>
<%@page import="metridoc.penn.bd.DataDumpCommand"%>
<%@page import="metridoc.penn.bd.BorrowDirectService"%>

<%@ page contentType="text/html;charset=ISO-8859-1"%>

<g:set var="currentYear" value="${Calendar.getInstance().get(Calendar.YEAR)}" />
<g:set var="dataDumpCommand" value="${request.dataDumpCommand != null? request.dataDumpCommand:new DataDumpCommand()}" />
<g:set var="dataDumpMultCommand" value="${request.dataDumpMultCommand != null? request.dataDumpMultCommand:new DataDumpMultCommand()}" />
<g:set var="libReportCommand" value="${request.libReportCommand != null ? request.libReportCommand: new LibReportCommand()}" />
<g:set var="dateFormat" value="MM/dd/yyyy" />
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<meta name="layout" content="bd_main" />
<calendar:resources lang="en" theme="tiger" />
<script>
	function selectionChanged(val){
		var disabledVal = "";
		if(val == ${LibReportCommand.UNFILLED_REQUESTS}){
			document.getElementById("mainSubmitBtn").value="Submit";
			document.getElementById("histSubmitBtn").style.display = "none";
		}else{
			disabledVal = 'disabled';
			document.getElementById("mainSubmitBtn").value="Current";
			document.getElementById("histSubmitBtn").style.display = "";
		}
	
		document.lib_data_summary_form.lr_from_value.disabled = disabledVal
		document.lib_data_summary_form.lr_to_value.disabled = disabledVal
		document.lib_data_summary_form.sortBy.disabled = disabledVal
		
		document.getElementById("lr_from-trigger").disabled = disabledVal
		document.getElementById("lr_to-trigger").disabled = disabledVal
	}
	function showHideLibraryFilter(){
		var display, imgSrc;
		if(document.summary_form.style.display == 'none'){
			display = "";
			imgSrc = '${request.getContextPath()}/images/close.gif';
		}else{
			display = "none";
			imgSrc = '${request.getContextPath()}/images/open.gif';
		}
		document.summary_form.style.display = display;
		document.getElementById('filterIcon').src = imgSrc;
		if(display != 'none'){
			document.getElementById('allLibArea').scrollIntoView(true)
		}
	}
</script>
</head>
<body>
	<div class="bd_body">
		<table width="700" height="179" border="0" cellpadding="12"
			cellspacing="0">
			<tr><td align="center" class="sectionTitle">
				DOWNLOAD MY LIBRARY'S DATA TO EXCEL
			</td></tr>
			<tr>
				<td valign="top">
				<g:hasErrors bean="${dataDumpCommand}">
  					<div class="errorMsg">
							Invalid input parameters!
  					</div>
				</g:hasErrors>
				<g:form name="data_dump_form" method="post" action="data_dump">
						<div class='formRow'>
							1. My Library Data Dump:
						</div>
						<div class='formRow'>
							Data For:
							<g:select name="library" from="${libraries}" value="${dataDumpCommand.library}" optionKey="library_id"
									optionValue="institution" /> 
						</div>
						<div class='formRow'>
						Specify Dates: 
						From: <g:render
									template="/bd_ezb/date_chooser"
									model="[currentYear:currentYear, fieldNamePrefix:'dd_from', commandBean: dataDumpCommand, minYear:minCalYear, dateFormat:dateFormat]" /> 
								To: <g:render
									template="/bd_ezb/date_chooser"
									model="[currentYear:currentYear, fieldNamePrefix:'dd_to', commandBean: dataDumpCommand, minYear:minCalYear, dateFormat:dateFormat]" /> </div>
						
						<div class='formRow'>
						<center>
								<input type="submit" name="Submit" value="Submit">
								<input type="reset" name="Reset" value="Reset">
						</center>
						</div>
					</g:form>
					<hr>
					<g:hasErrors bean="${dataDumpMultCommand}">
  					<div class="errorMsg">
						Invalid input parameters!
  					</div>
					</g:hasErrors>
					<g:form name="data_dump_mult_form" method="post" action="data_dump_mult">
					<div class='formRow'>
						2. Multiple Items Data Dump [System-Wide]: 
						</div>
						<div class='formRow'>
						Specify Dates: From: <g:render
									template="/bd_ezb/date_chooser"
									model="[currentYear:currentYear, fieldNamePrefix:'ddm_from', commandBean: dataDumpMultCommand, minYear:minCalYear, , dateFormat:dateFormat]" />
								To: <g:render
									template="/bd_ezb/date_chooser"
									model="[currentYear:currentYear, fieldNamePrefix:'ddm_to', commandBean: dataDumpMultCommand, minYear:minCalYear, dateFormat:dateFormat]" /> </div>
								
						<div class='formRow'>
						<% errorClass = dataDumpMultCommand.errors.hasFieldErrors("itemTimes") ? 'errorField' : ""; %>
						Number of Times Items Borrowed
								&gt;= <input type="text" name="itemTimes" value="${fieldValue(bean:dataDumpMultCommand,field:'itemTimes')}" class="${errorClass}"  />
								
						</div>
				
					<div class='formRow'>
						<center>
							<input type="submit" name="Submit" value="Submit"> 
							<input type="reset" name="Reset" value="Reset">
						</center>
						</div>
					</g:form>
				</td>
			</tr>
			<tr><td align="center" class="sectionTitle" id="allLibArea">
				ALL-LIBRARY REPORT OPTIONS
			</td></tr>
			<tr>
    <td>
    <div class="formRow">
    	<!-- include fill rate in the brackets if resurrecting the feature -->
       1. Summary Dashboard [filled request and turnaround times]:
        <div style="display:inline;float:right; text-align: left">
        <g:link action="summary">Current Year</g:link>&nbsp;|&nbsp;<g:link action="historical_summary">Historical</g:link> 
       </div>
       </div>
        	<g:if test="${BorrowDirectService.EZB_SERVICE_KEY.equals(serviceKey)}">
        	 <div class="formRow" style="margin-left:15px; padding-top:0px; font-style: italic;">
        	 Choose a comparison group:
        	<div style="display:inline;float:right; padding: 0 15px"><a href="javascript:showHideLibraryFilter()"><img src="${resource(plugin: "metridocBd", dir:'images',file:'open.gif')}" id="filterIcon"/>&nbsp;Filter</a></div>
        	 <g:form name="summary_form" method="post" action="summary" id="summary_form" style="margin:10px; display:none">
        	 <center>
        	 <table style="border:none">
        	<g:each var="library" status="i" in="${libraries}" >
        		<td class="libSelectionItem"><g:checkBox name="lIds" class="checkbox" value="${library.library_id}" checked="false"/>${library.institution}</td>
        		<g:if test="${i%2 == 1 }"></tr><tr></g:if>
        	</g:each>
        	<tr>
        	<td colspan="2" align="center">
        		<input type="submit" name="submitBtn" value="Current"> 
        		<input type="submit" name="submitBtn" value="Historical">
				<input type="reset" name="Reset" value="Reset"></td>
			</tr>
        	</table>
        	</center>	
        	</g:form>
        	</div>  
         	</g:if> 
            
        
        <hr/>
        <div  class="formRow">
       2. LC Class Dashboard [filled requests grouped by LC Class | first letter]:
        <div style="display:inline;float:right;">
        	<g:link action="lc_report">Current Year</g:link>&nbsp;|&nbsp;<g:link action="lc_report" params="[fiscalYear:currentFiscalYear-1]">Historical</g:link> 
    	</div>
    	</div>
       </td>
  </tr>
			
			<tr><td align="center" class="sectionTitle">
				MY LIBRARY REPORT OPTIONS
			</td></tr>			
  <tr>
    <td>
    <g:form name="lib_data_summary_form" method="post" action="lib_data_summary">
		<div class='formRow'>Select Your Library: 
              <g:select name="library" from="${libraries}" value="${libReportCommand.library}" optionKey="library_id"
									optionValue="institution" /> 
				</div>				
									<hr/>
		<div class='formRow'>
              <input name="reportType" type="radio" class="radio" value="${LibReportCommand.SUMMARY}"     
<%= libReportCommand.getReportType() == LibReportCommand.SUMMARY ? "checked=\"checked\"":"" %> onclick="selectionChanged(this.value)"/> 
<!-- include fill rate in the brackets if resurrecting the feature -->
Summary Dashboard [filled request and turnaround times]
      	</div>
        <hr/>       
		<div class='formRow'>
			  <input name="reportType" type="radio" class="radio" value="${LibReportCommand.LC_CLASS}" 
                <%= libReportCommand.getReportType() == LibReportCommand.LC_CLASS ? "checked=\"checked\"":"" %> onclick="selectionChanged(this.value)"/> LC Class Dashboard [filled requests grouped by LC Class | first letter]
        </div>
        <hr/>
        <div class='formRow'>
        <g:hasErrors bean="${libReportCommand}">
  					<div class="errorMsg">
						Invalid input parameters!
  					</div>
					</g:hasErrors>
        <input name="reportType" type="radio" class="radio" value="${LibReportCommand.UNFILLED_REQUESTS}" 
              <%= libReportCommand.getReportType() == LibReportCommand.UNFILLED_REQUESTS ? "checked=\"checked\"":"" %> onclick="selectionChanged(this.value)"/>
              List My Unfilled Requests &nbsp; [Please select date range for unfilled requests.]&nbsp; Sort By:
                
                <g:select name="sortBy" from="${sortByOptions}" value="${libReportCommand.sortBy}"
          		 valueMessagePrefix="datafarm.bd.unfilled.req.sortBy" />
              </div>
 	<div class='formRow'>
						Specify Dates: From: <g:render
									template="/bd_ezb/date_chooser"
									model="[currentYear:currentYear, fieldNamePrefix:'lr_from', commandBean: libReportCommand, minYear:minCalYear, dateFormat:dateFormat]" />
								To: <g:render
									template="/bd_ezb/date_chooser"
									model="[currentYear:currentYear, fieldNamePrefix:'lr_to', commandBean: libReportCommand, minYear:minCalYear, dateFormat:dateFormat]" /> </div>
	 <div class='formRow'>
         <center> <input type="submit" name="submitBtn" value="Current" id="mainSubmitBtn">
         <input type="submit" name="submitBtn" value="Historical"id="histSubmitBtn">
      <input type="reset" name="Reset" value="Reset"> </center>
  </div>
  </g:form>
    </td>
  </tr>
</table>		
	</div>
	<script>selectionChanged(${libReportCommand.getReportType()})</script>
</body>
</html>
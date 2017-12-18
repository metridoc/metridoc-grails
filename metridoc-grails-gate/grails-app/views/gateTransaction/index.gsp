<md:report>
    <div class="md-application-content">
        <div id="search-gateRecord" class="content scaffold-search" role="main">
        	
    	<md:form class="form-horizontal" action="query" method="GET">
            <fieldset class="form">
            	<div class="row-fluid">
                    <div class="span2 ">
                    	<span>&nbsp;&nbsp;Start Date&nbsp;&nbsp;</span><br>
	                	<input type="text" name="dateOfEntryRecord_start" class="input-search"
	                                       id="entry-record-start-date"/><br>
	                    <span>&nbsp;&nbsp;End Date&nbsp;&nbsp;</span><br>
	                    <input type="text" name="dateOfEntryRecord_end" class="input-search" id="entry-record-end-date"/>
                    </div>
                    <div class="span2 offset2">
                    	<span>&nbsp;&nbsp;Start Time&nbsp;&nbsp;</span><br>
	                	<g:textField id="entry-record-start-time" class="input-search" name="timeOfEntryRecord_start"
	                     value="" placeholder="HH:MM"/><br>
	                    <span>&nbsp;&nbsp;End Time&nbsp;&nbsp;</span><br>
	                    <g:textField id="entry-record-end-time" class="input-search" name="timeOfEntryRecord_end"
	                     value="" placeholder="HH:MM"/><br>
                    </div>
                </div>
                
                <div class="row-fluid">
                	<div class="span2">
	                	<label class="control-label" for="gateDoor">
	                        <g:message code="gateTransaction.gateDoor.label" default="Entry Point"/>
	                    </label>
	                	<g:select id="gateDoorSearch" class="input-search" name="gateDoorSearch"
		                          noSelection="${['0': 'All Gates']}" multiple="true" value="0"
		                          from="${allDoorNames}" style="width:470px"/><br>
                    </div>
                </div>
                <div class="row-fluid">
	                <div class="span2">
		            	<label class="control-label" for="gateCenter">
		                    <g:message code="gateTransaction.gateCenter.label" default="Center"/>
		                </label>
		            	<g:select id="gateCenterSearch" class="input-search" name="gateCenterSearch"
		                          noSelection="${['0': 'All Centers']}" multiple="true" value="0"
		                          from="${allCenterNames}"/>
	                </div>
	                <div class="span2 offset2">
	                    <label class="control-label" for="gateAffiliation">
	                        <g:message code="gateTransaction.gateAffiliation.label" default="Affiliation"/>
	                    </label>
	                	<g:select id="gateAffiliationSearch" class="input-search" name="gateAffiliationSearch"
		                          noSelection="${['0': 'All Affiliations']}" multiple="true" value="0"
		                          from="${allAffiliationNames}"/><br>
                    </div>
                </div>
                <div class="row-fluid">
	                <div class="span2">
	                	<label class="control-label" for="gateDepartment">
                        <g:message code="gateTransaction.gateDepartment.label" default="Department"/>
	                    </label>
	                	<g:select id="gateDepartmentSearch" class="input-search" name="gateDepartmentSearch"
		                          noSelection="${['0': 'All Departments']}" multiple="true" value="0"
		                          from="${allDepartmentNames}"/>
	                </div>
	                <div class="span2 offset2">
	                	<label class="control-label" for="gateUSC">
                        <g:message code="gateTransaction.gateUSC.label" default="Other Affiliations"/>
	                    </label>
	                	<g:select id="gateUSCSearch" class="input-search" name="gateUSCSearch"
		                          noSelection="${['0': 'All Other Affiliations']}" multiple="true" value="0"
		                          from="${allUSCNames}"/>
	                </div>
                </div>
            </fieldset>
            <fieldset class="buttons">
                <g:submitButton name="search" class="btn btn-success"
                                value="${message(code: 'default.button.search.label', default: 'Search')}"/>
            </fieldset>
        </md:form>

        </div>
    </div>
</md:report>
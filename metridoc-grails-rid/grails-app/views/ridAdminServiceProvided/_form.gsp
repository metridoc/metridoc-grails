<%@ page import="metridoc.rid.RidLibraryUnit; metridoc.rid.RidServiceProvided" %>

<g:render template="/ridAdminBase/baseForm" plugin="metridoc-rid"/>


<div class="control-group fieldcontain ${hasErrors(bean: ridInstance, field: 'ridLibraryUnit', 'error')} required">
    <label class="control-label" for="ridLibraryUnit">
        <g:message code="ridServiceProvided.ridLibraryUnit.label" default="Library Unit"/>
        <span class="required-indicator">*</span>
    </label>

    <div class="controls">
        <g:select id="ridLibraryUnit" style="width:120px" name="ridLibraryUnit.id"
                  from="${RidLibraryUnit.list()}"
                  optionKey="id" required="" value="${ridInstance?.ridLibraryUnit?.id}"
                  class="many-to-one"/>
    </div>
</div>
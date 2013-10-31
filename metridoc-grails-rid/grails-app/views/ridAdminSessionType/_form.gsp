<%@ page import="metridoc.rid.RidSessionType" %>

<g:render template="/ridAdminBase/baseForm" plugin="metridoc-rid"/>

<div class="control-group fieldcontain ${hasErrors(bean: ridInstance, field: 'ridLibraryUnit', 'error')} required">
    <label class="control-label" for="ridLibraryUnit">
        <g:message code="ridSessionTypeInstance.ridLibraryUnit.label" default="Library Unit"/>
        <span class="required-indicator">*</span>
    </label>

    <div class="controls">
        <g:select id="ridLibraryUnit" style="width:120px" name="ridLibraryUnit.id"
                  from="${metridoc.rid.RidLibraryUnit.list()}"
                  optionKey="id" required="" value="${ridInstance?.ridLibraryUnit?.id}"
                  class="many-to-one"/>
    </div>
</div>
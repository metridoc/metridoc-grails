<%@ page import="metridoc.rid.RidDepartment" %>

<div class="control-group fieldcontain required">
    <label class="control-label" for="name">
        <g:message code="ridDepartment.name.label" default="Name"/>
        <span class="required-indicator">*</span>
    </label>

    <div class="controls">
        <g:textField class="userInput" name="name" required="" value="${ridInstance?.name}"/>
    </div>
</div>

<div class="control-group fieldcontain">
    <label class="control-label" for="name">
        <g:message code="ridDepartment.fullName.label" default="Full Name"/>
    </label>

    <div class="controls">
        <g:textField class="userInput" name="fullName" value="${ridInstance?.fullName}"/>
    </div>
</div>

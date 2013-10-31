<%@ page import="metridoc.rid.RidLibraryUnit" %>

<div class="control-group fieldcontain required">
    <label class="control-label" for="name">
        <g:message code="ridLibraryUnit.name.label" default="Name"/>
        <span class="required-indicator">*</span>
    </label>

    <div class="controls">
        <g:textField class="userInput" name="name" required="" value="${ridInstance?.name}"/>
    </div>
</div>

<div class="control-group fieldcontain">
    <label class="control-label" for="spreadsheetUpload">Spreadsheet</label>

    <div class="controls">
        <input id="spreadsheetUpload" class="userInput" name="spreadsheetUpload" type="file" style="display: none"/>

        <div class="input-append">
            <input id="spreadsheetUploadPath" name="spreadsheetUploadPath" type="text" disabled="true"/>
            <a class="btn" onclick="$('input[id=spreadsheetUpload]').click();">Browse</a>
        </div>
    </div>
</div>

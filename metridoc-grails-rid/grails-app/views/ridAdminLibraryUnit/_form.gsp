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
  - 	permissions and limitations under the License.
  --}%

<%@ page import="metridoc.rid.RidLibraryUnit" %>
<r:require module="ridAdminBase"/>

<div class="control-group fieldcontain required">
    <div class="controls">
        <label class="control-label admin-label" for="name">
            <g:message code="ridLibraryUnit.name.label" default="Name"/>
            <span class="required-indicator">*</span>
        </label>
        <g:textField class="userInput" name="name" required="" value="${ridInstance?.name}"/>
    </div>
</div>

<div class="control-group fieldcontain">

    <div class="controls">
        <label class="control-label admin-label" for="spreadsheetUpload">Spreadsheet</label>

        <input id="spreadsheetUpload" class="userInput" name="spreadsheetUpload" type="file" style="display: none"/>

        <div class="input-append">
            <input id="spreadsheet-upload-path" name="spreadsheetUploadPath" type="text" disabled="true"/>
            <a class="btn" onclick="$('input[id=spreadsheetUpload]').click();">Browse</a>
        </div>
    </div>
</div>

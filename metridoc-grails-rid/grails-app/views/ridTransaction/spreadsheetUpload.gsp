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

<%@ page import="metridoc.rid.RidConsTransaction" %>
<g:set var="entityName" value="${message(code: 'ridTransaction.label', default: 'RidConsTransaction')}"/>

<md:report>
    <div class="md-application-content">
        <tmpl:toggle/>
        <tmpl:tabs/>
        <g:if test="${session.transType == "consultation"}">
            <div id="spreadsheetUpload-ridTransaction" class="content scaffold-search" role="main">
                <h1><g:message code="Upload Spreadsheet"/></h1>

                <md:form id="spreadsheetUploadForm" enctype="multipart/form-data" useToken="true">
                    <div class="control-group">
                        <div class="controls" id="spreadsheetUploadControls">
                            <input id="spreadsheetUpload" name="spreadsheetUpload" type="file" class="hidden-div"/>

                            <div class="input-append">
                                <input id="spreadsheet-upload-path" name="spreadsheetUploadPath" type="text"
                                       disabled="true"/>
                                <a class="btn" onclick="$('input[id=spreadsheetUpload]').click();">Browse</a>
                            </div>
                            <g:javascript>
                                $('input[id=spreadsheetUpload]').change(function () {
                                    var fileName = $(this).val().replace("C:\\fakepath\\", "");
                                    $('#spreadsheetUploadPath').val(fileName);
                                    checkInput();
                                });
                            </g:javascript>

                            <button class="btn" type="submit" id="submit-spreadsheet" name="_action_upload" disabled="true">
                                <i class="icon-upload-alt"></i> Upload
                            </button>

                            <g:javascript>
                                function checkInput() {
                                    if ($('input[id=spreadsheetUpload]').valueOf() != "") {
                                        document.getElementById("submitSpreadsheet").disabled = "";
                                    }
                                }
                            </g:javascript>

                        </div>

                        <h1><g:message code="Download Blank Spreadsheet"/></h1>

                        <div class="control-group">
                            <div class="controls" id="library-unit-controls">
                                <label for="ridLibraryUnit">Choose Library Unit</label>
                                <g:select id="ridLibraryUnit" class="input-wide" name="ridLibraryUnit.name"
                                          from="${metridoc.rid.RidLibraryUnit.list()}" optionKey="name" required=""/>
                            </div>

                            <div class="controls" id="spreadsheet-download">
                                <button class="btn" type="submit" name="_action_download">
                                    <i class="icon-download-alt"></i> Download Spreadsheet
                                </button>
                            </div>
                        </div>
                    </div>
                </md:form>
            </div>
        </g:if>
        <g:else>Not implemented</g:else>
    </div>
</md:report>
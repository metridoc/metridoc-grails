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

<%--
  Created by IntelliJ IDEA.
  User: tbarker
  Date: 2/4/13
  Time: 3:31 PM
  To change this template use File | Settings | File Templates.
--%>


<md:header>Metridoc Configuration</md:header>
<g:form class="form-horizontal" enctype="multipart/form-data">
    <div class="control-group">

        %{--TODO: move all scripts and css to separate js and css files--}%
        <div class="controls">
            <input id="metridocConfig" name="metridocConfig" type="file"/>

            <div class="input-append">
                <!--suppress HtmlFormInputWithoutLabel -->
                <input id="metridocConfigPath" name="metridocConfigPath" type="text" disabled="true"/>
                <a class="btn" onclick="$('input[id=metridocConfig]').click();">Browse</a>
            </div>
            <g:javascript>
                $('input[id=metridocConfig]').change(function () {
                    var fileName = $(this).val().replace("C:\\fakepath\\", "");
                    $('#metridocConfigPath').val(fileName);
                });
            </g:javascript>
        </div>

        <div class="controls">
            <g:if test="${metridocConfigExists}">
                <button class="btn" type="submit" name="_action_download">
                    <i class="icon-download-alt"></i> Download
                </button>
            </g:if>
            <button class="btn" type="submit" name="_action_upload">
                <i class="icon-upload-alt"></i> Upload
            </button>
        </div>
    </div>
</g:form>

<md:header>General Settings</md:header>
<md:form class="form-horizontal" action="updateGeneralSettings" method="POST">
    <label for="reportIssueEmails" class="control-label">Report Issues Email:</label>
    <div class="control-group">
        <div class="controls">
            <g:textField name="reportIssueEmails" placeholder="foo@foo.com bar@bar.com" value="${reportIssueEmails}"></g:textField>
        </div>
    </div>
    <label for="rememberMeCookieAge" class="control-label">Security Cookie Age:</label>
    <div class="control-group">
        <div class="controls">
            <g:textField name="rememberMeCookieAge" placeholder="360" value="${rememberMeCookieAge}"></g:textField>
        </div>
    </div>
    <label class="control-label" id="dateLabel">Server Shutdown Date:</label>

    <input type="text" name="shutdownDate" id="shutdownDate"
           value="${shutdownDate}" id="dp1" />
    <button class="btn" id="clearDate">Clear</button>
    <md:cgButton icon="icon-edit" class="active-on-change">Update</md:cgButton>
</md:form>

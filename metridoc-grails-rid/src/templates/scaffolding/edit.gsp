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

<%=packageName%>
<g:set var="entityName" value="\${message(code: '${domainClass.propertyName}.label', default: '${className}')}"/>
%{--<md:report>--}%
%{--<div class="md-application-content">--}%

<div id="edit-${domainClass.propertyName}" class="content scaffold-edit" role="main">
    <h1><g:message code="default.edit.label" args="[entityName]"/></h1>
    <g:hasErrors bean="\${${propertyName}}">
        <ul class="errors" role="alert">
            <g:eachError bean="\${${propertyName}}" var="error">
                <li <g:if test="\${error in org.springframework.validation.FieldError}">data-field-id="\${error.field}"</g:if>><g:message
                        error="\${error}"/></li>
            </g:eachError>
        </ul>
    </g:hasErrors>

    <g:form method="post" <%=multiPart ? ' enctype="multipart/form-data"' : '' %>>
    <g:hiddenField name="id" value="\${${propertyName}?.id}"/>
    <g:hiddenField name="version" value="\${${propertyName}?.version}"/>
    <div class="form-horizontal" style="margin-top: 2em">
        <tmpl:form/>
    </div>
    %{--<fieldset class="buttons">--}%
    <g:actionSubmit style="float: right; margin-left: 1em" class="btn btn-success" action="update"
                    value="\${message(code: 'default.button.update.label', default: 'Update')}"/>
    %{--<g:actionSubmit style="float: right" class="btn btn-danger" action="delete" value="\${message(code: 'default.button.delete.label', default: 'Delete')}"--}%
    %{--formnovalidate="" onclick="return confirm('\${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />--}%
    %{--</fieldset>--}%
    </g:form>
</div>
%{--</div>--}%
%{--</md:report>--}%

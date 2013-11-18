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

<div id="create-${domainClass.propertyName}" class="content scaffold-create" role="main">
    <h1><g:message code="default.create.label" args="[entityName]"/></h1>
    <g:hasErrors bean="\${${propertyName}}">
        <ul class="errors" role="alert">
            <g:eachError bean="\${${propertyName}}" var="error">
                <li <g:if test="\${error in org.springframework.validation.FieldError}">data-field-id="\${error.field}"</g:if>><g:message
                        error="\${error}"/></li>
            </g:eachError>
        </ul>
    </g:hasErrors>

    <md:form class="form-horizontal" action="save" <%=multiPart ? ' enctype="multipart/form-data"' : '' %>>
%{--<fieldset class="form">--}%
    <div style="margin-top: 2em">
        <tmpl:form/>
    </div>
    %{--</fieldset>--}%
    <fieldset class="buttons">
        <g:submitButton name="create" class="btn btn-danger"
                        value="\${message(code: 'default.button.create.label', default: 'Create')}"/>
    </fieldset>
    </md:form>
</div>
%{--</div>--}%
%{--</md:report>--}%

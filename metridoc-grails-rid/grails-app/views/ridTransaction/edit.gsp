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

<%@ page import="metridoc.rid.RidConsTransaction; metridoc.rid.RidInsTransaction" %>
<g:if test="${session.transType == "consultation"}">
    <g:set var="entityName" value="${message(code: 'ridTransaction.label', default: 'RidConsTransaction')}"/>
</g:if>
<g:else>
    <g:set var="entityName" value="${message(code: 'ridTransaction.label', default: 'RidInsTransaction')}"/>
</g:else>
<md:report>
    <div class="md-application-content">
        <tmpl:toggle/>
        <tmpl:tabs/>

        <g:render template="/ridAdminTransaction/modal" plugin="metridocRid"
                  model="[title: 'Academic Departments', myID: 'myDepartment']"/>

        <div id="edit-ridTransaction" class="content scaffold-edit" role="main">
            <h1><g:message code="default.edit.label" args="[entityName]"/></h1>
            <g:if test="${flash.message}">
                <div class="message" role="status">${flash.message}</div>
            </g:if>

            <g:hasErrors bean="${ridTransactionInstance}">
                <div class="errors">
                    <g:renderErrors bean="${ridTransactionInstance}" as="list"/>
                </div>
            </g:hasErrors>
            <g:form method="post" useToken="true">
                <g:hiddenField name="id" value="${ridTransactionInstance?.id}"/>
                <g:hiddenField name="version" value="${ridTransactionInstance?.version}"/>
                <fieldset class="form">
                    <tmpl:form/>
                </fieldset>
                <fieldset class="buttons">
                    <g:actionSubmit class="btn btn-success" action="update"
                                    value="${message(code: 'default.button.update.label', default: 'Update')}"/>
                    <g:actionSubmit class="btn btn-danger" action="delete"
                                    value="${message(code: 'default.button.delete.label', default: 'Delete')}"
                                    formnovalidate=""
                                    onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');"/>
                </fieldset>
            </g:form>
        </div>
    </div>
</md:report>
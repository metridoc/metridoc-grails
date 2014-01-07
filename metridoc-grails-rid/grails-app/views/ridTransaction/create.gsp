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

<%@ page import="org.apache.shiro.SecurityUtils; metridoc.rid.RidConsTransaction; metridoc.rid.RidInsTransaction" %>

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

        %{--TEMPORARY REMOVE TEMPLATES--}%
        %{--<g:render template="/ridAdminTransaction/modal" plugin="metridocRid"
                  model="[title: entityName + ' Creation']"/>--}%

        <div id="create-ridTransaction" class="content scaffold-create" role="main">

    %{--TEMPORARY REMOVE TEMPLATES--}%
%{--            <g:if test="${SecurityUtils.getSubject().getPrincipal()}">
                <h1>
                    <a class="modal-label" data-toggle="modal" href="templateList" data-target="#myModal">
                        <i class="icon-file-alt">Use Template</i>
                    </a>
                </h1>
            </g:if>--}%


            <g:hasErrors bean="${ridTransactionInstance}">
                <div class="errors">
                    <g:renderErrors bean="${ridTransactionInstance}" as="list"/>
                </div>
            </g:hasErrors>

            <md:form controller="RidTransaction" useToken="true" id="createForm">

                <fieldset class="form">
                    <tmpl:form/>
                </fieldset>


                <fieldset class="buttons active-on-change">
                    <input id="resetButton" class="btn btn-danger active-on-change" type="reset" value="Reset"/>
                    <g:actionSubmit action="save" name="create" class="btn btn-success active-on-change"
                                    value="${message(code: 'default.button.create.label', default: 'Create')}"/>

                    <g:if test="${SecurityUtils.getSubject().getPrincipal()}">
                    %{--TEMPORARY REMOVE TEMPLATES--}%
                        %{--<g:if test="${params.tmp}">
                            <g:hiddenField name="id" value="${params.tmp}"/>
                            <g:hiddenField name="isTemplate" value="true"/>
                            <g:actionSubmit class="btn btn-danger" action="delete" style="float: right"
                                            value="Delete template" formnovalidate=""
                                            onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');"/>
                        </g:if>
                        <g:actionSubmit action="remember" style="float: right; margin-right: 5px"
                                        name="remember" class="btn btn-warning"
                                        value="${message(code: 'default.button.remember.label', default: 'Remember as new template')}"
                                        onmouseover="removeRequired()" onmouseout="setRequired()"/>--}%
                    </g:if>

                </fieldset>
            </md:form>
        </div>

    </div>
</md:report>
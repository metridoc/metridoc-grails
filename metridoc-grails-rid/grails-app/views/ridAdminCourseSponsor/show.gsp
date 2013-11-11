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

<%@ page import="metridoc.rid.RidCourseSponsor" %>
<g:set var="entityName" value="${message(code: 'ridCourseSponsor.label', default: 'RidCourseSponsor')}"/>

<md:report>
    <div class="md-application-content">

        <div id="show-ridCourseSponsor" class="content scaffold-show" role="main">
            <h1><g:message code="default.show.label" args="[entityName]"/></h1>
            <g:if test="${flash.message}">
                <div class="message" role="status">${flash.message}</div>
            </g:if>
            <ol class="property-list ridCourseSponsor">

                <g:if test="${ridInstance?.name}">
                    <li class="fieldcontain">
                        <span id="name-label" class="property-label"><g:message code="ridCourseSponsor.name.label"
                                                                                default="Name"/></span>

                        <span class="property-value" aria-labelledby="name-label"><g:fieldValue
                                bean="${ridInstance}" field="name"/></span>

                    </li>
                </g:if>

                <g:if test="${ridnstance?.inForm}">
                    <li class="fieldcontain">
                        <span id="inForm-label" class="property-label"><g:message code="ridCourseSponsor.inForm.label"
                                                                                  default="In Form"/></span>

                        <span class="property-value" aria-labelledby="inForm-label"><g:fieldValue
                                bean="${ridInstance}" field="inForm"/></span>

                    </li>
                </g:if>

            %{--<g:if test="${ridCourseSponsorInstance?.ridTransaction}">--}%
            %{--<li class="fieldcontain">--}%
            %{--<span id="ridTransaction-label" class="property-label"><g:message code="ridCourseSponsor.ridTransaction.label" default="Rid Transaction" /></span>--}%
            %{----}%
            %{--<g:each in="${ridCourseSponsorInstance.ridTransaction}" var="r">--}%
            %{--<span class="property-value" aria-labelledby="ridTransaction-label"><g:link controller="ridTransaction" action="show" id="${r.id}">${r?.encodeAsHTML()}</g:link></span>--}%
            %{--</g:each>--}%
            %{----}%
            %{--</li>--}%
            %{--</g:if>--}%

            </ol>
            <g:form>
                <fieldset class="buttons">
                    <g:hiddenField name="id" value="${ridInstance?.id}"/>
                    <g:actionSubmit class="btn btn-success" action="edit" id="${ridInstance?.id}"
                                    value="${message(code: 'default.button.edit.label', default: 'Edit')}"/>
                    <g:actionSubmit class="btn btn-danger" action="delete"
                                    value="${message(code: 'default.button.delete.label', default: 'Delete')}"
                                    onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');"/>
                </fieldset>
            </g:form>
        </div>
    </div>
</md:report>

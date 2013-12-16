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
%{--<!doctype html>--}%
%{--<html>--}%
%{--<head>--}%
%{--<meta name="layout" content="main">--}%
%{--<g:set var="entityName" value="${message(code: 'ridTransaction.label', default: 'RidConsTransaction')}" />--}%
%{--<title><g:message code="default.list.label" args="[entityName]" /></title>--}%
%{--</head>--}%
%{--<body>--}%

<md:report>
    <r:require module="tableModule"/>
    <!--[if !IE]><!-->
    <r:external dir="css" file="floating_table.css" plugin="metridoc-rid"/>
    <!--<![endif]-->

    <div class="md-application-content">
        <tmpl:toggle/>
        <tmpl:tabs/>
        <g:if test="${session.transType == "consultation"}">
            <div id="list-ridTransaction" class="content scaffold-list" role="main">
                <h1>
                    <g:message code="default.list.label" args="[entityName]"/>
                    <g:if test="${ridTransactionAllList.size() > 0}">
                        <g:link action="export" params="${params}">
                            <i id="exportToFile"
                               title="Save the current transaction list as an excel file" class="icon-download-alt"></i>
                        </g:link>
                    </g:if>
                </h1>


                <table class="table table-striped table-hover">
                    <thead>
                    <tr>

                        <g:sortableColumn property="userQuestion"
                                          title="${message(code: 'ridTransaction.userQuestion.label', default: 'User Question')}"/>

                        <g:sortableColumn property="staffPennkey"
                                          title="${message(code: 'ridTransaction.staffPennkey.label', default: 'Staff Pennkey')}"/>

                        <g:sortableColumn property="dateOfConsultation"
                                          title="${message(code: 'ridTransaction.dateOfConsultation.label', default: 'Date of Consultation')}"/>

                        <g:sortableColumn property="ridLibraryUnit"
                                          title="${message(code: 'ridTransaction.ridLibraryUnit.label', default: 'Library Unit')}"/>

                        <g:sortableColumn property="notes"
                                          title="${message(code: 'ridTransaction.notes.label', default: 'Notes')}"/>

                    </tr>
                    </thead>
                    <tbody>
                    <g:each in="${ridTransactionInstanceList}" status="i" var="ridTransactionInstance">
                        <tr class="${(i % 2) == 0 ? 'even' : 'odd'} clickable-row"
                            onclick="window.location = 'show/${ridTransactionInstance.id}'">
                            <%
                                userQ = ridTransactionInstance.userQuestion
                                if (userQ != null && userQ.length() > 12)
                                    userQ = userQ.substring(0, 12) + "..."
                            %>
                            <td>
                                %{--<g:link action="show" id="${ridTransactionInstance.id}"--}%
                                %{--title="${ridTransactionInstance.userQuestion}">--}%
                                ${userQ}
                                %{--</g:link>--}%
                            </td>

                            <td>${fieldValue(bean: ridTransactionInstance, field: "staffPennkey")}</td>

                            <td><g:formatDate format="yyyy-MM-dd"
                                              date="${ridTransactionInstance?.dateOfConsultation}"/></td>
                            %{--<td>${fieldValue(bean: ridTransactionInstance, field: "dateOfConsultation")}</td>--}%

                            <td>${fieldValue(bean: ridTransactionInstance, field: "ridLibraryUnit")}</td>

                            <td>${fieldValue(bean: ridTransactionInstance, field: "notes")}</td>

                        </tr>
                    </g:each>
                    </tbody>
                </table>
                <g:if test="${ridTransactionInstanceTotal > params.max}">
                    <div class="pagination">
                        <g:paginate action="query" total="${ridTransactionInstanceTotal}" params="${params}"
                                    next="&gt;&gt;"
                                    prev="&lt;&lt;"/>
                    </div>
                </g:if>
            </div>
        </g:if>

        <g:else>
            <g:if env="development">
                <div id="list-ridTransaction" class="content scaffold-list" role="main">
                    <h1>
                        <g:message code="default.list.label" args="[entityName]"/>
                        <g:if test="${ridTransactionAllList.size() > 0}">
                            <g:link action="export" params="${params}">
                                <i id="exportToFile"
                                   title="Save the current transaction list as an excel file"
                                   class="icon-download-alt"></i>
                            </g:link>
                        </g:if>
                    </h1>


                    <table class="table table-striped table-hover">
                        <thead>
                        <tr>

                            <g:sortableColumn property="instructorPennkey"
                                              title="${message(code: 'ridTransaction.instructorPennkey.label', default: 'Instructor Pennkey')}"/>

                            <g:sortableColumn property="dateOfConsultation"
                                              title="${message(code: 'ridTransaction.dateOfInstruction.label', default: 'Date of Instruction')}"/>

                            <g:sortableColumn property="ridLibraryUnit"
                                              title="${message(code: 'ridTransaction.ridLibraryUnit.label', default: 'Library Unit')}"/>

                            <g:sortableColumn property="ridLocation"
                                              title="${message(code: 'ridTransaction.ridLocation.label', default: 'Location')}"/>

                            <g:sortableColumn property="notes"
                                              title="${message(code: 'ridTransaction.notes.label', default: 'Notes')}"/>
                        </tr>
                        </thead>
                        <tbody>
                        <g:each in="${ridTransactionInstanceList}" status="i" var="ridTransactionInstance">
                            <tr class="${(i % 2) == 0 ? 'even' : 'odd'} clickable-row"
                                onclick="window.location = 'show/${ridTransactionInstance.id}'">

                                <td>${fieldValue(bean: ridTransactionInstance, field: "instructorPennkey")}</td>

                                <td><g:formatDate format="yyyy-MM-dd"
                                                  date="${ridTransactionInstance?.dateOfInstruction}"/></td>
                                %{--<td>${fieldValue(bean: ridTransactionInstance, field: "dateofInstruction")}</td>--}%

                                <td>${fieldValue(bean: ridTransactionInstance, field: "ridLibraryUnit")}</td>

                                <td>${fieldValue(bean: ridTransactionInstance, field: "location")}</td>

                                <td>${fieldValue(bean: ridTransactionInstance, field: "notes")}</td>

                            </tr>
                        </g:each>
                        </tbody>
                    </table>
                    <g:if test="${ridTransactionInstanceTotal > params.max}">
                        <div class="pagination">
                            <g:paginate action="query" total="${ridTransactionInstanceTotal}" params="${params}"
                                        next="&gt;&gt;"
                                        prev="&lt;&lt;"/>
                        </div>
                    </g:if>
                </div>
            </g:if>
            <g:else>
                Not yet Implemented
            </g:else>
        </g:else>
    </div>
</md:report>
%{--</body>--}%
%{--</html>--}%

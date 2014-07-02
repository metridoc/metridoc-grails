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

<%@ page import="metridoc.rid.RidDepartment; metridoc.rid.RidConsTransaction; metridoc.rid.RidInsTransaction" %>
<g:set var="entityName" value="${message(code: 'ridTransaction.label', default: 'RidConsTransaction')}"/>

<md:report>
<div class="md-application-content">

<tmpl:toggle/>
<tmpl:tabs/>
<g:if test="${session.transType == "consultation"}">
    <div id="search-ridConsTransaction" class="content scaffold-search" role="main">
    <!--<h1><g:message code="RidTransaction Search"/></h1>-->
        <g:if test="${flash.message}">
            <div class="message" role="status">${flash.message}</div>
        </g:if>

        <md:form class="form-horizontal" action="query" method="GET">
            <fieldset class="form">
                <div class="row-fluid">
                    <div class="span4 ">
                        <div class="control-group fieldcontain">
                            <label class="control-label" for="dateOfConsultation">
                                <g:message code="ridTransaction.dateOfConsultation.label"
                                           default="Consultation Date Between"/>
                            </label>

                            <div class="controls">
                                <input type="text" name="dateOfConsultation_start" class="input-search"
                                       id="start-date"/><br>
                                <span class="search-date-middle">&nbsp;&nbsp;and&nbsp;&nbsp;</span><br>
                                <input type="text" name="dateOfConsultation_end" class="input-search" id="end-date"/>
                            </div>
                        </div>
                    </div>

                    <div class="span2">
                        <div class="control-group fieldcontain">
                            <label class="control-label" for="ridLibraryUnit">
                                <g:message code="ridTransaction.ridLibraryUnit.label" default="Library Unit"/>
                            </label>

                            <div class="controls">
                                <g:select id="ridLibraryUnitSearch" class="input-search" name="ridLibraryUnitSearch"
                                          noSelection="${['0': 'All Units']}" optionKey="id" multiple="true" value="0"
                                          from="${metridoc.rid.RidLibraryUnit.list()}"/>
                            </div>
                        </div>
                    </div>

                </div>

                <div class="row-fluid">
                    <div class="span2 ">
                        <div class="control-group fieldcontain">
                            <label class="control-label" for="staffPennkey">
                                <g:message code="ridTransaction.staffPennkey.label" default="Staff Pennkey"/>
                            </label>

                            <div class="controls">
                                <g:textField id="staffPennkey" class="trans-user-input input-search" name="staffPennkey"
                                             value=""/>
                            </div>
                        </div>

                    </div>

                    <div class="span2 offset2">
                        <div class="control-group fieldcontain">
                            <label class="control-label" for="userName">
                                <g:message code="ridTransaction.userName.label" default="User Name"/>
                            </label>

                            <div class="controls">
                                <g:textField id="userName" class="trans-user-input input-search" name="userName"
                                             value=""/>
                            </div>
                        </div>
                    </div>

                </div>

                <div class="row-fluid">

                    <div class="span2 ">
                        <div class="control-group fieldcontain">
                            <label class="control-label" for="ridSchool">
                                <g:message code="ridTransaction.ridSchool.label" default="School"/>
                            </label>

                            <div class="controls">
                                <g:select id="ridSchoolSearch" class="input-search" name="ridSchoolSearch"
                                          noSelection="${['0': 'All Schools']}" optionKey="id" multiple="true" value="0"
                                          from="${metridoc.rid.RidSchool.where { inForm == 1 }.list()}"/>
                            </div>
                        </div>
                    </div>

                    <div class="span2 offset2">
                        <div class="control-group fieldcontain">
                            <label class="control-label" for="ridDepartment">
                                <g:message code="ridTransaction.ridDepartment.label" default="Department"/>
                            </label>

                            <div class="controls">
                                <g:select id="ridDepartmentSearch" class="input-search" name="ridDepartmentSearch"
                                          noSelection="${['0': 'All Departments']}" multiple="true" value="0"
                                          optionKey="id"
                                //optionValue="${{ it.name.empty ? 'NOT SPECIFIED' : it.name }}"
                                //from="${metridoc.rid.RidDepartment.list()}"
                                          from="${metridoc.rid.RidDepartment.where { name != "" }.list()}"/>
                            </div>
                        </div>
                    </div>

                </div>


                <div class="control-group fieldcontain">
                    <label class="control-label" for="userQuestion">
                        <g:message code="ridTransaction.userQuestion.label" default="User Question"/>
                    </label>

                    <div class="controls">
                        <g:textField id="userQuestion" class="trans-user-input input-box" name="userQuestion"
                                     value=""/>
                    </div>
                </div>

                <div class="control-group fieldcontain">
                    <label class="control-label" for="notes">
                        <g:message code="ridTransaction.notes.label" default="Notes"/>
                    </label>

                    <div class="controls">
                        <g:textField id="notes" class="trans-user-input input-box" name="notes" value=""/>
                    </div>
                </div>
            </fieldset>
            <fieldset class="buttons">
                <input id="resetButton" class="btn btn-danger active-on-change" type="reset" value="Reset"/>
                <g:submitButton name="search" class="btn btn-success"
                                value="${message(code: 'default.button.search.label', default: 'Search')}"/>
            </fieldset>
        </md:form>
    </div>
</g:if>
<g:else>
    <div id="search-ridTransaction" class="content scaffold-search" role="main">
        <!--<h1><g:message code="RidInsTransaction Search"/></h1>-->
            <g:if test="${flash.message}">
                <div class="message" role="status">${flash.message}</div>
            </g:if>

            <md:form class="form-horizontal" action="query" method="GET">
                <fieldset class="form">
                    <div class="row-fluid">
                        <div class="span2 ">
                            <div class="control-group fieldcontain">
                                <label class="control-label" for="dateOfInstruction">
                                    <g:message code="ridTransaction.dateOfInstruction.label"
                                               default="Instruction Date Between"/>
                                </label>

                                <div class="controls">
                                    <input type="text" name="dateOfInstruction_start" class="input-search"
                                           id="start-date"/>
                                    <span class="search-date-middle">&nbsp;&nbsp;and&nbsp;&nbsp;</span>
                                    <input type="text" name="dateOfInstruction_end" class="input-search" id="end-date"/>
                                </div>
                            </div>
                        </div>

                        <div class="span2 offset2">
                            <div class="control-group fieldcontain">
                                <label class="control-label" for="ridLibraryUnit">
                                    <g:message code="ridTransaction.ridLibraryUnit.label" default="Library Unit"/>
                                </label>

                                <div class="controls">
                                    <g:select id="ridLibraryUnitSearch" class="input-search" name="ridLibraryUnitSearch"
                                              noSelection="${['0': 'All Units']}" optionKey="id" multiple="true"
                                              value="0"
                                              from="${metridoc.rid.RidLibraryUnit.list()}"/>
                                </div>
                            </div>
                        </div>

                    </div>

                    <div class="row-fluid">
                        <div class="span2 ">
                            <div class="control-group fieldcontain">
                                <label class="control-label" for="instructorPennkey">
                                    <g:message code="ridTransaction.instructorPennkey.label"
                                               default="Instructor Pennkey"/>
                                </label>

                                <div class="controls">
                                    <g:textField id="instructorPennkey" class="trans-user-input input-search"
                                                 name="instructorPennkey"
                                                 value=""/>
                                </div>
                            </div>

                        </div>

                        <div class="span2 offset2">
                            <div class="control-group fieldcontain">
                                <label class="control-label" for="courseName">
                                    <g:message code="ridTransaction.courseName.label"
                                               default="Course Name"/>
                                </label>

                                <div class="controls">
                                    <g:textField id="courseName" class="trans-user-input input-search"
                                                 name="courseName"
                                                 value=""/>
                                </div>
                            </div>

                        </div>

                    </div>

                    <div class="row-fluid">

                        <div class="span2 ">
                            <div class="control-group fieldcontain">
                                <label class="control-label" for="ridSchool">
                                    <g:message code="ridTransaction.ridSchool.label" default="School"/>
                                </label>

                                <div class="controls">
                                    <g:select id="ridSchoolSearch" class="input-search" name="ridSchoolSearch"
                                              noSelection="${['0': 'All Schools']}" optionKey="id" multiple="true"
                                              value="0"
                                              from="${metridoc.rid.RidSchool.where { inForm == 1 }.list()}"/>
                                </div>
                            </div>
                        </div>

                        <div class="span2 offset2">
                            <div class="control-group fieldcontain">
                                <label class="control-label" for="ridDepartment">
                                    <g:message code="ridTransaction.ridDepartment.label" default="Department"/>
                                </label>

                                <div class="controls">
                                    <g:select id="ridDepartmentSearch" class="input-search" name="ridDepartmentSearch"
                                              noSelection="${['0': 'All Departments']}" multiple="true" value="0"
                                              optionKey="id"
                                    //optionValue="${{ it.name.empty ? 'NOT SPECIFIED' : it.name }}"
                                    //from="${metridoc.rid.RidDepartment.list()}"
                                              from="${metridoc.rid.RidDepartment.where { name != "" }.list()}"/>
                                </div>
                            </div>
                        </div>

                    </div>

                    <div class="row-fluid">
                        <div class="span2 ">
                            <div class="control-group fieldcontain">
                                <label class="control-label" for="ridLocation">
                                    <g:message code="ridTransaction.ridLocation.label" default="Location"/>
                                </label>

                                <div class="controls">
                                    <g:select id="ridLocationSearch" class="input-search" name="ridLocationSearch"
                                              noSelection="${['0': 'All Locations']}" multiple="true" value="0"
                                              optionKey="id"
                                    //optionValue="${{ it.name.empty ? 'NOT SPECIFIED' : it.name }}"
                                    //from="${metridoc.rid.RidLocation.list()}"
                                              from="${metridoc.rid.RidLocation.where { name != "" }.list()}"/>
                                </div>
                            </div>
                        </div>

                    </div>

                    <div class="control-group fieldcontain">
                        <label class="control-label" for="notes">
                            <g:message code="ridTransaction.notes.label" default="Notes"/>
                        </label>

                        <div class="controls">
                            <g:textField id="notes" class="trans-user-input input-box" name="notes" value=""/>
                        </div>
                    </div>

                    <div class="control-group fieldcontain">
                        <label class="control-label" for="sessionDescription">
                            <g:message code="ridTransaction.sessionDescription.label" default="Session Description"/>
                        </label>

                        <div class="controls">
                            <g:textField id="sessionDescription" class="trans-user-input input-box"
                                         name="sessionDescription" value=""/>
                        </div>
                    </div>

                </fieldset>
                <fieldset class="buttons">
                    <input id="resetButton" class="btn btn-danger active-on-change" type="reset" value="Reset"/>
                    <g:submitButton name="search" class="btn btn-success"
                                    value="${message(code: 'default.button.search.label', default: 'Search')}"/>
                </fieldset>
            </md:form>
        </div>


</g:else>
</div>
</md:report>
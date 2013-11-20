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

<%@ page import="metridoc.rid.RidDepartment;metridoc.rid.RidSchool;metridoc.rid.RidLocation;metridoc.rid.RidRank;java.text.SimpleDateFormat;metridoc.rid.RidUserGoal;metridoc.rid.RidLibraryUnit;metridoc.rid.RidLibraryUnit;metridoc.rid.RidDepartment;metridoc.rid.RidCourseSponsor;metridoc.rid.RidConsTransaction;metridoc.rid.RidInsTransaction;metridoc.rid.RidAudience;metridoc.rid.RidInstructionalMaterials;metridoc.rid.RidSessionType" %>


<div class="row-fluid">
    <div class="span2">
        <div class="fieldcontain ${hasErrors(bean: ridTransactionInstance, field: 'ridLibraryUnit', 'error')}">
            <label for="ridLibraryUnit">
                <g:message code="ridTransaction.ridLibraryUnit.label" default="Library Unit"/>
                <span class="required-indicator">*</span>
            </label>
            <g:select id="ridLibraryUnit" style="width:160px" name="ridLibraryUnit.id"
                      from="${metridoc.rid.RidLibraryUnit.list()}"
                      optionKey="id" required="" value="${ridTransactionInstance?.ridLibraryUnit?.id}"
                      class="many-to-one"/>
        </div>
    </div>

    <div class="span3">
        <div class="fieldcontain ${hasErrors(bean: ridTransactionInstance, field: 'dateOfInstruction', 'error')} required">
            <label for="dateOfInstruction" style="width: 150px; margin-left: -37px">
                <g:message code="ridTransaction.dateOfInstruction.label" default="Date Of Instruction"/>
                <span class="required-indicator">*</span>
            </label>
            %{--<g:datePicker style="width: 150px" name="dateOfInstruction" precision="day"  --}%
            %{--value="${ridTransactionInstance?.dateOfInstruction}"  />--}%
            <% def dateString = ridTransactionInstance?.dateOfInstruction ? new SimpleDateFormat("MM/dd/yyyy").format(ridTransactionInstance?.dateOfInstruction) : ""; %>
            <input type="text" name="dateOfInstruction" style="width: 150px"
                   value="${dateString}" id="dp1" required=""/>
        </div>
    </div>

    <div class="span2">
        <div class="fieldcontain ${hasErrors(bean: ridTransactionInstance, field: 'instructorPennkey', 'error')} ">
            <label for="instructorPennkey">
                <g:message code="ridTransaction.instructorPennkey.label" default="Instructor Pennkey"/>
                <span class="required-indicator">*</span>
            </label>
            <g:textField style="width:150px" class="userInput" name="instructorPennkey" maxlength="100"
                         required="" value="${ridTransactionInstance?.instructorPennkey}"/>
        </div>
    </div>

    <div class="span2">
        <div class="fieldcontain ${hasErrors(bean: ridTransactionInstance, field: 'coInstructorPennkey', 'error')} ">
            <label for="coInstructorPennkey">
                <g:message code="ridTransaction.coInstructorPennkey.label" default="Co-Instructor Pennkey"/>

            </label>
            <g:textField style="width:150px" class="userInput" name="coInstructorPennkey" maxlength="100"/>
        </div>
    </div>
</div>

<div class="row-fluid">
    <div class="span2">
        <div class="fieldcontain ${hasErrors(bean: ridTransactionInstance, field: 'location', 'error')} required">
            <label for="location">
                <g:message code="ridTransaction.location.label" default="Location"/>
                <span class="required-indicator">*</span>
            </label>
            <% def locationList = RidLocation.findAllByInForm(1) %>
            <% if (ridTransactionInstance?.location?.inForm == 0)
                locationList.add(0, RidLocation.findById(
                        ridTransactionInstance?.location?.id))
            locationList = locationList.sort { it.name }
            %>
            <% locationList.addAll(RidLocation.findAllByInForm(2)) %>
            <select style="width:120px" id="location" name="location.id" required="" class="many-to-one">
                <g:each in="${locationList}">
                    <option value="${it.id}" inForm="${it.inForm}"
                            <g:if test="${ridTransactionInstance?.location?.id == it.id}">selected=""</g:if>>
                        ${it.name}
                    </option>
                </g:each>
            </select>
        </div>

        <div id="otherLocationDiv" style="display:none;"
             class="fieldcontain ${hasErrors(bean: ridTransactionInstance, field: 'otherLocation', 'error')} ">
            <label for="otherLocation">
                <g:message code="ridTransaction.otherLocation.label" default="Other Location"/>
            </label>
            <g:textField class="userInput" style="width:120px" name="otherLocation" maxlength="50"
                         value="${ridTransactionInstance?.otherLocation}"/>
        </div>
    </div>


    <div class="span2">
        <div class="fieldcontain ${hasErrors(bean: ridTransactionInstance, field: 'prepTime', 'error')} required">
            <label for="prepTime">
                <g:message code="ridTransaction.prepTime.label" default="Prep Time (minutes)"/>
                <span class="required-indicator">*</span>
            </label>
            <g:field class="userInput" style="width:120px"
                     name="prepTime" type="number" value="${ridTransactionInstance.prepTime}" required=""/>
        </div>
    </div>

    <div class="span2">
        <div class="fieldcontain ${hasErrors(bean: ridTransactionInstance, field: 'eventLength', 'error')} required">
            <label for="eventLength">
                <g:message code="ridTransaction.eventLength.label" default="Event Length (minutes)"/>
                <span class="required-indicator">*</span>
            </label>
            <g:field style="width:120px" class="userInput" name="eventLength" type="number"
                     value="${ridTransactionInstance.eventLength}" required=""/>
        </div>
    </div>

    <div class="span2">
        <div class="fieldcontain ${hasErrors(bean: ridTransactionInstance, field: 'attendanceTotal', 'error')} required">
            <label for="attendanceTotal">
                <g:message code="ridTransaction.attendanceTotal.label" default="Total Attendance"/>
                <span class="required-indicator">*</span>
            </label>
            <g:field style="width:120px" class="userInput" name="attendanceTotal" type="number" max="50"
                     value="${ridTransactionInstance.attendanceTotal}" required=""/>
        </div>
    </div>
</div>

<div class="row-fluid">
    <div class="span2">
        <div class="fieldcontain ${hasErrors(bean: ridTransactionInstance, field: 'sequenceName', 'error')} ">
            <label for="sequenceName">
                <g:message code="ridTransaction.sequenceName.label" default="Sequence Name"/>

            </label>
            <g:textField style="width:150px" class="userInput" name="sequenceName" maxlength="100"/>
        </div>
    </div>

    <div class="span2">
        <div class="fieldcontain ${hasErrors(bean: ridTransactionInstance, field: 'sequenceUnit', 'error')}">
            <label for="sequenceUnit">
                <g:message code="ridTransaction.sequenceUnit.label" default="Module Number"/>

            </label>
            <g:field class="userInput" style="width:120px"
                     name="sequenceUnit" type="number" value="${ridTransactionInstance.sequenceUnit}"/>
        </div>
    </div>

    <div class="span2">
        <div class="fieldcontain ${hasErrors(bean: ridTransactionInstance, field: 'courseName', 'error')} ">
            <label for="courseName">
                <g:message code="ridTransaction.courseName.label" default="Course Name"/>
            </label>
            <g:textField class="userInput" name="courseName" style="width: 120px" maxlength="100"
                         value="${ridTransactionInstance?.courseName}"/>
        </div>
    </div>

    <div class="span2">
        <div class="fieldcontain ${hasErrors(bean: ridTransactionInstance, field: 'courseNumber', 'error')} ">
            <label for="courseNumber">
                <g:message code="ridTransaction.courseNumber.label" default="Course Number"/>
            </label>
            <g:textField class="userInput" name="courseNumber" style="width: 120px" maxlength="100"
                         value="${ridTransactionInstance?.courseNumber}"/>
        </div>
    </div>

    <div class="span2">
        <div class="fieldcontain ${hasErrors(bea: ridTransactionInstance, field: 'department', 'error')}">
            <label for="department">
                <g:message code="ridTransaction.department.label" default="Department"/>
                <a style="font-size: 14px" data-toggle="modal"
                   href="../ridAdminDepartment/departmentList" data-target="#myDepartment">
                    <i class="icon-file-alt"></i>
                </a>
            </label>
            <g:select style="width:120px" id="department" name="department.id"
                      from="${metridoc.rid.RidDepartment.list().sort { it.name }}" optionKey="id"
                      value="${ridTransactionInstance?.department?.id}" class="many-to-one"/>
        </div>
    </div>

</div>

<div class="row-fluid">

    <div class="span2">
        <div class="fieldcontain ${hasErrors(bean: ridTransactionInstance, field: 'facultySponsor', 'error')} ">
            <label for="facultySponsor">
                <g:message code="ridTransaction.facultySponsor.label" default="Faculty Sponsor"/>
            </label>
            <g:textField class="userInput" name="facultySponsor" style="width: 120px" maxlength="300"
                         value="${ridTransactionInstance?.facultySponsor}"/>
        </div>
    </div>

    <div class="span2">
        <div class="fieldcontain ${hasErrors(bean: ridTransactionInstance, field: 'school', 'error')} required">
            <label for="school">
                <g:message code="ridTransaction.school.label" default="School"/>
                <span class="required-indicator">*</span>
            </label>
            <% def schoolList = RidSchool.findAllByInForm(1) %>
            <% if (ridTransactionInstance?.school?.inForm == 0)
                schoolList.add(0, RidSchool.findById(
                        ridTransactionInstance?.school?.id))
            schoolList = schoolList.sort { it.name }
            %>
            <% schoolList.addAll(RidSchool.findAllByInForm(2)) %>
            <select style="width:120px" id="school" name="school.id" required="" class="many-to-one">
                <g:each in="${schoolList}">
                    <option value="${it.id}" inForm="${it.inForm}"
                            <g:if test="${ridTransactionInstance?.school?.id == it.id}">selected=""</g:if>>
                        ${it.name}
                    </option>
                </g:each>
            </select>
        </div>

        <div id="otherSchoolDiv" style="display:none;"
             class="fieldcontain ${hasErrors(bean: ridTransactionInstance, field: 'otherSchool', 'error')} ">
            <label for="otherSchool">
                <g:message code="ridTransaction.otherSchool.label" default="Other School"/>
            </label>
            <g:textField class="userInput" style="width:120px" name="otherSchool" maxlength="50"
                         value="${ridTransactionInstance?.otherSchool}"/>
        </div>
    </div>

</div>

<div class="row-fluid">
    <div class="span2">
        <div class="fieldcontain ${hasErrors(bean: ridTransactionInstance, field: 'sessionType', 'error')} required">
            <label for="sessionType">
                <g:message code="ridTransaction.sessionType.label" default="Session Type "/>
                <span class="required-indicator">*</span>
            </label>


            <div id="currentSessionType" style="display: none;">${ridTransactionInstance?.sessionType?.id}</div>
            <%
                typeList = metridoc.rid.RidSessionType.findAllByInFormAndRidLibraryUnit(1,
                        ridTransactionInstance?.ridLibraryUnit ?: RidLibraryUnit.get(1))
                if (ridTransactionInstance?.sessionType?.inForm == 0)
                    typeList.add(0, metridoc.rid.RidSessionType.findById(
                            ridTransactionInstance?.sessionType?.id))
                typeList = typeList.sort { it.name }
                typeList.addAll(metridoc.rid.RidSessionType.findAllByInFormAndRidLibraryUnit(2,
                        ridTransactionInstance?.ridLibraryUnit ?: RidLibraryUnit.get(1)))
            %>
            <select style="width:120px" id="sessionType" name="sessionType.id" required="" class="many-to-one">
                <g:each in="${typeList}">
                    <option value="${it.id}" inForm="${it.inForm}"
                            <g:if test="${ridTransactionInstance?.sessionType?.id == it.id}">selected=""</g:if>>
                        ${it.name}
                    </option>
                </g:each>
            </select>
        </div>

        <div id="otherSessionTypeDiv" style="display:none;"
             class="fieldcontain ${hasErrors(bean: ridTransactionInstance, field: 'otherSessionType', 'error')} ">
            <label for="otherSessionType">
                <g:message code="ridTransaction.otherSessionType.label" default="Other Session Type"/>
            </label>
            <g:textField class="userInput" name="otherSessionType" style="width:120px"
                         maxlength="100" value="${ridTransactionInstance?.otherSessionType}"/>
        </div>
    </div>

    <div class="span2">
        <div class="fieldcontain ${hasErrors(bean: ridTransactionInstance, field: 'instructionalMaterials', 'error')} required">
            <label for="instructionalMaterials">
                <g:message code="ridTransaction.instructionalMaterials.label" default="Instructional Materials"/>

            </label>

            <div id="currentInstructionalMaterials"
                 style="display: none;">${ridTransactionInstance?.instructionalMaterials?.id}</div>
            <%
                instructionalMaterialsList = RidInstructionalMaterials.findAllByInForm(1)
                if (ridTransactionInstance?.instructionalMaterials?.inForm == 0)
                    instructionalMaterialsList.add(0, RidInstructionalMaterials.findById(
                            ridTransactionInstance?.instructionalMaterials?.id))
                instructionalMaterialsList = instructionalMaterialsList.sort { it.name }
                instructionalMaterialsList.addAll(RidInstructionalMaterials.findAllByInForm(2))
            %>
            <select style="width:120px" id="instructionalMaterials" name="instructionalMaterials.id" required=""
                    class="many-to-one">
                <g:each in="${instructionalMaterialsList}">
                    <option value="${it.id}" inForm="${it.inForm}"
                            <g:if test="${ridTransactionInstance?.instructionalMaterials?.id == it.id}">selected=""</g:if>>
                        ${it.name}
                    </option>
                </g:each>
            </select>
        </div>

        <div id="otherInstructionalMaterialsDiv" style="display:none"
             class="fieldcontain ${hasErrors(bean: ridTransactionInstance, field: 'otherInstructionalMaterials', 'error')} ">
            <label for="otherInstructionalMaterials">
                <g:message code="ridTransaction.otherInstructionalMaterials.label"
                           default="Other InstructionalMaterials"/>
            </label>
            <g:textField class="userInput" name="otherInstructionalMaterials" style="width:120px" maxlength="50"
                         value="${ridTransactionInstance?.otherInstructionalMaterials}"/>
        </div>
    </div>

    <div class="span2">
        <div class="fieldcontain ${hasErrors(bean: ridTransactionInstance, field: 'audience', 'error')} required">
            <label for="audience">
                <g:message code="ridTransaction.audience.label" default="Audience"/>

            </label>

            <div id="currentAudience" style="display: none;">${ridTransactionInstance?.audience?.id}</div>
            <%
                audienceList = RidAudience.findAllByInForm(1)
                if (ridTransactionInstance?.audience?.inForm == 0)
                    audienceList.add(0, RidAudience.findById(
                            ridTransactionInstance?.audience?.id))
                audienceList = audienceList.sort { it.name }
                audienceList.addAll(RidAudience.findAllByInForm(2))
            %>
            <select style="width:120px" id="audience" name="audience.id" required="" class="many-to-one">
                <g:each in="${audienceList}">
                    <option value="${it.id}" inForm="${it.inForm}"
                            <g:if test="${ridTransactionInstance?.audience?.id == it.id}">selected=""</g:if>>
                        ${it.name}
                    </option>
                </g:each>
            </select>
        </div>

        <div id="otherAudienceDiv" style="display:none"
             class="fieldcontain ${hasErrors(bean: ridTransactionInstance, field: 'otherAudience', 'error')} ">
            <label for="otherAudience">
                <g:message code="ridTransaction.otherAudience.label" default="Other Audience"/>
            </label>
            <g:textField class="userInput" name="otherAudience" style="width:120px" maxlength="50"
                         value="${ridTransactionInstance?.otherAudience}"/>
        </div>
    </div>

    <div class="span2">
        <div class="fieldcontain ${hasErrors(bean: ridTransactionInstance, field: 'requestor', 'error')} ">
            <label for="requestor">
                <g:message code="ridTransaction.requestor.label" default="Requestor"/>
            </label>
            <g:textField style="width:150px" class="userInput" name="requestor" maxlength="100"
                         value="${ridTransactionInstance?.requestor}"/>
        </div>
    </div>

</div>

<div class="row-fluid">
    <div class="span4">
        <div class="fieldcontain ${hasErrors(bean: ridTransactionInstance, field: 'sessionDescription', 'error')} ">
            <label for="sessionDescription">
                <g:message code="ridTransaction.sessionDescription.label" default="Session Description"/>
            </label>
            <br/>
            <g:textArea class="userInput" name="sessionDescription" cols="40" rows="5" maxlength="500"
                        value="${ridTransactionInstance?.sessionDescription}" onkeydown="isFull(this)"/>
        </div>
    </div>

    <div class="span4">
        <div class="fieldcontain ${hasErrors(bean: ridTransactionInstance, field: 'notes', 'error')} ">
            <label for="notes">
                <g:message code="ridTransaction.notes.label" default="Notes"/>
            </label>
            <br/>
            <g:textArea class="userInput" id="notes" name="notes" cols="40" rows="5" maxlength="500"
                        value="${ridTransactionInstance?.notes}" onkeydown="isFull(this)"/>

        </div>
    </div>
</div>



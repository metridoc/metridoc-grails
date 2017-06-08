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

<%@ page import=" metridoc.rid.RidDepartment;metridoc.rid.RidSchool;metridoc.rid.RidLocation;metridoc.rid.RidRank;java.text.SimpleDateFormat;metridoc.rid.RidUserGoal;metridoc.rid.RidLibraryUnit;metridoc.rid.RidLibraryUnit;metridoc.rid.RidDepartment;metridoc.rid.RidCourseSponsor;metridoc.rid.RidConsTransaction;metridoc.rid.RidInsTransaction;metridoc.rid.RidExpertise;metridoc.rid.RidInstructionalMaterials;metridoc.rid.RidSessionType" %>


<div class="row-fluid">
    <div class="span2">
        <div class="fieldcontain ${hasErrors(bean: ridTransactionInstance, field: 'ridLibraryUnit', 'error')}">
            <label for="ridLibraryUnit">
                <g:message code="ridTransaction.ridLibraryUnit.label" default="Library Unit"/>
                <span class="required-indicator">*</span>
            </label>
            <g:select id="ridLibraryUnit" name="ridLibraryUnit.id"
                      from="${metridoc.rid.RidLibraryUnit.list()}"
                      optionKey="id" required="" value="${ridTransactionInstance?.ridLibraryUnit?.id}"
                      class="many-to-one input-wide"
                      onchange="toggleOther()"/>
        </div>
    </div>

    <div class="span3">
        <div class="fieldcontain ${hasErrors(bean: ridTransactionInstance, field: 'dateOfInstruction', 'error')} required">
            <label for="dateOfInstruction" id="date-of-instruction-label">
                <g:message code="ridTransaction.dateOfInstruction.label" default="Date Of Instruction"/>
                <span class="required-indicator">*</span>
            </label>
            <% def dateString = ridTransactionInstance?.dateOfInstruction ? new SimpleDateFormat("MM/dd/yyyy").format(ridTransactionInstance?.dateOfInstruction) : ""; %>
            <input type="text" name="dateOfInstruction" class="input-wide"
                   value="${dateString}" id="transaction-date" required=""/>
        </div>
    </div>

    <div class="span2">
        <div class="fieldcontain ${hasErrors(bean: ridTransactionInstance, field: 'instructorPennkey', 'error')} ">
            <label for="instructorPennkey">
                <g:message code="ridTransaction.instructorPennkey.label" default="Instructor Pennkey"/>
                <span class="required-indicator">*</span>
            </label>
            <g:textField class="trans-user-input input-wide" name="instructorPennkey" maxlength="100"
                         required="" value="${ridTransactionInstance?.instructorPennkey}"/>
        </div>
    </div>

    <div class="span2">
        <div class="fieldcontain ${hasErrors(bean: ridTransactionInstance, field: 'coInstructorPennkey', 'error')} ">
            <label for="coInstructorPennkey">
                <g:message code="ridTransaction.coInstructorPennkey.label" default="Co-Instructor Pennkey"/>

            </label>
            <g:textField class="trans-user-input input-wide" name="coInstructorPennkey" maxlength="100"/>
        </div>
    </div>
</div>

<div class="row-fluid">
    <div class="span2">
        <div class="fieldcontain ${hasErrors(bean: ridTransactionInstance, field: 'location', 'error')} required">
            <label for="location">
                <g:message code="ridTransaction.location.label" default="Location "/>
                <span class="required-indicator">*</span>
            </label>


            <div id="currentLocation" class="hidden-div">${ridTransactionInstance?.location?.id}</div>
            <%
                locationList = metridoc.rid.RidLocation.findAllByInFormAndRidLibraryUnit(1,
                        ridTransactionInstance?.ridLibraryUnit ?: RidLibraryUnit.get(1))
                if (ridTransactionInstance?.location?.inForm == 0)
                    locationList.add(0, metridoc.rid.RidLocation.findById(
                            ridTransactionInstance?.location?.id))
                locationList = locationList.sort { it.name }
                locationList.addAll(metridoc.rid.RidLocation.findAllByInFormAndRidLibraryUnit(2,
                        ridTransactionInstance?.ridLibraryUnit ?: RidLibraryUnit.get(1)))
            %>
            <select id="location" name="location.id" required="" class="many-to-one input-create">
                <g:each in="${locationList}">
                    <option value="${it.id}" inForm="${it.inForm}"
                            <g:if test="${ridTransactionInstance?.location?.id == it.id}">selected=""</g:if>>
                        ${it.name}
                    </option>
                </g:each>
            </select>
        </div>

        <div id="otherLocationDiv"
             class="fieldcontain ${hasErrors(bean: ridTransactionInstance, field: 'otherLocation', 'error')} hidden-div">
            <label for="otherLocation">
                <g:message code="ridTransaction.otherLocation.label" default="Other Location"/>
            </label>
            <g:textField class="trans-user-input input-create" name="otherLocation"
                         maxlength="100" value="${ridTransactionInstance?.otherLocation}"/>
        </div>
    </div>


    <div class="span2">
        <div class="fieldcontain ${hasErrors(bean: ridTransactionInstance, field: 'prepTime', 'error')} required">
            <label for="prepTime">
                <g:message code="ridTransaction.prepTime.label" default="Prep Time (minutes)"/>
                <span class="required-indicator">*</span>
            </label>
            <g:field class="trans-user-input input-create"
                     name="prepTime" type="number" value="${ridTransactionInstance.prepTime}" required=""/>
        </div>
    </div>

    <div class="span2">
        <div class="fieldcontain ${hasErrors(bean: ridTransactionInstance, field: 'eventLength', 'error')} required">
            <label for="eventLength">
                <g:message code="ridTransaction.eventLength.label" default="Event Length (minutes)"/>
                <span class="required-indicator">*</span>
            </label>
            <g:field class="trans-user-input input-create" name="eventLength" type="number"
                     value="${ridTransactionInstance.eventLength}" required=""/>
        </div>
    </div>

    <div class="span2">
        <div class="fieldcontain ${hasErrors(bean: ridTransactionInstance, field: 'attendanceTotal', 'error')} required">
            <label for="attendanceTotal">
                <g:message code="ridTransaction.attendanceTotal.label" default="Total Attendance"/>
                <span class="required-indicator">*</span>
            </label>
            <g:field class="trans-user-input input-create" name="attendanceTotal" type="number" max="1023"
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
            <g:textField class="trans-user-input input-wide" name="sequenceName" maxlength="100"/>
        </div>
    </div>

    <div class="span2">
        <div class="fieldcontain ${hasErrors(bean: ridTransactionInstance, field: 'sequenceUnit', 'error')}">
            <label for="sequenceUnit">
                <g:message code="ridTransaction.sequenceUnit.label" default="Module Number"/>

            </label>
            <g:field class="trans-user-input input-create"
                     name="sequenceUnit" type="number" value="${ridTransactionInstance.sequenceUnit}"/>
        </div>
    </div>

    <div class="span2">
        <div class="fieldcontain ${hasErrors(bean: ridTransactionInstance, field: 'courseName', 'error')} ">
            <label for="courseName">
                <g:message code="ridTransaction.courseName.label" default="Course Name"/>
            </label>
            <g:textField class="trans-user-input input-create" name="courseName" maxlength="100"
                         value="${ridTransactionInstance?.courseName}"/>
        </div>
    </div>

    <div class="span2">
        <div class="fieldcontain ${hasErrors(bean: ridTransactionInstance, field: 'courseNumber', 'error')} ">
            <label for="courseNumber">
                <g:message code="ridTransaction.courseNumber.label" default="Course Number"/>
            </label>
            <g:textField class="trans-user-input input-create" name="courseNumber" maxlength="100"
                         value="${ridTransactionInstance?.courseNumber}"/>
        </div>
    </div>

    <div class="row-fluid">
        <div class="span2">
            <div class="fieldcontain ${hasErrors(bea: ridTransactionInstance, field: 'department', 'error')}">
                <label for="department">
                    <g:message code="ridTransaction.department.label" default="Department"/>
                    <a class="modal-label" data-toggle="modal"
                       href="#deptModal">
                        <i class="icon-file-alt"></i>
                    </a>
                    <tmpl:departmentModal/>
                </label>
                <g:select id="department" name="department.id"
                          from="${RidDepartment.list().sort { it.name }}" optionKey="id"
                          value="${ridTransactionInstance?.department?.id}" class="many-to-one input-create"/>
            </div>
        </div>
    </div>

</div>

<div class="row-fluid">

    <div class="span2">
        <div class="fieldcontain ${hasErrors(bean: ridTransactionInstance, field: 'facultySponsor', 'error')} ">
            <label for="facultySponsor">
                <g:message code="ridTransaction.facultySponsor.label" default="Faculty Sponsor"/>
            </label>
            <g:textField class="trans-user-input input-create" name="facultySponsor" maxlength="300"
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
            <select id="school" name="school.id" required="" class="many-to-one input-create">
                <g:each in="${schoolList}">
                    <option value="${it.id}" inForm="${it.inForm}"
                            <g:if test="${ridTransactionInstance?.school?.id == it.id}">selected=""</g:if>>
                        ${it.name}
                    </option>
                </g:each>
            </select>
        </div>

        <div id="otherSchoolDiv"
             class="fieldcontain ${hasErrors(bean: ridTransactionInstance, field: 'otherSchool', 'error')} hidden-div">
            <label for="otherSchool">
                <g:message code="ridTransaction.otherSchool.label" default="Other School"/>
            </label>
            <g:textField class="trans-user-input input-create" name="otherSchool" maxlength="50"
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


            <div id="currentSessionType" class="hidden-div">${ridTransactionInstance?.sessionType?.id}</div>
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
            <select id="sessionType" name="sessionType.id" required="" class="many-to-one input-create">
                <g:each in="${typeList}">
                    <option value="${it.id}" inForm="${it.inForm}"
                            <g:if test="${ridTransactionInstance?.sessionType?.id == it.id}">selected=""</g:if>>
                        ${it.name}
                    </option>
                </g:each>
            </select>
        </div>

        <div id="otherSessionTypeDiv"
             class="fieldcontain ${hasErrors(bean: ridTransactionInstance, field: 'otherSessionType', 'error')} hidden-div">
            <label for="otherSessionType">
                <g:message code="ridTransaction.otherSessionType.label" default="Other Session Type"/>
            </label>
            <g:textField class="trans-user-input input-create" name="otherSessionType"
                         maxlength="100" value="${ridTransactionInstance?.otherSessionType}"/>
        </div>
    </div>

    <div class="span2">
        <div class="fieldcontain ${hasErrors(bean: ridTransactionInstance, field: 'instructionalMaterials', 'error')}">
            <label for="instructionalMaterials">
                <g:message code="ridTransaction.instructionalMaterials.label" default="Instructional Materials "/>

            </label>


            <div id="currentInstructionalMaterials" class="hidden-div">${ridTransactionInstance?.instructionalMaterials?.id}</div>
            <%
                materialsList = metridoc.rid.RidInstructionalMaterials.findAllByInFormAndRidLibraryUnit(1,
                        ridTransactionInstance?.ridLibraryUnit ?: RidLibraryUnit.get(1))
                if (ridTransactionInstance?.instructionalMaterials?.inForm == 0)
                    materialsList.add(0, metridoc.rid.RidInstructionalMaterials.findById(
                            ridTransactionInstance?.instructionalMaterials?.id))
                materialsList = materialsList.sort { it.name }
                materialsList.addAll(metridoc.rid.RidInstructionalMaterials.findAllByInFormAndRidLibraryUnit(2,
                        ridTransactionInstance?.ridLibraryUnit ?: RidLibraryUnit.get(1)))
            %>
            <select id="instructionalMaterials" name="instructionalMaterials.id"  class="many-to-one input-create">
                <g:each in="${materialsList}">
                    <option value="${it.id}" inForm="${it.inForm}"
                            <g:if test="${ridTransactionInstance?.instructionalMaterials?.id == it.id}">selected=""</g:if>>
                        ${it.name}
                    </option>
                </g:each>
            </select>
        </div>

        <div id="otherInstructionalMaterialsDiv"
             class="fieldcontain ${hasErrors(bean: ridTransactionInstance, field: 'otherInstructionalMaterials', 'error')} hidden-div">
            <label for="otherInstructionalMaterials">
                <g:message code="ridTransaction.otherInstructionalMaterials.label" default="Other Instructional Materials"/>
            </label>
            <g:textField class="trans-user-input input-create" name="otherInstructionalMaterials"
                         maxlength="100" value="${ridTransactionInstance?.otherInstructionalMaterials}"/>
        </div>
    </div>



    <div class="span2">
        <div class="fieldcontain ${hasErrors(bean: ridTransactionInstance, field: 'requestor', 'error')} ">
            <label for="requestor">
                <g:message code="ridTransaction.requestor.label" default="Requestor"/>
            </label>
            <g:textField class="trans-user-input input-wide" name="requestor" maxlength="100"
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
            <g:textArea class="trans-user-input" name="sessionDescription" cols="40" rows="5" maxlength="500"
                        value="${ridTransactionInstance?.sessionDescription}" onkeydown="isFull(this)"/>
        </div>
    </div>

    <div class="span4">
        <div class="fieldcontain ${hasErrors(bean: ridTransactionInstance, field: 'notes', 'error')} ">
            <label for="notes">
                <g:message code="ridTransaction.notes.label" default="Notes"/>
            </label>
            <br/>
            <g:textArea class="trans-user-input" id="notes" name="notes" cols="40" rows="5" maxlength="500"
                        value="${ridTransactionInstance?.notes}" onkeydown="isFull(this)"/>

        </div>
    </div>
</div>



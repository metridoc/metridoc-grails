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


<md:report>
<div class="md-application-content">
<tmpl:toggle/>
<tmpl:tabs/>
<g:if test="${session.transType == "consultation"}">
    <g:set var="entityName" value="${message(code: 'ridTransaction.label', default: 'RidConsTransaction')}"/>
    <div id="show-ridTransaction" class="content scaffold-show" role="main">
        <h1><g:message code="default.show.label" args="[entityName]"/></h1>
        %{--<g:if test="${flash.message}">--}%
        %{--<div class="message" role="status">${flash.message}</div>--}%
        %{--</g:if>--}%
        <br/>

        <table border="0"
               class="show-table"
               align="center">
            <tr align="center">
                <td colspan="2">
                    <span id="ridLibraryUnit-label" class="property-label"><g:message
                            code="ridTransaction.ridLibraryUnit.label" default="Library Unit"/></span>
                    :
                    <span class="property-value" aria-labelledby="ridLibraryUnit-label"><g:fieldValue
                            bean="${ridTransactionInstance}" field="ridLibraryUnit"/></span>
                </td>
                <td colspan="2">
                    <span id="dateOfConsultation-label" class="property-label"><g:message
                            code="ridTransaction.dateOfConsultation.label" default="Date Of Consultation"/></span>
                    :
                    <span class="property-value" aria-labelledby="dateOfConsultation-label"><g:formatDate
                            format="yyyy-MM-dd" date="${ridTransactionInstance?.dateOfConsultation}"/></span>
                </td>
                <td colspan="2">
                    <span id="staffPennkey-label" class="property-label"><g:message
                            code="ridTransaction.staffPennkey.label" default="Staff Pennkey"/></span>
                    :
                    <span class="property-value" aria-labelledby="staffPennkey-label"><g:fieldValue
                            bean="${ridTransactionInstance}" field="staffPennkey"/></span>
                </td>
            </tr>

            <tr align="center">
                <td>
                    <span id="userName-label" class="property-label"><g:message code="ridTransaction.userName.label"
                                                                                default="User Name"/></span>
                    :<br/>
                    <span class="property-value" aria-labelledby="userName-label"><g:fieldValue
                            bean="${ridTransactionInstance}" field="userName"/></span>
                </td>
                <td>
                    <span id="rank-label" class="property-label"><g:message code="ridTransaction.rank.label"
                                                                            default="Rank"/></span>
                    :<br/>
                    <span class="property-value" aria-labelledby="rank-label"><g:fieldValue
                            bean="${ridTransactionInstance}" field="rank"/></span>
                </td>
                <td>
                    <span id="school-label" class="property-label"><g:message
                            code="ridTransaction.school.label" default="School"/></span>
                    :<br/>
                    <span class="property-value" aria-labelledby="school-label"><g:fieldValue
                            bean="${ridTransactionInstance}" field="school"/></span>
                </td>
                %{--<td>--}%
                %{--<span id="patronEmail-label" class="property-label"><g:message code="ridTransaction.patronEmail.label" default="Patron Email" /></span>--}%
                %{--:<br/>--}%
                %{--<span class="property-value" aria-labelledby="patronEmail-label"><g:fieldValue bean="${ridTransactionInstance}" field="patronEmail"/></span>--}%
                %{--</td>--}%
                <td colspan="2">
                    <span id="interactOccurrences-label" class="property-label"><g:message
                            code="ridTransaction.interactOccurrences.label" default="Interact Occurrences"/></span>
                    :<br/>
                    <span class="property-value" aria-labelledby="interactOccurrences-label"><g:fieldValue
                            bean="${ridTransactionInstance}" field="interactOccurrences"/></span>
                </td>
            </tr>

            <tr align="center">
                <td>
                    <span id="modeOfConsultation-label" class="property-label"><g:message
                            code="ridTransaction.modeOfConsultation.label" default="Consultation Mode"/></span>
                    :<br/>
                    <span class="property-value" aria-labelledby="modeOfConsultation-label"><g:fieldValue
                            bean="${ridTransactionInstance}" field="modeOfConsultation"/></span>
                </td>
                <td>
                    <span id="serviceProvided-label" class="property-label"><g:message
                            code="ridTransaction.serviceProvided.label" default="Service Provided"/></span>
                    :<br/>
                    <span class="property-value" aria-labelledby="serviceProvided-label"><g:fieldValue
                            bean="${ridTransactionInstance}" field="serviceProvided"/></span>
                </td>
                <td>
                    <span id="userGoal-label" class="property-label"><g:message code="ridTransaction.userGoal.label"
                                                                                default="User Goal"/></span>
                    :<br/>
                    <span class="property-value" aria-labelledby="userGoal-label"><g:fieldValue
                            bean="${ridTransactionInstance}" field="userGoal"/></span>
                </td>
                <td>
                    <span id="prepTime-label" class="property-label"><g:message code="ridTransaction.prepTime.label"
                                                                                default="Prep Time (min)"/></span>
                    :<br/>
                    <span class="property-value" aria-labelledby="prepTime-label"><g:fieldValue
                            bean="${ridTransactionInstance}" field="prepTime"/></span>
                </td>
                <td>
                    <span id="eventLength-label" class="property-label"><g:message
                            code="ridTransaction.eventLength.label" default="Event Length(min):"/></span>
                    <br/>
                    <span class="property-value" aria-labelledby="eventLength-label"><g:fieldValue
                            bean="${ridTransactionInstance}" field="eventLength"/></span>
                </td>
            </tr>

            <tr align="center">
                <td>
                    <span id="courseName-label" class="property-label"><g:message
                            code="ridTransaction.courseName.label" default="Course Name"/></span>
                    :<br/>
                    <span class="property-value" aria-labelledby="courseName-label"><g:fieldValue
                            bean="${ridTransactionInstance}" field="courseName"/></span>
                </td>
                <td>
                    <span id="courseSponsor-label" class="property-label"><g:message
                            code="ridTransaction.courseSponsor.label" default="Course Sponsor"/></span>
                    :<br/>
                    <span class="property-value" aria-labelledby="courseSponsor-label"><g:fieldValue
                            bean="${ridTransactionInstance}" field="courseSponsor"/></span>
                </td>
                <td>
                    <span id="department-label" class="property-label"><g:message
                            code="ridTransaction.department.label"
                            default="Department"/></span>
                    :<br/>
                    %{--<span class="property-value" aria-labelledby="department-label"><g:link controller="ridDepartmentalAffiliation" action="show" id="${ridTransactionInstance?.department?.id}">${ridTransactionInstance?.department?.encodeAsHTML()}</g:link></span>--}%
                    <span class="property-value" aria-labelledby="department-label"><g:fieldValue
                            bean="${ridTransactionInstance}" field="department"/></span>
                </td>
                <td>
                    <span id="courseNumber-label" class="property-label"><g:message
                            code="ridTransaction.courseNumber.label" default="Course Number"/></span>
                    :<br/>
                    <span class="property-value" aria-labelledby="courseNumber-label"><g:fieldValue
                            bean="${ridTransactionInstance}" field="courseNumber"/></span>
                </td>

                <td>
                    <span id="facultySponsor-label" class="property-label"><g:message
                            code="ridTransaction.facultySponsor.label" default="Faculty Sponsor"/></span>
                    :<br/>
                    <span class="property-value" aria-labelledby="facultySponsor-label"><g:fieldValue
                            bean="${ridTransactionInstance}" field="facultySponsor"/></span>
                </td>
            </tr>
            <tr align="center">
                <td colspan="2">
                    <span id="userQuestion-label" class="property-label"><g:message
                            code="ridTransaction.userQuestion.label" default="User Question"/></span>
                    :<br/>
                    <span class="property-value" aria-labelledby="userQuestion-label"><g:fieldValue
                            bean="${ridTransactionInstance}" field="userQuestion"/></span>
                </td>
                <td>
                    <span id="notes-label" class="property-label"><g:message code="ridTransaction.notes.label"
                                                                             default="Notes"/></span>
                    :<br/>
                    <span class="property-value" aria-labelledby="notes-label"><g:fieldValue
                            bean="${ridTransactionInstance}" field="notes"/></span>
                </td>
            </tr>

        </table>

        <md:form>
            <fieldset class="buttons">
                <g:hiddenField name="id" value="${ridTransactionInstance?.id}"/>
                <g:actionSubmit class="btn btn-success" action="edit" id="${ridTransactionInstance?.id}"
                                value="${message(code: 'default.button.edit.label', default: 'Edit')}"/>
                <g:actionSubmit class="btn btn-danger" action="delete"
                                value="${message(code: 'default.button.delete.label', default: 'Delete')}"
                                onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');"/>
            </fieldset>
        </md:form>

    </div>
</g:if>
<g:else>
<g:if env="development">
<g:set var="entityName" value="${message(code: 'ridTransaction.label', default: 'RidInsTransaction')}"/>
<div id="show-ridTransaction" class="content scaffold-show" role="main">
<h1><g:message code="default.show.label" args="[entityName]"/></h1>
%{--<g:if test="${flash.message}">--}%
%{--<div class="message" role="status">${flash.message}</div>--}%
%{--</g:if>--}%
<br/>

<table border="0"
       class="show-table:
       align="center">

<tr><td></td></tr>
<tr align="center">
    <td colspan="2">
        <span id="dateOfInstruction-label" class="property-label"><g:message
        code="ridTransaction.dateOfInstruction.label" default="Date Of Instruction"/></span>
        :
<span class="property-value" aria-labelledby="dateOfInstruction-label"><g:formatDate
        format="yyyy-MM-dd" date="${ridTransactionInstance?.dateOfInstruction}"/></span>
</td>
<td colspan="2">
    <span id="instructorPennkey-label" class="property-label"><g:message
            code="ridTransaction.instructorPennkey.label" default="Instructor Pennkey"/></span>
    :
    <span class="property-value" aria-labelledby="instructorPennkey-label"><g:fieldValue
            bean="${ridTransactionInstance}" field="instructorPennkey"/></span>
</td>
<td colspan="2">
    <span id="coInstructorPennkey-label" class="property-label"><g:message
            code="ridTransaction.coInstructorPennkey.label" default="Co-instructor Pennkey"/></span>
    :
    <span class="property-value" aria-labelledby="coInstructorPennkey-label"><g:fieldValue
            bean="${ridTransactionInstance}" field="coInstructorPennkey"/></span>
</td>

</tr>
<tr><td></td></tr>
<tr align="center">
    <td colspan="2">
        <span id="attendanceTotal-label" class="property-label"><g:message
                code="ridTransaction.attendanceTotal.label" default="Total Attendance: "/></span>

        <span class="property-value" aria-labelledby="attendanceTotal-label"><g:fieldValue
                bean="${ridTransactionInstance}" field="attendanceTotal"/></span>
    </td>

    <td colspan="2">
        <span id="prepTime-label" class="property-label"><g:message code="ridTransaction.prepTime.label"
                                                                    default="Prep Time (min): "/></span>

        <span class="property-value" aria-labelledby="prepTime-label"><g:fieldValue
                bean="${ridTransactionInstance}" field="prepTime"/></span>
    </td>
    <td colspan="2">
        <span id="eventLength-label" class="property-label"><g:message
                code="ridTransaction.eventLength.label" default="Event Length (min): "/></span>

        <span class="property-value" aria-labelledby="eventLength-label"><g:fieldValue
                bean="${ridTransactionInstance}" field="eventLength"/></span>
    </td>
</tr>
<tr><td></td></tr>
<tr align="center">
    <td colspan="2">
        <span id="ridLibraryUnit-label" class="property-label"><g:message
                code="ridTransaction.ridLibraryUnit.label" default="Library Unit: "/></span>

        <span class="property-value" aria-labelledby="ridLibraryUnit-label"><g:fieldValue
                bean="${ridTransactionInstance}" field="ridLibraryUnit"/></span>
    </td>

    <td colspan="2">
        <span id="school-label" class="property-label"><g:message
                code="ridTransaction.school.label" default="School: "/></span>

        <span class="property-value" aria-labelledby="school-label"><g:fieldValue
                bean="${ridTransactionInstance}" field="school"/></span>
    </td>
    <td colspan="2">
        <span id="location-label" class="property-label"><g:message
                code="ridTransaction.location.label" default="Location: "/></span>

        <span class="property-value" aria-labelledby="location-label"><g:fieldValue
                bean="${ridTransactionInstance}" field="location"/></span>
    </td>
    %{--<td>--}%
    %{--<span id="patronEmail-label" class="property-label"><g:message code="ridTransaction.patronEmail.label" default="Patron Email" /></span>--}%
    %{--:<br/>--}%
    %{--<span class="property-value" aria-labelledby="patronEmail-label"><g:fieldValue bean="${ridTransactionInstance}" field="patronEmail"/></span>--}%
    %{--</td>--}%

</tr>

<tr><td></td></tr>
<tr align="center">
    <td colspan="2">
        <span id="sequenceName-label" class="property-label"><g:message
                code="ridTransaction.sequenceName.label" default="Sequence: "/></span>

        <span class="property-value" aria-labelledby="sequenceName-label"><g:fieldValue
                bean="${ridTransactionInstance}" field="sequenceName"/></span>
    </td>


    <td colspan="2">
        <span id="sequenceUnit-label" class="property-label"><g:message
                code="ridTransaction.eventLength.label" default="Sequence module: "/></span>

        <span class="property-value" aria-labelledby="sequenceUnit-label"><g:fieldValue
                bean="${ridTransactionInstance}" field="sequenceUnit"/></span>
    </td>

</tr>


<tr><td></td></tr>
<tr align="center">

    <td>
        <span id="department-label" class="property-label"><g:message
                code="ridTransaction.department.label"
                default="Department"/></span>
        :<br/>
        %{--<span class="property-value" aria-labelledby="department-label"><g:link controller="ridDepartmentalAffiliation" action="show" id="${ridTransactionInstance?.department?.id}">${ridTransactionInstance?.department?.encodeAsHTML()}</g:link></span>--}%
        <span class="property-value" aria-labelledby="department-label"><g:fieldValue
                bean="${ridTransactionInstance}" field="department"/></span>
    </td>
    <td>
        <span id="courseName-label" class="property-label"><g:message
                code="ridTransaction.courseName.label" default="Course Name"/></span>
        :<br/>
        <span class="property-value" aria-labelledby="courseName-label"><g:fieldValue
                bean="${ridTransactionInstance}" field="courseName"/></span>
    </td>

    <td>
        <span id="courseNumber-label" class="property-label"><g:message
                code="ridTransaction.courseNumber.label" default="Course Number"/></span>
        :<br/>
        <span class="property-value" aria-labelledby="courseNumber-label"><g:fieldValue
                bean="${ridTransactionInstance}" field="courseNumber"/></span>
    </td>

    <td>
        <span id="facultySponsor-label" class="property-label"><g:message
                code="ridTransaction.facultySponsor.label" default="Faculty Sponsor"/></span>
        :<br/>
        <span class="property-value" aria-labelledby="facultySponsor-label"><g:fieldValue
                bean="${ridTransactionInstance}" field="facultySponsor"/></span>
    </td>
</tr>
<tr><td></td></tr>
<tr align="center">

    <td>
        <span id="sessionType-label" class="property-label"><g:message
                code="ridTransaction.sessionType.label"
                default="Session Type"/></span>
        :<br/>
        %{--<span class="property-value" aria-labelledby="sessionType-label"><g:link controller="ridDepartmentalAffiliation" action="show" id="${ridTransactionInstance?.sessionType?.id}">${ridTransactionInstance?.sessionType?.encodeAsHTML()}</g:link></span>--}%
        <span class="property-value" aria-labelledby="sessionType-label"><g:fieldValue
                bean="${ridTransactionInstance}" field="sessionType"/></span>
    </td>
    <td>
        <span id="instructionalMaterials-label" class="property-label"><g:message
                code="ridTransaction.instructionalMaterials.label" default="Instructional Materials"/></span>
        :<br/>
        <span class="property-value" aria-labelledby="instructionalMaterials-label"><g:fieldValue
                bean="${ridTransactionInstance}" field="instructionalMaterials"/></span>
    </td>

    <td>
        <span id="expertise-label" class="property-label"><g:message
                code="ridTransaction.expertise.label" default="Expertise"/></span>
        :<br/>
        <span class="property-value" aria-labelledby="expertise-label"><g:fieldValue
                bean="${ridTransactionInstance}" field="expertise"/></span>
    </td>

    <td>
        <span id="requestor-label" class="property-label"><g:message
                code="ridTransaction.requestor.label" default="Requestor"/></span>
        :<br/>
        <span class="property-value" aria-labelledby="requestor-label"><g:fieldValue
                bean="${ridTransactionInstance}" field="requestor"/></span>
    </td>
</tr>
<tr><td></td></tr>
<tr align="center">
    <td colspan="3">
        <span id="sessionDescription-label" class="property-label"><g:message
                code="ridTransaction.sessionDescription.label"
                default="Description"/></span>
        :<br/>
        <span class="property-value" aria-labelledby="sessionDescription-label"><g:fieldValue
                bean="${ridTransactionInstance}" field="sessionDescription"/></span>
    </td>
    <td colspan="3">
        <span id="notes-label" class="property-label"><g:message code="ridTransaction.notes.label"
                                                                 default="Notes"/></span>
        :<br/>
        <span class="property-value" aria-labelledby="notes-label"><g:fieldValue
                bean="${ridTransactionInstance}" field="notes"/></span>
    </td>
</tr>
</table>

<md:form>
    <fieldset class="buttons">
        <g:hiddenField name="id" value="${ridTransactionInstance?.id}"/>
        <g:actionSubmit class="btn btn-success" action="edit" id="${ridTransactionInstance?.id}"
                        value="${message(code: 'default.button.edit.label', default: 'Edit')}"/>
        <g:actionSubmit class="btn btn-danger" action="delete"
                        value="${message(code: 'default.button.delete.label', default: 'Delete')}"
                        onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');"/>
    </fieldset>
</md:form>

</div>
</g:if>
<g:else>
    Not yet Implemented
</g:else>
</g:else>
</div>
</md:report>
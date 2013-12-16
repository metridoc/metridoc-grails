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
<r:require module="tableModule"/>
<r:require module="statistics"/>
<!--[if !IE]><!-->
<r:external dir="css" file="floating_tables_for_admin_5.css" plugin="metridoc-rid"/>
<!--<![endif]-->

<!--IMPORTANT NAMING CONVENTIONS
 Tables = "Table#"
 divs around tables = "dTable#"
 monthly breakdown activation = toggleCol#
 fields with top 5 lists = showField_#
 fields without top 5 lists = allField_#
 table toggling checkboxes = toggle_Field_#
 I should probably improve this at some point-->


<div class="md-application-content">

<tmpl:toggle/>
<tmpl:tabs/>
<g:if test="${session.transType == "consultation"}">

<g:if test="${statResults.totalTransactions == 0}">
    <p>No transactions have been reported</p>
</g:if>
<g:else>

<div id="stats-ridTransaction" class="content scaffold-list" role="main">

</div>

<h1>
    Transaction Overview

</h1>

<div class="stats-overview">
<input type="button" id="toggleCol1" value="Expand to monthly view"> </input>
<table class="table table-striped table-hover" id="Table1">
<thead>
<tr>

    <th>Parameter</th>
    <th>Average</th>
    <th>Total</th>
    <th>Past Year</th>
<g:each in="${statResults.months}">
    <th>${it.getDateString().replaceAll('/1/', '/').padLeft(5, '0')}</th>
</g:each>

</tr>
</thead>
<tbody>
<tr>
    <td>Transactions</td>
    <td>N/A</td>
    <td>${statResults.totalTransactions}</td>
    <td>${statResults.yearTransactions}</td>
    <g:each in="${statResults.monthTransactions}">
        <th>${it}</th>
    </g:each>
</tr>
<tr>
    <td>Interact Occurrences</td>
    <td>${statResults.avgInteractOccurrences}</td>
    <td>${statResults.totalInteractOccurences}</td>
    <td>${statResults.yearInteractOccurences}</td>
    <g:each in="${statResults.monthInteractOccurences}">
        <th>${it}</th>
    </g:each>
</tr>
<tr>
    <td>Prep Time (min)</td>
    <td>${statResults.avgPrepTime}</td>
    <td>${statResults.totalPrepTime}</td>
    <td>${statResults.yearPrepTime}</td>
    <g:each in="${statResults.monthPrepTime}">
        <th>${it}</th>
    </g:each>
</tr>
<tr>
    <td>Event Length (min)</td>
    <td>${statResults.avgEventLength}</td>
    <td>${statResults.totalEventLength}</td>
    <td>${statResults.yearEventLength}</td>
    <g:each in="${statResults.monthEventLength}">
        <th>${it}</th>
    </g:each>
</tr>
</tbody>

</table>

    </div>

<h1>Additional statistics by field</h1>
<!--id numbers are references to the tables they correspond to-->
<label class="checkbox inline">
    <input type="checkbox" id="toggleAll">All fields</label>
<label class="checkbox inline">
    <input type="checkbox" id="toggle_Dept_2">Departments</label>
<label class="checkbox inline">
    <input type="checkbox" id="toggle_Course_3">Courses</label>
<label class="checkbox inline">
    <input type="checkbox" id="toggle_Rank_4">Ranks</label>
<label class="checkbox inline">
    <input type="checkbox" id="toggle_Goal_5">User Goals</label>
<label class="checkbox inline">
    <input type="checkbox" id="toggle_Mode_6">Modes of Consultation</label>
<label class="checkbox inline">
    <input type="checkbox" id="toggle_School_7">Schools</label>
<label class="checkbox inline">
    <input type="checkbox" id="toggle_Sponsor_8">Course Sponsors</label>
<label class="checkbox inline">
    <input type="checkbox" id="toggle_Service_9">Services Provided</label>
<label class="checkbox inline">
    <input type="checkbox" id="toggle_Library_10">Library Units</label>


<div id="dTable2">
<h1 id="aDept">Department Data</h1>
<input type="button" id="showDepartments_2" value="Show all Departments"> </input>
<input type="button" id="toggleCol2" value="Expand to monthly view"> </input>

<div class="stats-table>
    <table class="table table-striped table-hover" id="Table2">
        <thead>
        <tr>

            <th>Parameter</th>
            <th>Total</th>
            <th>Past Year</th>
<g:each in="${statResults.months}">
    <th>${it.getDateString().replaceAll('/1/', '/').padLeft(5, '0')}</th>
</g:each>

</tr>
</thead>
<tbody>

<g:each var="dept" in="${0..<statResults.topFiveDepartments.size()}">
    <tr>
        <td>${statResults.topFiveDepartments[dept].toString()}</td>
        <td>${statResults.topFiveTotalDepartments[dept]}</td>
        <td>${statResults.topFiveYearDepartments[dept]}</td>
        <g:each var="mon" in="${0..<13}">
            <td>${statResults.topFiveMonthDepartments[dept][mon]}</td>
        </g:each>
    </tr>

</g:each>

<g:each var="dept" in="${0..<statResults.departments.size()}">
    <tr>
        <td>${statResults.departments[dept].toString()}</td>
        <td>${statResults.totalDepartments[dept]}</td>
        <td>${statResults.yearDepartments[dept]}</td>
        <g:each var="mon" in="${0..<13}">
            <td>${statResults.monthDepartments[dept][mon]}</td>
        </g:each>
    </tr>

</g:each>

</tbody>
</table>

</div>
</div>
<div id="dTable3">
<h1 id="aCourse">Course Data</h1>
<g:if test="${statResults.courses.size() > 5}">
    <input type="button" id="showCourses_3" value="Show all Courses"> </input>
</g:if>
<input type="button" id="toggleCol3" value="Expand to monthly view"> </input>

<div style='overflow:auto; font-size:10pt; height:${70 + statResults.courses.size * 35}px'>
    <table class="table table-striped table-hover" id="Table3">
        <thead>
        <tr>

            <th>Parameter</th>
            <th>Total</th>
            <th>Past Year</th>
            <g:each in="${statResults.months}">
                <th>${it.getDateString().replaceAll('/1/', '/').padLeft(5, '0')}</th>
            </g:each>

        </tr>
        </thead>
        <tbody>

        <g:each var="c" in="${0..<statResults.topFiveCourses.size()}">
            <tr>
                <td>${statResults.topFiveCourses[c].toString()}</td>
                <td>${statResults.topFiveTotalCourses[c]}</td>
                <td>${statResults.topFiveYearCourses[c]}</td>
                <g:each var="mon" in="${0..<13}">
                    <td>${statResults.topFiveMonthCourses[c][mon]}</td>
                </g:each>
            </tr>

        </g:each>
        <g:if test="${statResults.courses.size() > 5}">
            <g:each var="c" in="${0..<statResults.courses.size()}">
                <tr>
                    <td>${statResults.courses[c].toString()}</td>
                    <td>${statResults.totalCourses[c]}</td>
                    <td>${statResults.yearCourses[c]}</td>
                    <g:each var="mon" in="${0..<13}">
                        <td>${statResults.monthCourses[c][mon]}</td>
                    </g:each>
                </tr>

            </g:each>
        </g:if>

        </tbody>
    </table>

</div>
</div>
    <div id="dTable4">
<h1 id="aRank">Rank Data</h1>

<input type="button" id="toggleCol4" value="Expand to monthly view"> </input>
<input type="button" id="allRanks_4" value="Show only Ranks with transactions"> </input>

<div class="stats-table>
    <table class="table table-striped table-hover" id="Table4">
        <thead>
        <tr>

            <th>Parameter</th>
            <th>Total</th>
            <th>Past Year</th>
<g:each in="${statResults.months}">
    <th>${it.getDateString().replaceAll('/1/', '/').padLeft(5, '0')}</th>
</g:each>

</tr>
</thead>
<tbody>

<g:if test="${statResults.ranks.size() > 5}">
    <g:each var="c" in="${0..<statResults.ranks.size()}">
        <tr>
            <td>${statResults.ranks[c].toString()}</td>
            <td>${statResults.totalRanks[c]}</td>
            <td>${statResults.yearRanks[c]}</td>
            <g:each var="mon" in="${0..<13}">
                <td>${statResults.monthRanks[c][mon]}</td>
            </g:each>
        </tr>

    </g:each>
</g:if>

</tbody>
</table>

</div>
</div>
<div id="dTable5">
<br>
<h1 id="aUserGoal">User Goal Data</h1>

<input type="button" id="toggleCol5" value="Expand to monthly view"> </input>
<input type="button" id="allGoals_5" value="Show only User Goals with transactions"> </input>

<div class="stats-table>
<table class="table table-striped table-hover" id="Table5">
    <thead>
    <tr>

        <th>Parameter</th>
        <th>Total</th>
        <th>Past Year</th>
<g:each in="${statResults.months}">
    <th>${it.getDateString().replaceAll('/1/', '/').padLeft(5, '0')}</th>
</g:each>

</tr>
</thead>
<tbody>

<g:if test="${statResults.userGoals.size() > 5}">
    <g:each var="c" in="${0..<statResults.userGoals.size()}">
        <tr>
            <td>${statResults.userGoals[c].toString()}</td>
            <td>${statResults.totalUserGoals[c]}</td>
            <td>${statResults.yearUserGoals[c]}</td>
            <g:each var="mon" in="${0..<13}">
                <td>${statResults.monthUserGoals[c][mon]}</td>
            </g:each>
        </tr>

    </g:each>
</g:if>

</tbody>
</table>

</div>
</div>
<div id="dTable6">
<br>
<h1 id="aMode">Mode of Consultation Data</h1>

<input type="button" id="toggleCol6" value="Expand to monthly view"> </input>
<input type="button" id="allModes_6" value="Show only Modes with transactions"> </input>

<div class="stats-table>
<table class="table table-striped table-hover" id="Table6">
    <thead>
    <tr>

        <th>Parameter</th>
        <th>Total</th>
        <th>Past Year</th>
<g:each in="${statResults.months}">
    <th>${it.getDateString().replaceAll('/1/', '/').padLeft(5, '0')}</th>
</g:each>

</tr>
</thead>
<tbody>

<g:if test="${statResults.modeOfConsultation.size() > 5}">
    <g:each var="c" in="${0..<statResults.modeOfConsultation.size()}">
        <tr>
            <td>${statResults.modeOfConsultation[c].toString()}</td>
            <td>${statResults.totalModeOfConsultation[c]}</td>
            <td>${statResults.yearModeOfConsultation[c]}</td>
            <g:each var="mon" in="${0..<13}">
                <td>${statResults.monthModeOfConsultation[c][mon]}</td>
            </g:each>
        </tr>

    </g:each>
</g:if>

</tbody>
</table>

</div>
</div>
<div id="dTable7">
<br>
<h1 id="aSchool">School Data</h1>

<input type="button" id="toggleCol7" value="Expand to monthly view"> </input>
<input type="button" id="allSchools_7" value="Show only Schools with transactions"> </input>

<div class="stats-table>
<table class="table table-striped table-hover" id="Table7">
    <thead>
    <tr>

        <th>Parameter</th>
        <th>Total</th>
        <th>Past Year</th>
<g:each in="${statResults.months}">
    <th>${it.getDateString().replaceAll('/1/', '/').padLeft(5, '0')}</th>
</g:each>

</tr>
</thead>
<tbody>

<g:if test="${statResults.schools.size() > 5}">
    <g:each var="c" in="${0..<statResults.schools.size()}">
        <tr>
            <td>${statResults.schools[c].toString()}</td>
            <td>${statResults.totalSchools[c]}</td>
            <td>${statResults.yearSchools[c]}</td>
            <g:each var="mon" in="${0..<13}">
                <td>${statResults.monthSchools[c][mon]}</td>
            </g:each>
        </tr>

    </g:each>
</g:if>

</tbody>
</table>

</div>
</div>
<div id="dTable8">
<br>
<h1 id="aSponsor">Course Sponsor Data</h1>

<input type="button" id="toggleCol8" value="Expand to monthly view"> </input>
<input type="button" id="allSponsors_8" value="Show only Course Sponsors with transactions"> </input>

<div class="stats-table>
<table class="table table-striped table-hover" id="Table8">
    <thead>
    <tr>

        <th>Parameter</th>
        <th>Total</th>
        <th>Past Year</th>
<g:each in="${statResults.months}">
    <th>${it.getDateString().replaceAll('/1/', '/').padLeft(5, '0')}</th>
</g:each>

</tr>
</thead>
<tbody>

<g:if test="${statResults.courseSponsors.size() > 5}">
    <g:each var="c" in="${0..<statResults.courseSponsors.size()}">
        <tr>
            <td>${statResults.courseSponsors[c].toString()}</td>
            <td>${statResults.totalCourseSponsors[c]}</td>
            <td>${statResults.yearCourseSponsors[c]}</td>
            <g:each var="mon" in="${0..<13}">
                <td>${statResults.monthCourseSponsors[c][mon]}</td>
            </g:each>
        </tr>

    </g:each>
</g:if>

</tbody>
</table>

</div>
</div>
<div id="dTable9">
<br>
<h1 id="aService">Service Provided Data</h1>

<input type="button" id="toggleCol9" value="Expand to monthly view"> </input>
<input type="button" id="allServices_9" value="Show only Services with transactions"> </input>

<div class="stats-table>
<table class="table table-striped table-hover" id="Table9">
    <thead>
    <tr>

        <th>Parameter</th>
        <th>Total</th>
        <th>Past Year</th>
<g:each in="${statResults.months}">
    <th>${it.getDateString().replaceAll('/1/', '/').padLeft(5, '0')}</th>
</g:each>

</tr>
</thead>
<tbody>

<g:if test="${statResults.serviceProvided.size() > 5}">
    <g:each var="c" in="${0..<statResults.serviceProvided.size()}">
        <tr>
            <td>${statResults.serviceProvided[c].toString()}</td>
            <td>${statResults.totalServiceProvided[c]}</td>
            <td>${statResults.yearServiceProvided[c]}</td>
            <g:each var="mon" in="${0..<13}">
                <td>${statResults.monthServiceProvided[c][mon]}</td>
            </g:each>
        </tr>

    </g:each>
</g:if>

</tbody>
</table>

</div>
</div>
<div id="dTable10">

<br>
<h1 id="aLibrary">Library Unit Data</h1>

<input type="button" id="toggleCol10" value="Expand to monthly view"> </input>
<input type="button" id="allLibraries_10" value="Show only Libraries with transactions"> </input>

<div class="stats-table>
<table class="table table-striped table-hover" id="Table10">
    <thead>
    <tr>

        <th>Parameter</th>
        <th>Total</th>
        <th>Past Year</th>
<g:each in="${statResults.months}">
    <th>${it.getDateString().replaceAll('/1/', '/').padLeft(5, '0')}</th>
</g:each>

</tr>
</thead>
<tbody>

<g:if test="${statResults.libraryUnits.size() > 5}">
    <g:each var="c" in="${0..<statResults.libraryUnits.size()}">
        <tr>
            <td>${statResults.libraryUnits[c].toString()}</td>
            <td>${statResults.totalLibraryUnits[c]}</td>
            <td>${statResults.yearLibraryUnits[c]}</td>
            <g:each var="mon" in="${0..<13}">
                <td>${statResults.monthLibraryUnits[c][mon]}</td>
            </g:each>
        </tr>

    </g:each>
</g:if>

</tbody>
</table>

</div>
</div>
</g:else>
</g:if>
<g:else>Not Yet Implemented</g:else>
</md:report>


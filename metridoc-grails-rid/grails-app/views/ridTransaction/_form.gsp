<%@ page import="metridoc.rid.RidDepartment;metridoc.rid.RidSchool;metridoc.rid.RidLocation;metridoc.rid.RidRank;java.text.SimpleDateFormat;metridoc.rid.RidUserGoal;metridoc.rid.RidLibraryUnit;metridoc.rid.RidLibraryUnit;metridoc.rid.RidDepartment;metridoc.rid.RidCourseSponsor;metridoc.rid.RidConsTransaction;metridoc.rid.RidInsTransaction" %>

<r:require module="datePicker"/>

<g:if test="${session.transType == "consultation"}">
    <tmpl:formCons/>
</g:if>
<g:else>
    <g:if env="development">
        <tmpl:formIns/>
    </g:if>
    <g:else>
        Not implemented
    </g:else>
</g:else>
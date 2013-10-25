<g:if test="${session.transType == "consultation"}">
    <%@ page import="metridoc.rid.RidConsTransaction" %>
    <g:set var="entityName" value="${message(code: 'ridTransaction.label', default: 'RidConsTransaction')}"/>
    <div id="list-ridTransaction" class="content scaffold-list" role="main">
        <r:require module="tableModule"/>
        <!--[if !IE]><!-->
        %{--<r:external dir="css" file="floating_tables_for_admin_2.css" />--}%
        <!--<![endif]-->

        <h1 style="padding-bottom: 10px">Choose a template from the list</h1>
        <g:if test="${flash.message}">
            <div class="message" role="status">${flash.message}</div>
        </g:if>
        <table class="table table-striped table-hover">
            <thead>
            <tr>
                <g:sortableColumn property="ridLibraryUnit"
                                  title="${message(code: 'ridTransaction.ridLibraryUnit.label', default: 'Library')}"/>
                <g:sortableColumn property="school"
                                  title="${message(code: 'ridTransaction.school.label', default: 'School')}"/>
                <g:sortableColumn property="department"
                                  title="${message(code: 'ridTransaction.department.label', default: 'Department')}"/>
                <g:sortableColumn property="courseName"
                                  title="${message(code: 'ridTransaction.courseName.label', default: 'Course')}"/>
                <g:sortableColumn property="modeOfConsultation"
                                  title="${message(code: 'ridTransaction.modeOfConsultation.label', default: 'Mode')}"/>
                <g:sortableColumn property="serviceProvided"
                                  title="${message(code: 'ridTransaction.serviceProvided.label', default: 'Service')}"/>
            </tr>
            </thead>
            <tbody>
            <g:each in="${ridTransactionInstanceList}" status="i" var="ridTransactionInstance">
                <tr onclick="window.location = 'create?tmp=${ridTransactionInstance.id}'" style="cursor: pointer;">
                    <td>${fieldValue(bean: ridTransactionInstance, field: "ridLibraryUnit")}</td>
                    <td>${fieldValue(bean: ridTransactionInstance, field: "school")}</td>
                    <td>${fieldValue(bean: ridTransactionInstance, field: "department")}</td>
                    <td>${fieldValue(bean: ridTransactionInstance, field: "courseName")}</td>
                    <td>${fieldValue(bean: ridTransactionInstance, field: "modeOfConsultation")}</td>
                    <td>${fieldValue(bean: ridTransactionInstance, field: "serviceProvided")}</td>
                </tr>
            </g:each>
            </tbody>
        </table>
    </div>

</g:if>
<g:else>
    <%@ page import="metridoc.rid.RidInsTransaction" %>
    <g:set var="entityName" value="${message(code: 'ridTransaction.label', default: 'RidInsTransaction')}"/>

    <div id="list-ridTransaction" class="content scaffold-list" role="main">
        <r:require module="tableModule"/>
        <!--[if !IE]><!-->
        %{--<r:external dir="css" file="floating_tables_for_admin_2.css" />--}%
        <!--<![endif]-->

        <h1 style="padding-bottom: 10px">Choose a template from the list</h1>
        <g:if test="${flash.message}">
            <div class="message" role="status">${flash.message}</div>
        </g:if>
        <table class="table table-striped table-hover">
            <thead>
            <tr>
                <g:sortableColumn property="ridLibraryUnit"
                                  title="${message(code: 'ridTransaction.ridLibraryUnit.label', default: 'Library')}"/>
                <g:sortableColumn property="location"
                                  title="${message(code: 'ridTransaction.location.label', default: 'Location')}"/>
                <g:sortableColumn property="school"
                                  title="${message(code: 'ridTransaction.school.label', default: 'School')}"/>
                <g:sortableColumn property="department"
                                  title="${message(code: 'ridTransaction.department.label', default: 'Department')}"/>
                <g:sortableColumn property="courseName"
                                  title="${message(code: 'ridTransaction.courseName.label', default: 'Course')}"/>
                <g:sortableColumn property="sessionType"
                                  title="${message(code: 'ridTransaction.sessionType.label', default: 'Type')}"/>

            </tr>
            </thead>
            <tbody>
            <g:each in="${ridTransactionInstanceList}" status="i" var="ridTransactionInstance">
                <tr onclick="window.location = 'create?tmp=${ridTransactionInstance.id}'" style="cursor: pointer;">
                    <td>${fieldValue(bean: ridTransactionInstance, field: "ridLibraryUnit")}</td>
                    <td>${fieldValue(bean: ridTransactionInstance, field: "location")}</td>
                    <td>${fieldValue(bean: ridTransactionInstance, field: "school")}</td>
                    <td>${fieldValue(bean: ridTransactionInstance, field: "department")}</td>
                    <td>${fieldValue(bean: ridTransactionInstance, field: "courseName")}</td>
                    <td>${fieldValue(bean: ridTransactionInstance, field: "sessionType")}</td>

                </tr>
            </g:each>
            </tbody>
        </table>
    </div>

</g:else>
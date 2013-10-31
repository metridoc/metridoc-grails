<%@ page import="metridoc.rid.RidDepartment" %>
<g:set var="entityName"
       value="${message(code: 'ridDepartment.label', default: 'RidDepartment')}"/>

<r:external dir="js" file="RidTransaction.js" plugin="metridoc-rid"/>
<r:require module="tableModule"/>
<!--[if !IE]><!-->
%{--<r:external dir="css" file="floating_tables_for_admin_6.css" plugin="metridoc-rid"/>--}%
<!--<![endif]-->

<div id="list-ridDepartment" class="content scaffold-list">
    <table class="table table-striped table-hover">
        <thead>
        <tr>
            <th>${message(code: 'ridDepartment.name.label', default: 'Name')}</th>
            <th>${message(code: 'ridDepartment.fullName.label', default: 'Full Name')}</th>
        </tr>
        </thead>
        <tbody>
        <g:each in="${ridDepartmentInstanceList}" var="ridDepartmentInstance">
            <tr style="cursor: pointer;"
                onclick="setDepartment(${fieldValue(bean: ridDepartmentInstance, field: "id")})">
                <td>${fieldValue(bean: ridDepartmentInstance, field: "name")}</td>
                <td>${fieldValue(bean: ridDepartmentInstance, field: "fullName")}</td>
            </tr>
        </g:each>
        </tbody>
    </table>
</div>


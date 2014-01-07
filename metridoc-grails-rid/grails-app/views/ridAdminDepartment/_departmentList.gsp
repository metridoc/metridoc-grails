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


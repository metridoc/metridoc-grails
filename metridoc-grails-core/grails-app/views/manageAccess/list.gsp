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
  - 	permissions and limitations under the License.  --}%

<%--
  Created by IntelliJ IDEA.
  User: intern
  Date: 8/14/13
  Time: 1:03 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page import="metridoc.core.ShiroRole" %>
<%@ page import="metridoc.core.ShiroUser" %>
<%@ page import="org.apache.shiro.SecurityUtils" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<md:report>
    <g:hiddenField name="previousExpanded" id="previousExpanded" value="${previousExpanded}"/>
    <g:render template="/commonTemplates/manageMetridocTabs"/>

    <div class="manage-div">
        <a href="#" onclick="collapseOthers('userList')">
            <h1 class="manage-header">Manage Users&nbsp<i id="cUserList"
                                                                        class="icon-plus-sign"></i></h1>

        </a>
        <span id="sUserList" class="manage-subheader">Create, view, and edit user info</span>

        <div id="userList" class="collapse">
            <g:render template="/user/userList"/>
        </div>
    </div>
    <br>

    <div class="manage-div">
        <a href="#" onclick="collapseOthers('roleList')">
            <h1 class="manage-header">Manage Roles&nbsp<i id="cRoleList"
                                                                        class="icon-plus-sign"></i></h1>

        </a>
        <span id="sRoleList" class="manage-subheader">Add roles</span>

        <div id="roleList" class="collapse">
            <g:render template="/role/manageRole"/>
        </div>
    </div>
    <br>

    <div class="manage-div">
        <a href="#" onclick="collapseOthers('manageReportIndex')">
            <h1 class="manage-header">Manage Controllers&nbsp<i id="cManageReportIndex"
                                                                              class="icon-plus-sign"></i></h1>

        </a>
        <span id="sManageReportIndex" class="manage-subheader">View and manage controller permissions</span>

        <div id="manageReportIndex" class="collapse">
            <g:render template="/manageReport/manageReportIndex"/>
        </div>
    </div>
    <br>
</md:report>
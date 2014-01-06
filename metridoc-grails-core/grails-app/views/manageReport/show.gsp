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
  User: tbarker
  Date: 2/1/13
  Time: 2:13 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<md:report>
    <tmpl:manageReportHeaders/>
    <md:header>Edit Controller Security</md:header>
    <g:form class="form-horizontal monitored-form" action="update">
        <g:hiddenField name="controllerName" value="${controllerDetails.controllerName}"/>
        <div class="control-group">
            <label for="controllerForSecurity" class="control-label">Controller Name:</label>

            <div class="controls">
                <g:textField name="controllerForSecurity" disabled="true" value="${controllerDetails.controllerName}"/>
            </div>
            <label for="isProtected" class="control-label">Protected?</label>

            <div class="controls">
                <g:checkBox name="isProtected" value="${controllerDetails.isProtected}"/>
            </div>
            <g:render template="/commonTemplates/roles" model="[selectedRoles: controllerDetails.roles]"/>
            <div class="controls">
                <button class="btn active-on-change" type="submit">
                    <i class="icon-edit"></i> Update
                </button>
            </div>
        </div>
    </g:form>
</md:report>
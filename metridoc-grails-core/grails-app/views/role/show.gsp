<!--

  - Copyright 2013 Trustees of the University of Pennsylvania. Licensed under the
    Educational Community License, Version 2.0 (the "License"); you may
    not use this file except in compliance with the License. You may
    obtain a copy of the License at

    http://www.osedu.org/licenses/ECL-2.0

    Unless required by applicable law or agreed to in writing,
    software distributed under the License is distributed on an "AS IS"
    BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
    or implied. See the License for the specific language governing
    permissions and limitations under the License.

-->

<%@ page import="metridoc.core.ShiroRole" %>

<md:report>

    <g:render template="/commonTemplates/tabs"/>

    <g:form class="form-horizontal">
        <div class="control-group">
            <label for="shiro-role" class="control-label">Role</label>

            <div class="controls">
                <input name="shiro-role" id="shiro-role" type="text" disabled="disabled"
                       value="${shiroRoleInstance?.name}"/>
                </div>
        </div>
    </g:form>
</md:report>

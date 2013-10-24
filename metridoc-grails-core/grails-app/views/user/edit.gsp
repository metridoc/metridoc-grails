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
<%@ page import="metridoc.core.ShiroUser" %>
<md:report>

    <g:render template="/commonTemplates/tabs"/>

    <g:form method="post" class="form-horizontal">
        <g:hiddenField name="id" value="${shiroUserInstance?.id}"/>
        <g:hiddenField name="version" value="${shiroUserInstance?.version}"/>

        <div class="control-group">
            <tmpl:userName disabled="${true}"/>
            %{--<g:render template="/user/userName"  model="[disabled: true]"></g:render>--}%
            <tmpl:email/>
            <g:render template="/commonTemplates/roles" model="${[target: shiroUserInstance]}"/>
            <div class="controls">
                <button class="btn" type="submit" name="_action_update">
                    <i class="icon-edit"></i> Update
                </button>
            </div>
        </div>
    </g:form>
</md:report>
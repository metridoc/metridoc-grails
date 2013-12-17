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

    <g:render template="/commonTemplates/manageMetridocTabs"/>

    <md:form class="form-horizontal"
             onsubmit="if(this.submited == '_action_delete') return window.confirm('Are you sure you want to delete the user ${shiroUserInstance?.username}?'); return true;">
        <g:hiddenField name="id" value="${shiroUserInstance?.id}"/>

        <g:render template="/user/userName" model="[disabled: true]"/>
        <g:render template="/user/email" model="[disabled: true]"/>
        <g:render template="/commonTemplates/roles" model="[disabled: true, target: shiroUserInstance]"/>
        <g:render template="/commonTemplates/button"
                  model="[type: 'submit', action: '_action_edit', icon: 'icon-edit', content: 'Edit']"/>
        <g:if test="${shiroUserInstance != null && shiroUserInstance.username != 'admin'}">
            <g:render template="/commonTemplates/button"
                      model="[type: 'submit',
                              action: '_action_delete',
                              icon: 'icon-trash',
                              content: 'Delete',
                              buttonClass: 'btn-danger',
                              onClick: 'return confirm(\'Are you sure you want to delete this user?\');']"/>
        </g:if>
    </md:form>
</md:report>

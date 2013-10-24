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
<%@ page import="metridoc.core.LdapRoleMapping" %>
<md:report>

    <g:render template="/commonTemplates/tabs"/>

    <g:form method="post" class="form-horizontal">
        <g:hiddenField name="id" value="${ldapRoleMappingInstance?.id}"/>
        <g:hiddenField name="version" value="${ldapRoleMappingInstance?.version}"/>

        <div class="control-group">
            <g:render template="/commonTemplates/nameLabel"
                      model="${[disabled: true, target: ldapRoleMappingInstance, required: true, category: 'Group Name']}"/>
            <g:render template="/commonTemplates/roles" model="${[target: ldapRoleMappingInstance]}"/>
            <g:render template="/commonTemplates/button"
                      model="${[content: 'Update', icon: 'icon-edit', action: '_action_update']}"/>
        </div>
    </g:form>
</md:report>
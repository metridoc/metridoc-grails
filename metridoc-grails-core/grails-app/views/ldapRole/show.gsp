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

    <g:form class="form-horizontal"
            onsubmit="if(this.submitted == '_action_delete') return window.confirm('Are you sure you want to delete the group ${ldapRoleMappingInstance?.name}?'); return true;">
        <g:hiddenField name="id" value="${ldapRoleMappingInstance?.id}"/>
        <div class="control-group">
            <g:render template="/commonTemplates/nameLabel"
                      model="${[disabled: true, target: ldapRoleMappingInstance, category: 'Group Name']}"/>
            <g:render template="/commonTemplates/roles" model="[disabled: true, target: ldapRoleMappingInstance]"/>
            <div class="controls">
                <g:render template="/commonTemplates/button"
                          model="[type: 'submit', action: '_action_edit', icon: 'icon-edit', content: 'Edit']"/>
                <g:if test="${ldapRoleMappingInstance}">
                    <button class="btn btn-danger" type="submit" name="_action_delete"
                            onclick="deleteMapping(${ldapRoleMappingInstance.id})" >
                        <i class="icon-trash"></i> Delete
                    </button>
                    <g:form name="mdForm_${ldapRoleMappingInstance.id}" method="delete" action="delete"
                            id="${ldapRoleMappingInstance.id}"/>
                </g:if>
            </div>
        </div>
    </g:form>
</md:report>

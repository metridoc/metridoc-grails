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
<div class="lMargin25">
    <h1>Create New Group
        <a href="#" onclick="showGroupForm()">
            <i id="createGroup" class="icon-plus-sign"></i>
        </a>
    </h1>

    <div id="createGroupForm" hidden="true">
        <g:form action="save" class="form-horizontal">
            <div class="control-group">
                <g:render template="/commonTemplates/nameLabel"
                          model="${[disabled: false, target: ldapRoleMappingInstance, required: true, category: 'Group Name']}"/>
                <g:render template="/commonTemplates/roles" model="${[target: ldapRoleMappingInstance]}"/>
                <g:render template="/commonTemplates/button" model="${[content: 'Create', icon: 'icon-edit']}"/>
            </div>
        </g:form>
    </div>

    <div id="list-ldapRoleMapping" class="content scaffold-list" role="main">
        <table class="table table-striped table-hover">
            <thead>
            <tr>
                <g:sortableColumn property="name"
                                  title="Groups"/>
                <th>Roles</th>
                <th></th>
            </tr>

            </thead>

            <tbody>
            <g:each in="${ldapRoleMappingInstanceList}" status="i" var="ldapRoleMappingInstance">
                <tr>

                    <td>
                        <g:link action="show"
                                id="${ldapRoleMappingInstance.id}">${fieldValue(bean: ldapRoleMappingInstance, field: "name")}
                        </g:link>

                    </td>
                    <td>
                        <g:each in="${ldapRoleMappingInstance.roles}" var="role" status="count">

                            <g:if test="${count < 10}">
                                <span>
                                    <g:if test="${count < 9 && count < ldapRoleMappingInstance.roles.size() - 1}">
                                        ${role.name.minus("ROLE_").toLowerCase().capitalize()},&nbsp;
                                    </g:if>
                                    <g:else>
                                        ${role.name.minus("ROLE_").toLowerCase().capitalize()}
                                    </g:else>
                                </span>
                            </g:if>
                            <g:if test="${count == 10}">
                                <a href="#" name="popRoles" class="popRoles" rel="popover" data-trigger="hover"
                                   data-content="${ldapRoleMappingInstance.roles}">...</a>
                            </g:if>

                        </g:each>

                    </td>
                    <td>
                        <span class="inCellActions">

                            <a href="edit/${ldapRoleMappingInstance.id}">
                                <i class="icon-edit"></i>
                            </a>
                            <a class="delete" href="#" onclick="deleteMapping(${ldapRoleMappingInstance.id})">
                                <i class="icon-trash"></i>
                            </a>

                            <g:form name="mdForm_${ldapRoleMappingInstance.id}" method="delete" action="delete"
                                    id="${ldapRoleMappingInstance.id}"/>

                        </span>

                    </td>

                </tr>
            </g:each>
            </tbody>
        </table>

        <g:if test="${showPagination}">
            <div class="pagination">
                <g:paginate total="${ldapRoleMappingInstanceTotal}"/>
            </div>
        </g:if>

    </div>
</div>
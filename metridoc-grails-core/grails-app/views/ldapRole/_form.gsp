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
<%@ page import="metridoc.core.ShiroRole; metridoc.core.LdapRoleMapping" %>

<div class="fieldcontain ${hasErrors(bean: ldapRoleMappingInstance, field: 'name', 'error')} required">
    <g:render template="/commonTemplates/nameLabel"
              model="${[disabled: false, target: ldapRoleMappingInstance, required: true, category: 'Group Name']}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: ldapRoleMappingInstance, field: 'roles', 'error')} ">
    <g:render template="/commonTemplates/roles" model="${[disabled: false, target: ldapRoleMappingInstance]}"/>
</div>


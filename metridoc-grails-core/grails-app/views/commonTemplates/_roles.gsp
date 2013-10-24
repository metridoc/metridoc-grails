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

<%@ page import="metridoc.core.ShiroRole" %>
<div class="control-group">
    <label for="roles" class="control-label">
        Roles:
    </label>
    <g:if test="${target}">
        <g:set var="selectedRoles" value="${target.roles?.collect { it.name }}"/>
    </g:if>
    <div class="controls">
        <g:if test="${disabled}">
            <select name="roles" multiple="multiple" size="5" disabled="${disabled ?: false}">
                <g:each in="${ShiroRole.list()}" var="shiroRole">
                    <g:if test="${selectedRoles?.contains(shiroRole.name)}">
                        <option value="${shiroRole.name}" selected="selected">${shiroRole.name}</option>
                    </g:if>
                    <g:else>
                        <option value="${shiroRole.name}">${shiroRole.name}</option>
                    </g:else>
                </g:each>
            </select>
        </g:if>
        <g:else>
            <select name="roles" multiple="multiple" size="5">
                <g:each in="${ShiroRole.list()}" var="shiroRole">
                    <g:if test="${selectedRoles?.contains(shiroRole.name)}">
                        <option value="${shiroRole.name}" selected="selected">${shiroRole.name}</option>
                    </g:if>
                    <g:else>
                        <option value="${shiroRole.name}">${shiroRole.name}</option>
                    </g:else>
                </g:each>
            </select>
        </g:else>
    </div>
</div>
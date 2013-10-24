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
<%@ page import="metridoc.core.ShiroRole; metridoc.core.ShiroUser" %>

<div class="fieldcontain ${hasErrors(bean: shiroUserInstance, field: 'username', 'error')} required">
    <label for="username">
        <g:message code="shiroUser.username.label" default="User Name"/>
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="username" required="" value="${shiroUserInstance?.username}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: shiroUserInstance, field: 'password', 'error')} ">
    <label for="password">
        <g:message code="shiroUser.password.label" default="Password"/>
        <span class="required-indicator">*</span>
    </label>
    <g:passwordField name="password"/>
</div>

<div class="fieldcontain ${hasErrors(bean: shiroUserInstance, field: 'password', 'error')} ">
    <label for="password">
        <g:message code="shiroUser.confirmPassword.label" default="Confirm Password"/>
        <span class="required-indicator">*</span>
    </label>
    <g:passwordField name="confirm"/>
</div>

<div class="fieldcontain ${hasErrors(bean: shiroUserInstance, field: 'emailAddress', 'error')} ">
    <label for="emailAddress">
        <g:message code="shiroUser.emailAddress.label" default="Email Address"/>
    </label>
    <g:textField name="emailAddress" required="" value="${shiroUserInstance?.emailAddress}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: shiroUserInstance, field: 'roles', 'error')} ">
    <label for="roles">
        <g:message code="shiroUser.roles.label" default="Roles"/>
    </label>
    <select name="roles" multiple="multiple" size="5">
        <g:each in="${ShiroRole.list()}" var="shiroRole">
            <g:if test="${shiroUserInstance?.roles?.contains(shiroRole)}">
                <option value="${shiroRole.name}" selected="selected">${shiroRole.name}</option>
            </g:if>
            <g:else>
                <option value="${shiroRole.name}">${shiroRole.name}</option>
            </g:else>
        </g:each>
    </select>
</div>


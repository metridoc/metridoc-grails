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
  - 	permissions and limitations under the License.
  --}%

<g:if test="${shiroUserInstance.username != 'anonymous'}">

    <md:form action="update" class="form-horizontal">
        <g:hiddenField name="id" value="${shiroUserInstance?.id}"/>
        <g:hiddenField name="version" value="${shiroUserInstance?.version}"/>
        <g:render template="/user/userName" model="[disabled: true]"/>
        <g:render template="/user/email"/>
        <g:if test="${managingAccount}">
            <g:render template="/commonTemplates/roles" model="[disabled: true, target: shiroUserInstance]"/>
        </g:if>
        <g:else>
            <g:render template="/commonTemplates/roles" model="[target: shiroUserInstance]"/>
        </g:else>
        <div class="control-group">
            <label class="control-label" for="changePW">Change Password?</label>

            <div class="controls" id="ifChangePassword">
                <%--suppress CheckTagEmptyBody --%>
                <g:checkBox class="ays-ignore" name="changePW" id="changePW" value="${false}"></g:checkBox>
            </div>
        </div>
        <g:if test="${managingAccount}">
            <g:render template="/user/password"
                      model="[noValidation: true, passwordId: 'oldPassword', label: 'oldPassword', passwordPrepend: 'Old ']"/>
        </g:if>
        <g:render template="/user/passwords" model="[noValidation: true]"/>
        <g:render template="/commonTemplates/button"
                  model="[content: 'Update',
                          icon: 'icon-edit',
                          buttonClass: 'active-on-change']"/>

    </md:form>
</g:if>
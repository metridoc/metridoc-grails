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

<md:report>
    <div id="template">
        <md:form method="post" action="doResetPassword">
            <div>
                <label for="password">
                    <g:message code="shiroUser.newPassword.label" default="New Password"/>
                    <span class="required-indicator">*</span>
                </label>
                <g:passwordField name="password" required=""/>
            </div>

            <div>
                <label for="password">
                    <g:message code="shiroUser.confirmPassword.label" default="Confirm Password"/>
                    <span class="required-indicator">*</span>
                </label>
                <g:passwordField name="confirm" required=""/>
            </div>

            <g:hiddenField name="resetPasswordId" value="${resetPasswordId}"/>

            <span class="buttons">
                <input type="submit" value="Update" id="upPasswordButton" name="submit"/>
            </span>
        </md:form>
    </div>
</md:report>
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

<div class = "control-group">
    <label for="emailAddress" class="control-label">
        User Email <span class="required-indicator">*</span></label>

    <div class="controls">
        <g:if test="${disabled}">
            <input name="emailAddress"
                   id="emailAddress"
                   required=""
                   type="email"
                   pattern=".{7,}"
                   title="valid email is required, must be more than 7 characters"
                   disabled="${disabled}"
                   value="${shiroUserInstance?.emailAddress}"/>
        </g:if>
        <g:else>
            <input name="emailAddress"
                   id="emailAddress"
                   required=""
                   type="email"
                   pattern=".{7,}"
                   title="valid email is required, must be more than 7 characters"
                   value="${shiroUserInstance?.emailAddress}"/>
        </g:else>

    </div>
</div>
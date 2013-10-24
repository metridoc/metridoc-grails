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

<div class="control-group">
    <label for="${label}" class="control-label password">${passwordPrepend}Password <span
            class="required-indicator">*</span></label>
    <g:if test="${noValidation}">
        <div class="controls password">
            <g:passwordField id="${passwordId}" name="${label}" placeholder="Password"/></div>
    </g:if>
    <g:else>
        <div class="controls password">
            <g:passwordField id="${passwordId}" name="${label}" placeholder="Password" pattern=".{5,}"
                             title="Password must be at least 5 characters long" required=""/>
        </div>
    </g:else>
</div>
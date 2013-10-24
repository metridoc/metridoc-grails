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
    <label for="username" class="control-label">User Name</label>
    <g:set var="disableUserName" value="${disabled ? true : false}"></g:set>
    <div class="controls">
        <g:textField class="username" name="username" pattern=".{5,}" maxlength="250"
                     title="Username must be at least 5 characters in length" required=""
                     value="${shiroUserInstance?.username}" disabled="${disableUserName}" placeholder="User Name"/>
    </div>
</div>
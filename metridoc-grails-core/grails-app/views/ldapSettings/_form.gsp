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

<div class="row-fluid">
    <div class="span3">
        <label>Server</label>
    </div>
    <div class="span3">
        <g:textField  class="userInput" name="server" maxlength="750" value="${LDAP.server}"/>
    </div>
</div>

<div class="row-fluid">
    <div class="span3">
        <label>Root DN</label>
    </div>
    <div class="span3">
        <g:textField  class="userInput" name="rootDN" maxlength="750" value="${LDAP.rootDN}"/>
    </div>
</div>

<div class="row-fluid">
    <div class="span3">
        <label>User Search Base</label>
    </div>
    <div class="span3">
        <g:textField  class="userInput" name="userSearchBase" maxlength="750"
                      value="${LDAP.userSearchBase}"/>
    </div>
</div>

<div class="row-fluid">
    <div class="span3">
        <label>User Search Filter</label>
    </div>
    <div class="span3">
        <g:textField  class="userInput" name="userSearchFilter" maxlength="750"
                      value="${LDAP.userSearchFilter}"/>
    </div>
</div>

<div class="row-fluid">
    <div class="span3">
        <label>Group Search Base</label>
    </div>
    <div class="span3">
        <g:textField  class="userInput" name="groupSearchBase" maxlength="750"
                      value="${LDAP.groupSearchBase}"/>
    </div>
</div>

<div class="row-fluid">
    <div class="span3">
        <label>Group Search Filter</label>
    </div>
    <div class="span3">
        <g:textField  class="userInput" name="groupSearchFilter" maxlength="750"
                      value="${LDAP.groupSearchFilter}"/>
    </div>
</div>



<div class="row-fluid">
    <div class="span3">
        <label>Manager DN</label>
    </div>
    <div class="span3">
        <g:textField  class="userInput" name="managerDN" maxlength="750" value="${LDAP.managerDN}"/>
    </div>
</div>

<div class="row-fluid">
    <div class="span3">
        <label>Manager Password</label>
    </div>
    <div class="span3">
        <g:passwordField name="unencryptedPassword" id="${LDAP.getUnencryptedPassword()}"
                         value="${LDAP.getUnencryptedPassword()}"
                         placeholder="Password"/>
    </div>
</div>

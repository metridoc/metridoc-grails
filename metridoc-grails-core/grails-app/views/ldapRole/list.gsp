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

<%@ page import="metridoc.core.LdapRoleMapping" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<md:report>
    <g:hiddenField name="previousExpanded" id="previousExpanded" value="${previousExpanded}"/>
    <g:render template="/commonTemplates/tabs"/>

    <div class="border-bot">
        <a href="#" onclick="changeIcon('groupList')">

            <h1 class="ldap-header" data-toggle="collapse" data-target="#groupList">LDAP Groups&nbsp
                <i id="cGroupList"  class="icon-minus-sign"></i>
            </h1>
        </a>

        <span id="sGroupList" class="ldap-span">Create and view LDAP groups</span>

        <div id="groupList" class="collapse in">
            <g:render template="/ldapRole/ldapGroupList"/>
        </div>
    </div>
    <br>

    <div class="border-bot">
        <a href="#" onclick="changeIcon('ldapConfig')">

            <h1 class="ldap-header" data-toggle="collapse" data-target="#ldapConfig">LDAP Config&nbsp
                <i id="cLdapConfig"  class="icon-plus-sign"></i>
            </h1>
        </a>

        <span id="sLdapConfig" class="ldap-span">Change LDAP configuration</span>

        <div id="ldapConfig" class="collapse">
            <g:render template="/ldapSettings/ldapConfig"/>
        </div>
    </div>
    <br>

</md:report>

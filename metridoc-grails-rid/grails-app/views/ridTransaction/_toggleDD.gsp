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

<div class="btn-group pull-right">

    <g:if test="${session.transType == "consultation"}">
        <a class="btn" data-toggle="dropdown">Consultation
            <span class="caret"></span></a>
    </g:if>
    <g:else>
        <a class="btn" data-toggle="dropdown">Instructional
            <span class="caret"></span></a>
    </g:else>


    <ul class="dropdown-menu">

        <g:if test="${session.transType == "consultation"}">
            <g:render
                    template="toggleLabelDD"
                    plugin="metridocRid"
                    model="[controllerName: controllerName,
                            actionName: actionName,
                            linkController: 'ridTransaction',
                            linkAction: 'instructional',
                            linkText: 'Instructional']"/>
        </g:if>
        <g:else>
            <g:render
                    template="toggleLabelDD"
                    plugin="metridocRid"
                    model="[controllerName: controllerName,
                            actionName: actionName,
                            linkController: 'ridTransaction',
                            linkAction: 'consultation',
                            linkText: 'Consultation']"/>

        </g:else>
        <li class="divider"></li>
        <shiro:hasRole name="ROLE_ADMIN">
            <g:render
                    template="adminToggleLabel"
                    plugin="metridocRid"
                    model="[controllerName: controllerName,
                            actionName: actionName,
                            linkController: 'ridTransaction',
                            linkAction: 'switchMode',
                            linkText: 'Admin Controls']"/>

            <g:render
                    template="adminToggleLabel"
                    plugin="metridocRid"
                    model="[controllerName: controllerName,
                            actionName: actionName,
                            linkController: 'ridAdminTransaction',
                            linkAction: 'switchMode',
                            linkText: 'Transactions']"/>

        </shiro:hasRole>

    </ul>

</div>
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

<r:external dir="css" file="ridtrans_admin.css" plugin="metridoc-rid"/>

<ul class="nav nav-tabs green">

    <li>
    <li class="dropdown">
        <a class="dropdown-toggle green" data-toggle="dropdown">
            <g:if test="${controllerName != "ridAdminTransaction"}">
                ${controllerName.minus("ridAdmin")}
            </g:if>
            <g:else>
                Edit/Add properties
            </g:else>
            <span class="caret"></span></a>

        <ul class="dropdown-menu">

            <g:render
                    template="/ridTransaction/tabLabel"
                    plugin="metridocRid"
                    model="[controllerName: controllerName,
                            actionName: actionName,
                            linkController: 'ridAdminLibraryUnit',
                            linkAction: 'list',
                            linkText: 'Library Unit',
                    ]"/>

            <g:render
                    template="/ridTransaction/tabLabel"
                    plugin="metridocRid"
                    model="[controllerName: controllerName,
                            actionName: actionName,
                            linkController: 'ridAdminDepartment',
                            linkAction: 'list',
                            linkText: 'Department',
                    ]"/>
            <g:render
                    template="/ridTransaction/tabLabel"
                    plugin="metridocRid"
                    model="[controllerName: controllerName,
                            actionName: actionName,
                            linkController: 'ridAdminSchool',
                            linkAction: 'list',
                            linkText: 'School',
                    ]"/>
            <g:if test="${session.transType == "consultation"}">
                <g:render template="/ridAdminTransaction/consTabs" plugin="metridoc-rid"/>
            </g:if>
            <g:else>
                <g:render template="/ridAdminTransaction/insTabs" plugin="metridoc-rid"/>
            </g:else>
    </li>

</ul>

</ul>

    </ul>


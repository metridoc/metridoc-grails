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


<br>

<ul class="nav nav-tabs">
<g:render
        template="/ridTransaction/tabLabel"
        plugin="metridocRid"
        model="[controllerName: controllerName,
                actionName: actionName,
                linkController: 'ridTransaction',
                linkAction: 'create',
                linkText: 'Create Transaction',
                icon: 'icon-plus-sign-alt']"/>
<g:render
        template="/ridTransaction/tabLabel"
        plugin="metridocRid"
        model="[controllerName: controllerName,
                actionName: actionName,
                linkController: 'ridTransaction',
                linkAction: 'search',
                linkText: 'Search Transaction',
                icon: 'icon-search']"/>
<g:render
        template="/ridTransaction/tabLabel"
        plugin="metridocRid"
        model="[controllerName: controllerName,
                actionName: actionName,
                linkController: 'ridTransaction',
                linkAction: 'spreadsheetUpload',
                linkText: 'Upload Spreadsheet',
                icon: 'icon-cloud-upload']"/>

<li>

<g:if env="development">
    <g:if test="${actionName == "stats" || actionName == "statSearch" || actionName == "statGraph" || actionName == "statQuery"}">
        <li class="dropdown active">
    </g:if>
    <g:else>
        <li class="dropdown green">
    </g:else>
    <a class="dropdown-toggle green" data-toggle="dropdown">
        <i class="icon-signal"></i>
        Statistics

        <span class="caret"></span></a>

    <ul class="dropdown-menu">

    <g:render
            template="/ridTransaction/tabLabel"
            plugin="metridocRid"
            model="[controllerName: controllerName,
                    actionName: actionName,
                    linkController: 'ridTransaction',
                    linkAction: 'stats',
                    linkText: 'Statistics Overview',
                    icon: 'icon-reorder']"/>

    <g:render
            template="/ridTransaction/tabLabel"
            plugin="metridocRid"
            model="[controllerName: controllerName,
                    actionName: actionName,
                    linkController: 'ridTransaction',
                    linkAction: 'statSearch',
                    linkText: 'Filtered Statistics',
                    icon: 'icon-search']"/>

    <g:render
            template="/ridTransaction/tabLabel"
            plugin="metridocRid"
            model="[controllerName: controllerName,
                    actionName: actionName,
                    linkController: 'ridTransaction',
                    linkAction: 'statGraph',
                    linkText: 'Graphed Statistics',
                    icon: 'icon-bar-chart']"/>
    </li>
    </ul>
</g:if>
</ul>
</ul>



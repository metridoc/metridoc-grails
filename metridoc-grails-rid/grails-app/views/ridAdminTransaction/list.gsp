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

<meta name="layout" content="main">
<g:set var="entityName" value="ridAdminTransaction"/>

<md:report>
    <r:external dir="css" file="ridtrans_admin.css" plugin="metridoc-rid"/>
    <div class="md-application-content">
        <div id="list-ridAdminTransaction" class="content scaffold-list" role="main">
            <h1><g:message code="default.list.label" args="[entityName]"/></h1>
            <g:if test="${flash.message}">
                <div class="message" role="status">${flash.message}</div>
            </g:if>
            <hr width="96%" align="center"/>

            <div style="font-size: 14px">
                <g:render template="toggle" plugin="metridoc-rid"/>
                <g:render template="tabs" plugin="metridoc-rid"/>
            </div>


            %{--<div id="controllerList" style="padding-left: 150px;">--}%
            %{--<ul>--}%
            %{--<g:each var="c" in="${grailsApplication.controllerClasses}">--}%
            %{--<g:if test="${c.shortName.contains('Rid') && !c.shortName.contains('RidConsTransaction')}">--}%
            %{--<li class="controller" style="padding-top: 10px;">--}%
            %{--<g:link controller="${c.logicalPropertyName}">--}%
            %{--${c.shortName}--}%
            %{--</g:link>--}%
            %{--</li>--}%
            %{--</g:if>--}%
            %{--</g:each>--}%
            %{--</ul>--}%
            %{--</div>--}%
        </div>
    </div>
</md:report>
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

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <g:if test="${params.layout}">
        <meta name="layout" content="${layout}"/>
    </g:if>
    <g:else>
        <meta name="layout" content="main"/>
    </g:else>

    <g:if test="${hasModule}">
        <r:require module="${module}"/>
    </g:if>
    <g:else>
        <r:require module="${controllerName}" strict="false"/>
    </g:else>
</head>

<body>
<div class="md-application-content">
    <g:if test="${descriptionExists}">
        <div class="description">
            <g:if test="${pluginName}">
                <g:render template="${descriptionTemplate}" plugin="${pluginName}"/>
            </g:if>
            <g:else>
                <g:render template="${descriptionTemplate}"/>
            </g:else>
        </div>
    </g:if>
    ${body}
</div>
</body>
</html>

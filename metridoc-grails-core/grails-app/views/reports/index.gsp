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


<md:report>
    <body>
    <div class="md-application-content">

        %{--TODO: is there some way we can generalize this repeated code, this is a mess--}%
        <g:if test="${params.action == 'index'}">
            <g:if test="${pluginName}">
                <g:render template="${templateDir}/${params.controller}" plugin="${pluginName}"/>
            </g:if>
            <g:else>
                <g:render template="${templateDir}/${params.controller}"/>
            </g:else>
        </g:if>
        <g:elseif test="${params.action}">
            <g:if test="${pluginName}">
                <g:render template="${templateDir}/${params.action}" plugin="${pluginName}"/>
            </g:if>
            <g:else>
                <g:render template="${templateDir}/${params.action}"/>
            </g:else>
        </g:elseif>
        <g:else>
            <g:if test="${pluginName}">
                <g:render template="${templateDir}/${params.controller}" plugin="${pluginName}"/>
            </g:if>
            <g:else>
                <g:render template="${templateDir}/${params.controller}"/>
            </g:else>
        </g:else>
    </div>
    </body>
</md:report>
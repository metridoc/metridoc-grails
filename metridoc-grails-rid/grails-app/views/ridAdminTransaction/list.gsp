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
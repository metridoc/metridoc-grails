<g:if test="${linkController == controllerName && linkAction == actionName}">
    <li class="active">
        <g:link controller="${linkController}" action="${linkAction}"><i class="${icon}"></i> ${linkText}</g:link>
    </li>
</g:if>


<g:else>
    <li>
        <g:link controller="${linkController}" action="${linkAction}"><i class="${icon}"></i> ${linkText}</g:link>
    </li>
</g:else>
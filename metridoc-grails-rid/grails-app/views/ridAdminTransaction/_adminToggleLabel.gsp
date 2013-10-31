<g:if test="${linkText != "Admin Controls"}">
    <li>
        <g:link controller="${linkController}" rel="tooltip" title="Switch to Transactions"
                action="${linkAction}"><i class="${icon}"></i> ${linkText}</g:link>
    </li>
</g:if>
<g:else>
    <li class="active">
        <g:link controller="${linkController}"
                action="${linkAction}"><i class="${icon}"></i> ${linkText}</g:link>
    </li>
</g:else>



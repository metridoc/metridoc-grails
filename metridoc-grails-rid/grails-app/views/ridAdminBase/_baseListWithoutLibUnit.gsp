<tbody>
<g:each in="${ridInstanceList}" status="i" var="ridInstance">
    <tr>

        <td>
            <a data-toggle="modal"
               href="edit/${ridInstance.id}?dummy=${org.apache.commons.lang.math.RandomUtils.nextInt()}"
               data-target="#myModal">
                ${fieldValue(bean: ridInstance, field: "name")}
            </a>
        </td>

        <% def choices = ['NO', 'YES, and no indication needed', 'YES, and indication required'] %>
        <td>${choices.get(ridInstance?.inForm)}</td>

        <g:if test="${session.transType == "consultation"}">
            <td>${ridInstance?.ridConsTransaction?.size()}</td>
        </g:if>
        <g:else>
            <td>${ridInstance?.ridInsTransaction?.size()}</td>
        </g:else>
    </tr>
</g:each>
</tbody>
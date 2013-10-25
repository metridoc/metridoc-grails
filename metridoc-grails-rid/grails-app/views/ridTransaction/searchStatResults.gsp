<%@ page import="org.apache.shiro.SecurityUtils; metridoc.rid.RidConsTransaction; metridoc.rid.RidInsTransaction" %>

<g:if test="${session.transType == "consultation"}">
    <g:set var="entityName" value="${message(code: 'ridTransaction.label', default: 'RidConsTransaction')}"/>
</g:if>
<g:else>
    <g:set var="entityName" value="${message(code: 'ridTransaction.label', default: 'RidInsTransaction')}"/>
</g:else>
<md:report>
    <r:require module="tableModule"/>
    <!--[if !IE]><!-->
    <r:external dir="css" file="floating_tables_for_admin_5.css" plugin="metridoc-rid"/>
    <!--<![endif]-->


    <div class="md-application-content">

    <tmpl:toggle/>
    <tmpl:tabs/>
    <g:if test="${session.transType == "consultation"}">

        <g:if test="${statResults.transactionSum == 0}">
            <p>No transactions match your search criteria</p>
        </g:if>
        <g:else>
            <g:each var="input" in="${0..<statResults.inputs.size()}">
                <g:each var="field" in="${0..<statResults.inputs[input].size()}">
                    <p>${statResults.inputs[input][field]}: ${statResults.inputNames[input][field]}</p>

                </g:each>

                <p>The total number of transactions is ${statResults.totalTransactions[input]}</p>

                <p>The average number of interact occurrences is ${statResults.avgInteractOccurrences[input]}</p>

                <p>The average prep time is ${statResults.avgPrepTime[input]}</p>

                <p>The average event length is ${statResults.avgEventLength[input]}</p>
                <br>
                <br>

            </g:each>

        </g:else>
    </g:if>
    <g:else>Not Yet Implemented</g:else>
</md:report>


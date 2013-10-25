<%@ page import="org.apache.shiro.SecurityUtils; metridoc.rid.RidConsTransaction; metridoc.rid.RidInsTransaction; org.grails.plugins.google.visualization.data.Cell; org.grails.plugins.google.visualization.util.DateUtil" %>

<g:if test="${session.transType == "consultation"}">
    <g:set var="entityName" value="${message(code: 'ridTransaction.label', default: 'RidConsTransaction')}"/>
</g:if>
<g:else>
    <g:set var="entityName" value="${message(code: 'ridTransaction.label', default: 'RidInsTransaction')}"/>
</g:else>
<md:report>
    <r:require module="tableModule"/>
    <r:require module="statistics"/>
    <!--[if !IE]><!-->
    <r:external dir="css" file="floating_tables_for_admin_5.css" plugin="metridoc-rid"/>
    <!--<![endif]-->

    <gvisualization:apiImport/>




    <div class="md-application-content">

    <tmpl:toggle/>
    <tmpl:tabs/>
    <g:if test="${session.transType == "consultation"}">

        <g:if test="${statResults.totalTransactions == 0}">
            <p>No transactions have been reported</p>
        </g:if>
        <g:else>

            <h1>
                Transaction Overview

            </h1>

            <div>
                <table class="table table-striped table-hover" id="transactionTable">
                    <thead>
                    <tr>

                        <th>Parameter</th>
                        <th>Average</th>
                        <th>Total</th>
                        <th>Past Year</th>
                        <g:each in="${statResults.months}">
                            <th>${it.getDateString().replaceAll('/1/', '/').padLeft(5, '0')}</th>
                        </g:each>

                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td>Transactions</td>
                        <td>N/A</td>
                        <td>${statResults.totalTransactions}</td>
                        <td>${statResults.yearTransactions}</td>
                        <g:each in="${statResults.monthTransactions}">
                            <th>${it}</th>
                        </g:each>
                    </tr>
                    <tr>
                        <td>Interact Occurrences</td>
                        <td>${statResults.avgInteractOccurrences}</td>
                        <td>${statResults.totalInteractOccurences}</td>
                        <td>${statResults.yearInteractOccurences}</td>
                        <g:each in="${statResults.monthInteractOccurences}">
                            <th>${it}</th>
                        </g:each>
                    </tr>
                    <tr>
                        <td>Prep Time (min)</td>
                        <td>${statResults.avgPrepTime}</td>
                        <td>${statResults.totalPrepTime}</td>
                        <td>${statResults.yearPrepTime}</td>
                        <g:each in="${statResults.monthPrepTime}">
                            <th>${it}</th>
                        </g:each>
                    </tr>
                    <tr>
                        <td>Event Length (min)</td>
                        <td>${statResults.avgEventLength}</td>
                        <td>${statResults.totalEventLength}</td>
                        <td>${statResults.yearEventLength}</td>
                        <g:each in="${statResults.monthEventLength}">
                            <th>${it}</th>
                        </g:each>
                    </tr>
                    </tbody>

                </table>

            </div>
            <%
                def dataColumns = [['string', 'Month'], ['number', 'Transactions']]
                def dataValues = []
                dataValues[0] = [statResults.months[0].getDateString().replaceAll('/1/', '/').padLeft(5, '0'), statResults.monthTransactions[0]]
                dataValues[1] = [statResults.months[1].getDateString().replaceAll('/1/', '/').padLeft(5, '0'), statResults.monthTransactions[1]]
                dataValues[2] = [statResults.months[2].getDateString().replaceAll('/1/', '/').padLeft(5, '0'), statResults.monthTransactions[2]]
                dataValues[3] = [statResults.months[3].getDateString().replaceAll('/1/', '/').padLeft(5, '0'), statResults.monthTransactions[3]]
                dataValues[4] = [statResults.months[4].getDateString().replaceAll('/1/', '/').padLeft(5, '0'), statResults.monthTransactions[4]]
                dataValues[5] = [statResults.months[5].getDateString().replaceAll('/1/', '/').padLeft(5, '0'), statResults.monthTransactions[5]]
                dataValues[6] = [statResults.months[6].getDateString().replaceAll('/1/', '/').padLeft(5, '0'), statResults.monthTransactions[6]]
                dataValues[7] = [statResults.months[7].getDateString().replaceAll('/1/', '/').padLeft(5, '0'), statResults.monthTransactions[7]]
                dataValues[8] = [statResults.months[8].getDateString().replaceAll('/1/', '/').padLeft(5, '0'), statResults.monthTransactions[8]]
                dataValues[9] = [statResults.months[9].getDateString().replaceAll('/1/', '/').padLeft(5, '0'), statResults.monthTransactions[9]]
                dataValues[10] = [statResults.months[10].getDateString().replaceAll('/1/', '/').padLeft(5, '0'), statResults.monthTransactions[10]]
                dataValues[11] = [statResults.months[11].getDateString().replaceAll('/1/', '/').padLeft(5, '0'), statResults.monthTransactions[11]]
                dataValues[12] = [statResults.months[12].getDateString().replaceAll('/1/', '/').padLeft(5, '0'), statResults.monthTransactions[12]]



            %>
            <gvisualization:lineCoreChart elementId="linechart" height="${400}" title="Transactions"
                                          columns="${dataColumns}" data="${dataValues}"
                                          chartArea="${new Expando(width: '100%', left: 20, top: 40)}" legend="bottom"/>
            <div id="linechart"></div>

        </g:else>

    </g:if>
    <g:else>Not Yet Implemented</g:else>
</md:report>


<%@page import="metridoc.funds.ReportCommand"%>
<%@ page contentType="text/html;charset=ISO-8859-1"%>
<g:set var="reportCommand"
       value="${request.reportCommand != null ? request.reportCommand: new metridoc.funds.ReportCommand()}" />
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
    <meta name="layout" content="funds_main" />
</head>
<body>
<div class="body">
    <g:form name="funds_form" method="post" action="fund_report">
        <table>
            <tr>
                <td>
                    <div class='formRowHead'>SELECT REPORT TYPE:</div>
                    <hr/>
                    <div class='formRow'>
                        <input name="reportType" class="radio"
                               value="${ReportCommand.SUMMARY_REPORT_TYPE}"
                               type="radio"
                            <%= reportCommand.reportType == ReportCommand.SUMMARY_REPORT_TYPE ? "checked=\"checked\"":"" %>>
                        <span class="formRowSubHead">Summary Report</span> (summary activity by reporting
                    funds without titles, publishers, etc.)

                        <div class='formRow'>
                            Select one ledger:&nbsp;&nbsp;
                            <g:select name="ledgerSumm" from="${ledgerOptions}"
                                      value="${reportCommand.ledgerSumm}" optionKey="id"
                                      optionValue="value" />
                        </div>
                    </div>
                    <div class='formRow'>
                        <input name="reportType"
                               value="${ReportCommand.EXPENDITURE_REPORT_TYPE}" type="radio"
                               class="radio" <%= reportCommand.reportType == ReportCommand.EXPENDITURE_REPORT_TYPE ? "checked=\"checked\"":"" %>> <span class="formRowSubHead">Expenditure Report</span>
                        (expenditure activity with titles, publishers, vendors, BibIDs,
                        POs)
                        <div  class='formRow'> Select one ledger or CTRL+select multiple ledgers for
                        comparison:</div>

                        <div class='formRow'>
                            <g:select name="ledgers" from="${ledgerOptions}"
                                      value="${reportCommand.ledgers}" optionKey="id"
                                      optionValue="value" multiple="true" />
                        </div>
                    </div>
                    <%--
                    <div class='formRow'>
                        Sort individual expenditures by:
                        <g:select name="sortBy" from="${sortByOptions}"
                            valueMessagePrefix="funds.report.sortBy" />

                        <select name="sortOrder">
                            <option value="asc" selected="selected">Ascending</option>
                            <option value="desc">Descending</option>
                        </select>
                    </div>
                    --%>
                </td>
            </tr>
            <tr>
                <td><div class='formRowHead'>FILTER REPORT BY:</div>
                    <hr/>
                    <div class='formRow'>
                        <input name="filterBy" class="radio"
                               value="${ReportCommand.FUND_FILTER_TYPE}" type="radio"
                            <%= reportCommand.filterBy == ReportCommand.FUND_FILTER_TYPE ? "checked=\"checked\"":"" %>>
                        Fund:&nbsp;
                        <g:select name="fundId" from="${fundList}"
                                  value="${reportCommand.fundId}" optionKey="sumfund_id"
                                  optionValue="sumfund_name" /> &nbsp;

                        <input name="filterBy" class="radio"
                               value="${ReportCommand.VENDOR_FILTER_TYPE}" type="radio"
                            <%= reportCommand.filterBy == ReportCommand.VENDOR_FILTER_TYPE ? "checked=\"checked\"":"" %>>
                        Vendor:&nbsp;
                        <g:select name="vendorCode"
                                  from="${vendorList}" value="${reportCommand.vendorCode}"
                                  optionKey="vendor_code" optionValue="vendor_name" />
                    </div>
                </td>
            </tr>
            <tr><td align="center">
                <input type="submit" name="submitBtn" value="Submit" id="submitBtn">
                <input type="reset" name="Reset" value="Reset"> </td></tr>
        </table>
    </g:form>
</div>
</body>
</html>
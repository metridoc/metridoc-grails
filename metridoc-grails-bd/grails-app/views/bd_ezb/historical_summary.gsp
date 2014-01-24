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

<%@ page contentType="text/html;charset=ISO-8859-1" %>
<g:set var="allRowName" value="All Libraries"/>
<g:if test="${selectedLibIds != null && selectedLibIds.size() > 0}">
    <g:set var="allRowName" value="All Selected Libraries"/>
<%--<g:set var="additionalParams" value="&lIds=${selectedLibIds }"/>--%>

</g:if>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"/>
    <meta name="layout" content="bd_main"/>
    <title>Historical Summary</title>
    <script>
        function getSummary(year) {
            document.summary.fiscalYear.value = year;
            document.summary.submit();
        }

    </script>
</head>

<body>
<div class="body_bd">
    <div style="text-align: right;">
        <g:if test="${controllerName.startsWith("EZBorrow")}">
            <a href="http://datafarm.library.upenn.edu/bdez/EZhist.html" target="_historical">Older Data</a>
        </g:if>
        <g:else>
            <a href="http://datafarm.library.upenn.edu/bdez/BDhist.html" target="_historical">Older Data</a>
        </g:else>
    </div>

    <div class="pageTitle">
        <g:if test="${libraryId != null}">${libName} :</g:if>
        Historical Summary Dashboard <br>[Click the year for details]</div>

    <form action="summary" name="summary" method="POST">
        <input type="hidden" name="fiscalYear" value=""/>
        <g:each var="id" in="${selectedLibIds}">
            <input type="hidden" name="lIds" value="${id}"/>
        </g:each>
        <g:if test="${libraryId != null}">
            <input type="hidden" name="library" value="${libraryId}"/>
        </g:if>
    </form>
    <table class="list summary" cellspacing="0">
        <thead>
        <tr>
            <th class="mainColHeader" rowspan="2">Borrowing</th>
            <g:each var="i" in="${(reportData.currentFiscalYear..reportData.minFiscalYear)}">
                <th colspan="2">
                    <a href="javascript:getSummary(${i != reportData.currentFiscalYear ? i : ""})">${i}</a>
                    <%--
                   <g:if test="${i == reportData.currentFiscalYear}">
                       <g:link action="summary" params="[lIds:selectedLibIds]">${ i }</g:link>
                   </g:if>
                   <g:else>
                       <g:link action="summary" params="[fiscalYear:i, lIds:selectedLibIds]">${ i }</g:link>
                   </g:else>
                    --%>
                </th>
            </g:each>
        </tr>
        <tr>
            <g:each var="i" in="${(reportData.currentFiscalYear..reportData.minFiscalYear)}">
                <th>Items</th> <th>Fill Rate</th>
            </g:each>
        </tr>
        </thead>
        <tbody>
        <g:set var="currentDataMap" value="${reportData.get(-1l) != null ? reportData.get(-1l).borrowing : [:]}"/>
        <g:render template="/bd_ezb/historical_summary_row"
                  model="[currentDataMap: currentDataMap,
                          index: 0,
                          libName: allRowName,
                          lending: false,
                          minFiscalYear: reportData.minFiscalYear,
                          currentFiscalYear: reportData.currentFiscalYear]"/>
        <g:set var="rowOffset" value="${0}"/>
        <g:each var="library" status="i" in="${libraries}">
            <g:if test="${libraryId == null || libraryId != library.library_id}">
                <g:set var="currentDataMap"
                       value="${reportData.get(library.library_id.longValue()) != null ? reportData.get(library.library_id.longValue()).borrowing : [:]}"/>
                <g:render template="/bd_ezb/historical_summary_row"
                          model="[currentDataMap: currentDataMap,
                                  index: (i + 1 - rowOffset),
                                  libName: library.institution,
                                  lending: false,
                                  minFiscalYear: reportData.minFiscalYear,
                                  currentFiscalYear: reportData.currentFiscalYear]"/>
            </g:if>
            <g:else>
                <g:set var="rowOffset" value="${1}"/>
            </g:else>
        </g:each>
        </tbody></table>
    <br>
    <table class="list summary" cellspacing="0">
        <thead>
        <tr>
            <th class="mainColHeader" rowspan="2">Lending</th>
            <g:each var="i" in="${(reportData.currentFiscalYear..reportData.minFiscalYear)}">
                <th colspan="2">
                    <a href="javascript:getSummary(${i != reportData.currentFiscalYear ? i : ""})">${i}</a>
                    <%--
                   <g:if test="${i == reportData.currentFiscalYear}">
                       <g:link action="summary">${ i }</g:link>
                   </g:if>
                   <g:else>
                       <g:link action="summary" params="[fiscalYear:i]">${ i }</g:link>
                   </g:else>--%>
                </th>
            </g:each>
        </tr>
        <tr>
            <g:each var="i" in="${(reportData.currentFiscalYear..reportData.minFiscalYear)}">
                <th>Items</th> <th>Fill Rate</th>
            </g:each>
        </tr>
        </thead>
        <tbody>
        <g:set var="currentDataMap" value="${reportData.get(-1l) != null ? reportData.get(-1l).lending : [:]}"/>
        <g:render template="/bd_ezb/historical_summary_row"
                  model="[currentDataMap: currentDataMap,
                          index: 0,
                          libName: allRowName,
                          lending: true,
                          minFiscalYear: reportData.minFiscalYear,
                          currentFiscalYear: reportData.currentFiscalYear]"/>
        <g:set var="rowOffset" value="${0}"/>
        <g:each var="library" status="i" in="${libraries}">
            <g:if test="${libraryId == null || libraryId != library.library_id}">
                <g:set var="currentDataMap"
                       value="${reportData.get(library.library_id.longValue()) != null ? reportData.get(library.library_id.longValue()).lending : [:]}"/>
                <g:render template="/bd_ezb/historical_summary_row"
                          model="[currentDataMap: currentDataMap,
                                  index: (i + 1 - rowOffset),
                                  libName: library.institution,
                                  lending: true,
                                  minFiscalYear: reportData.minFiscalYear,
                                  currentFiscalYear: reportData.currentFiscalYear]"/>
            </g:if>
            <g:else>
                <g:set var="rowOffset" value="${1}"/>
            </g:else>
        </g:each>
        </tbody></table>
</div>
</body>
</html>
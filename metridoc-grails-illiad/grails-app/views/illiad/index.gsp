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

<%@ page import="metridoc.utils.DateUtil" %>
<%--
  Created by IntelliJ IDEA.
  User: tbarker
  Date: 8/3/12
  Time: 3:26 PM
  To change this template use File | Settings | File Templates.
--%>

<md:report>

    <div id="updateInfo">last update: ${lastUpdated}</div>
    <md:header>Borrowing for the Current Fiscal Year (${month} to Present)</md:header>

    <div class='subReportBody'>
        <tmpl:aggregationHeader type="Loan" borrowing="true">Books</tmpl:aggregationHeader>
        <g:render template="/illiad/summary_group"
                  model="[summaryData: basicStatsData.books.borrowing,
                          allRowName: allRowName,
                          groups: groups,
                          isBorrowing: true]"/>
        <tmpl:aggregationHeader type="Article" borrowing="true">Articles</tmpl:aggregationHeader>
        <g:render template="/illiad/summary_group"
                  model="[summaryData: basicStatsData.articles.borrowing,
                          allRowName: allRowName,
                          groups: groups,
                          isBorrowing: true]"/>
    </div>

    <md:header>Lending for the Current Fiscal Year (${month} to Present)</md:header>

    <div class='subReportBody'>

        <tmpl:aggregationHeader type="Loan" borrowing="false">Books</tmpl:aggregationHeader>
        <g:render template="/illiad/summary_group"
                  model="[summaryData: basicStatsData.books.lending,
                          allRowName: allRowName,
                          groups: groups,
                          isBorrowing: false]"/>
        <tmpl:aggregationHeader type="Article" borrowing="false">Articles</tmpl:aggregationHeader>
        <g:render template="/illiad/summary_group"
                  model="[summaryData: basicStatsData.articles.lending,
                          allRowName: allRowName,
                          groups: groups,
                          isBorrowing: false]"/>
    </div>

</md:report>
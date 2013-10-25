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

<strong>
    Borrowing for the Current Fiscal Year
</strong>
<hr/>

<div class='subReportBody'>
    <div class='subHeadRow'>Books</div>
    <g:render template="/illiad/summary_group"
              model="[summaryData: basicStatsData.books.borrowing,
                      allRowName: allRowName,
                      groups: groups,
                      isBorrowing: true]"/>
    <div class='subHeadRow'>Articles</div>
    <g:render template="/illiad/summary_group"
              model="[summaryData: basicStatsData.articles.borrowing,
                      allRowName: allRowName,
                      groups: groups,
                      isBorrowing: true]"/>
</div>

<strong>
    Lending for the Current Fiscal Year
</strong>
<hr/>

<div class='subReportBody'>

    <div class='subHeadRow'>Books</div>
    <g:render template="/illiad/summary_group"
              model="[summaryData: basicStatsData.books.lending,
                      allRowName: allRowName,
                      groups: groups,
                      isBorrowing: false]"/>
    <div class='subHeadRow'>Articles</div>
    <g:render template="/illiad/summary_group"
              model="[summaryData: basicStatsData.articles.lending,
                      allRowName: allRowName,
                      groups: groups,
                      isBorrowing: false]"/>
</div>

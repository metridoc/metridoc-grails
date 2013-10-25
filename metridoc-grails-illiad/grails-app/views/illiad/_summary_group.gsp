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

<table class="illiadTable table table-striped table hover">
 <thead>
<g:if test="${isBorrowing}">
    <tr>
        <th class="mainColHeader" rowspan="2">&nbsp;</th>
        <th rowspan="2"># Requests</th>
        <th rowspan="2"># Filled</th>
        <th rowspan="2">Filled %</th>
        <th colspan="3">Turnaround</th>
        <th rowspan="2"># Exhausted reqs.</th>
        <th rowspan="2">Exhausted %</th>
        <th rowspan="2">Sum fees</th>
    </tr>
    <tr>
        <th>Req-Shp</th>
        <th>Shp-Rec</th>
        <th>Req-Rec</th>
    </tr>
    </thead>
    <tbody>
    <g:set var="currentDataMap" value="${summaryData.get("-1") != null ? summaryData.get("-1") : [:]}"/>
    <g:render template="/illiad/summary_row"
              model="[currentDataMap: currentDataMap,
                      index: 0,
                      groupName: allRowName,
                      isBorrowing: isBorrowing,
                      isTotal: true]"/>
    </tbody>
    </table>
    <br>
    <table class="illiadTable table table-striped table-hover">
    <thead>
   <tr>
     <th class="mainColHeader" rowspan="2">&nbsp;</th>
     <th rowspan="2"># Requests</th>
     <th colspan="3">Turnaround</th>
     <th rowspan="2">Sum fees</th>
   </tr>
   <tr>
     <th>Req-Shp</th>
     <th>Shp-Rec</th>
     <th>Req-Rec</th>
   </tr>
</g:if>
<g:else>
    <tr>
        <th class="mainColHeader">&nbsp;</th>
        <th># Requests</th>
        <th># Filled</th>
        <th>Filled %</th>
        <th>Turnaround time</th>
        <th># Exhausted reqs.</th>
        <th>Exhausted %</th>
        <th>Sum fees</th>
    </tr>
    </thead>
    <tbody>
    <g:set var="currentDataMap" value="${summaryData.get("-1") != null ? summaryData.get("-1") : [:]}"/>
    <g:render template="/illiad/summary_row"
              model="[currentDataMap: currentDataMap,
                      index: 0,
                      groupName: allRowName,
                      isBorrowing: isBorrowing,
                      isTotal: true]"/>
    </tbody>
    </table>
    <br>
    <table class="illiadTable table-striped table table-hover">
    <thead>
   <tr>
     <th class="mainColHeader">&nbsp;</th>
     <th># Requests</th>
     <th># Filled</th>
     <th>Filled %</th>
     <th>Turnaround time</th>
     <th># Exhausted reqs.</th>
     <th>Exhausted %</th>
     <th>Sum fees</th>
   </tr>
</g:else>
</thead>
<tbody>
<g:each var="group" status="i" in="${groups}">
    <g:set var="currentDataMap"
           value="${summaryData.get(String.valueOf(group.groupNo)) != null ? summaryData.get(String.valueOf(group.groupNo)) : [:]}"/>
    <g:render template="/illiad/summary_row"
              model="[currentDataMap: currentDataMap,
                      index: (i + 1),
                      groupName: group.groupName,
                      isBorrowing: isBorrowing]"/>
</g:each>
</tbody></table>
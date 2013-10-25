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

<% int filledRequests = currentDataMap.filledRequests != null ? currentDataMap.filledRequests : 0;
int exhaustedRequests = currentDataMap.exhaustedRequests != null ? currentDataMap.exhaustedRequests : 0;
int allRequests = filledRequests + exhaustedRequests %>
<tr class="${(index % 2) == 0 ? 'even' : 'odd'}">
    <td>${groupName}</td>
    <td class="dataCell"><g:formatNumber number="${allRequests}" format="###,###,##0"/></td>
    <g:if test="${!isBorrowing || isTotal}">
        <td class="dataCell"><g:formatNumber number="${filledRequests}" format="###,###,##0"/></td>
        <td class="dataCell"><g:formatNumber number="${allRequests != 0 ? (filledRequests / allRequests) * 100 : 0}"
                                             format="0.00"/></td>
    </g:if>
    <g:if test="${isBorrowing}">
        <td class="dataCell">
            <g:if test="${currentDataMap.turnaroundReqShp != null}">
                <g:formatNumber number="${currentDataMap.turnaroundReqShp}" format="0.00"/>
            </g:if>
            <g:else>--</g:else>
        </td>
        <td class="dataCell">
            <g:if test="${currentDataMap.turnaroundShpRec != null}">
                <g:formatNumber number="${currentDataMap.turnaroundShpRec}" format="0.00"/>
            </g:if>
            <g:else>--</g:else>
        </td>
        <td class="dataCell">
            <g:if test="${currentDataMap.turnaroundReqRec != null}">
                <g:formatNumber number="${currentDataMap.turnaroundReqRec}" format="0.00"/>
            </g:if>
            <g:else>--</g:else>
        </td>
    </g:if>
    <g:else>
        <td class="dataCell">
            <g:if test="${currentDataMap.turnaround != null}">
                <g:formatNumber number="${currentDataMap.turnaround}" format="0.00"/>
            </g:if>
            <g:else>
                --
            </g:else>
        </td>
    </g:else>
    <g:if test="${!isBorrowing || isTotal}">
        <td class="dataCell"><g:formatNumber number="${exhaustedRequests}" format="###,###,##0"/></td>
        <td class="dataCell"><g:formatNumber number="${allRequests != 0 ? (exhaustedRequests / allRequests) * 100 : 0}"
                                             format="0.00"/></td>
    </g:if>
    <td class="dataCell"><g:formatNumber number="${currentDataMap.sumFees != null ? currentDataMap.sumFees : 0}"
                                         format="###,###,##0"/></td>
</tr>
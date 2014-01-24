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

<tr class="${ (index % 2) == 0 ? 'even' : 'odd'}">
     	<td>${libName}</td>
    	 <g:each var="i" in="${(currentFiscalYear .. minFiscalYear)}">
    	 	<td class="dataCell"><g:formatNumber number="${currentDataMap.items != null && currentDataMap.items[i] != null ? currentDataMap.items[i]:0 }" format="###,###,##0" /></td>
    	 	<td class="dataCell">
    	 	<g:if test="${currentDataMap.fillRates != null && currentDataMap.fillRates[i] != null && currentDataMap.fillRates[i] > -1}">
    	 		<g:formatNumber number="${currentDataMap.fillRates[i]}" format="0.00" />
    	 	</g:if>
    	 	<g:else>--</g:else>
    	 </td>
    	 </g:each>  	
</tr>
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
  - 	permissions and limitations under the License.
  --}%

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
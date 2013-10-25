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

<div class='subHeadRow'>
    <a href="${createLink(action: "download", params: [borrowing: borrowing, type: type])}">
        <icon class="icon-download-alt" data-toggle="tooltip" data-original-title="Download all available illiad data for this table"></icon></a>
    <span class="aggregation-header" data-toggle="tooltip"
          data-original-title="Items below may not sum to the aggregates since transactions can be in mulltiple groups">${body()}*</span>
</div>
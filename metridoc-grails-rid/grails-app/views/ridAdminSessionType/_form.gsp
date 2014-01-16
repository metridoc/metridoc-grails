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

<%@ page import="metridoc.rid.RidSessionType" %>

<g:render template="/ridAdminBase/baseForm" plugin="metridoc-rid"/>

<div class="control-group fieldcontain ${hasErrors(bean: ridInstance, field: 'ridLibraryUnit', 'error')} required">

    <div class="controls">
        <label class="control-label admin-label" for="ridLibraryUnit">
            <g:message code="ridSessionTypeInstance.ridLibraryUnit.label" default="Library Unit"/>
            <span class="required-indicator">*</span>
        </label>
        <g:select id="ridLibraryUnit" style="width:120px" name="ridLibraryUnit.id"
                  from="${metridoc.rid.RidLibraryUnit.list()}"
                  optionKey="id" required="" value="${ridInstance?.ridLibraryUnit?.id}"
                  class="many-to-one"/>
    </div>
</div>
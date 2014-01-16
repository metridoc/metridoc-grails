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

<r:require module="ridAdminBase"/>

<div class="control-group fieldcontain required">

    <div class="controls">
        <label class="control-label admin-label" for="name">
            &nbsp;&nbsp;&nbsp;Name
            <span class="required-indicator">*</span>
        </label>
        <input type="text" class="userInput" name="name" required="" value="" id="name">
    </div>
</div>

<div class="control-group fieldcontain required">

    <div class="controls">
        <label class="control-label admin-label" for="inForm">
            <g:message code="${ridInstance}.inForm.label" default="In Form"/>
            <span class="required-indicator">*</span>
        </label>
        <% def choices = ['NO', 'YES, and no indication needed', 'YES, and indication required'] %>
        <g:select name="inForm" from="${choices}" required=""
                  value="${ridInstance?.inForm}"
                  keys="${ridInstance.constraints.inForm.inList}"/>
    </div>
</div>


%{--

        <div class="control-group fieldcontain required">
            <label class="control-label" for="name">
                Name
                <span class="required-indicator">*</span>
            </label>

            <div class="controls">
                <input type="text" class="userInput" name="name" required="" value="" id="name">
            </div>
        </div>

        <div class="control-group fieldcontain required">
            <label class="control-label" for="inForm">
                In Form
                <span class="required-indicator">*</span>
            </label>

            <div class="controls">

                <select name="inForm" required="" id="inForm">
                    <option value="0" selected="selected">NO</option>
                    <option value="1">YES, and no indication needed</option>
                    <option value="2">YES, and indication required</option>
                </select>
            </div>
        </div>

    </div>
--}%

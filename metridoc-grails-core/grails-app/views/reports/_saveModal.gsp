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

<div id="${modalName}Modal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="${modalName}Modal"
     aria-hidden="true">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>

        <h3 id="${modalName}ModalLabel">${modalHeader}</h3>
    </div>
    <g:form action="${modalFormAction}" id="${modalFormId}" controller="${modalFormController}" class="${modalFormClass}">
        <div class="modal-body">
            ${modalBody}
        </div>

        <div class="modal-footer">
            <button class="btn" data-dismiss="modal" aria-hidden="true">Close</button>
            <button class="btn" type="submit" value="Save" id="${modalName}SubmitButton">${modalSaveChanges}</button>
        </div>
    </g:form>
</div>
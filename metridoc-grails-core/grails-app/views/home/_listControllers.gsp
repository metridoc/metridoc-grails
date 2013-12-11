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

<span class="linkContainer">
    <div class="wMargin">
        <md:header>${category.appName} <i class="${category.iconName}"></i>
            <a href="#" onclick="showApps(this.id)" class="categoryHeader">
                <i class="icon-minus-sign"></i>
            </a>

        </md:header>
    </div>

    <g:if test="${!category.controllerInfo}">
        <ul class="undecorated">
            <li>No applications available</li>
        </ul>
    </g:if>
    <div class="categoryDiv">
        <g:each in="${category.controllerInfo}" var="controller" status="i">
            <ul class="undecorated">
                <li><a href="${createLink(controller: controller.link, action: "index")}">${controller.title}</a><g:if
                        test="${controller.description}"> - </g:if>${controller.description}</li>
            </ul>
        </g:each>
    </div>
</span>

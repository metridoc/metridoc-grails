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

<g:render
        template="/ridTransaction/tabLabel"
        plugin="metridocRid"
        model="[controllerName: controllerName,
                actionName: actionName,
                linkController: 'ridAdminCourseSponsor',
                linkAction: 'list',
                linkText: 'Course Sponsor',
        ]"/>

<g:render
        template="/ridTransaction/tabLabel"
        plugin="metridocRid"
        model="[controllerName: controllerName,
                actionName: actionName,
                linkController: 'ridAdminRank',
                linkAction: 'list',
                linkText: 'Rank',
        ]"/>
<g:render
        template="/ridTransaction/tabLabel"
        plugin="metridocRid"
        model="[controllerName: controllerName,
                actionName: actionName,
                linkController: 'ridAdminModeOfConsultation',
                linkAction: 'list',
                linkText: 'Consultation Mode',
        ]"/>

<g:render
        template="/ridTransaction/tabLabel"
        plugin="metridocRid"
        model="[controllerName: controllerName,
                actionName: actionName,
                linkController: 'ridAdminUserGoal',
                linkAction: 'list',
                linkText: 'User Goal',
        ]"/>
<g:render
        template="/ridTransaction/tabLabel"
        plugin="metridocRid"
        model="[controllerName: controllerName,
                actionName: actionName,
                linkController: 'ridAdminServiceProvided',
                linkAction: 'list',
                linkText: 'Service Provided',
        ]"/>
<g:render
        template="/ridTransaction/tabLabel"
        plugin="metridocRid"
        model="[controllerName: controllerName,
                actionName: actionName,
                linkController: 'ridAdminExpertise',
                linkAction: 'list',
                linkText: 'Expertise',
        ]"/>
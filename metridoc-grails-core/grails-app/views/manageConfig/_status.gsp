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

<md:header>Status</md:header>
<div id="status">
    <ul>
        <li><strong>Java Exec:&nbsp;&nbsp;</strong><code>${javaCommand}</code></li>
        <li><strong>Java VM Arguments:&nbsp;&nbsp;</strong><code>${javaVmArguments}</code></li>
        <li><strong>Main Command:&nbsp;&nbsp;</strong><code>${mainCommand}</code></li>
        <li><strong>Data source url:&nbsp;&nbsp;</strong><code>${dataSourceUrl}</code></li>
        <li><strong>Application Name:&nbsp;&nbsp;</strong><code>${applicationName}</code></li>
        <li>
            <strong>Shiro Filters:</strong>

            <div>
                <pre>${shiroFilters}</pre>
            </div>
        </li>
    </ul>
</div>
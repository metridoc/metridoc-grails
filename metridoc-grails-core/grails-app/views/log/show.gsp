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

<md:report>
    <g:form class="form-inline">
        <a id="scrollTop" href="#" class="btn"><i class="icon-arrow-up"></i> Top</a>
        <a id="scrollBottom" href="#" class="btn"><i class="icon-arrow-down"></i> Bottom</a>
        <g:select name="logFile" from="${logFiles}" value="${initialValue}"/>
        <label class="checkbox">
            <input type="checkbox" id="doStreaming" name="doStreaming"/> Stream
        </label>

    </g:form>

    <div id="metridocLogsContainer">
        <div id="metridocLogs">
            <tmpl:plainLog/>
        </div>
    </div>

</md:report>
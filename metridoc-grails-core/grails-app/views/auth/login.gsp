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

<md:report module="login">
    <div id="template">

        <g:form action="signIn" class="form-horizontal monitored-form">

            <input type="hidden" name="targetUri" value="${targetUri}"/>

            <div class="control-group">
                <label for="username">User Name :</label>

                <div class="controls">
                    <input type="text" id="username" name="username" value="${username}" placeholder="User Name"/>
                </div>
                <label for="password">Password :</label>

                <div class="controls inline-password-control">
                    <input type="password" id="password" name="password" value="" placeholder="Password"/>
                    <button type="submit" class="btn">
                        Sign In
                    </button>
                </div>
                <label for="rememberMe" id="rememberLabel">Remember Me? :</label>

                <div class="controls">
                    <g:checkBox name="rememberMe" value="${rememberMe}"/>
                </div>
            </div>
        </g:form>
    </div>
</md:report>
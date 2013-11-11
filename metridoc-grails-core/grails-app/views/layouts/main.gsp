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

<!DOCTYPE html>
<%@ page import="org.apache.shiro.SecurityUtils" %>
<!--

    Copyright 2010 Trustees of the University of Pennsylvania Licensed under the
    Educational Community License, Version 2.0 (the "License"); you may
    not use this file except in compliance with the License. You may
    obtain a copy of the License at

    http://www.osedu.org/licenses/ECL-2.0

    Unless required by applicable law or agreed to in writing,
    software distributed under the License is distributed on an "AS IS"
    BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
    or implied. See the License for the specific language governing
    permissions and limitations under the License.

-->

<!--[if lt IE 7 ]> <html lang="en" class="no-js ie6"> <![endif]-->
<!--[if IE 7 ]>    <html lang="en" class="no-js ie7"> <![endif]-->
<!--[if IE 8 ]>    <html lang="en" class="no-js ie8"> <![endif]-->
<!--[if IE 9 ]>    <html lang="en" class="no-js ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!--> <html lang="en" class="no-js"><!--<![endif]-->
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <title>MetriDoc</title>
    <!-- make sure console works in any browser (ie won't crash in ie!) -->
    <script type="text/javascript">
        if (!console) console = {log: function () {
        }};
    </script>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <g:javascript library="jquery"/>
    <r:require module="application"/>
    <g:layoutHead/>
    <r:layoutResources/>
</head>

<body>

<div class="shadow container">
    <div id="metridocBanner" role="banner">
        <a id="metridocLogo" href="http://metridoc.googlecode.com">
            <img src="${resource(plugin: 'metridocCore', dir: 'images', file: 'MDlogo_small.png')}"
                 alt="MetriDoc"/>
        </a>

        <% if (SecurityUtils.subject.principal == "anonymous" || !SecurityUtils.subject.principal) { %>
        <a id="metridocLoginLink" href="/<g:meta name="app.name"/>/auth">login</a>
        <% } else { %>
        <span id="metridocLoginLink">
            <a href="/<g:meta name="app.name"/>/profile/edit">${SecurityUtils.subject.principal}</a> (<a
                href="/<g:meta name="app.name"/>/auth/signOut">logout</a>)
        </span>
        <% } %>

    </div>

    <div class="navbar navbar-inverse">
        <div class="navbar-inner">
            <div>
                <a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </a>

                <div class="nav-collapse collapse">
                    <ul class="nav">

                        <g:if test="${"home" == controllerName}">
                            <li class="active"><g:link controller="home"><i class="icon-home"></i> Home</g:link></li>
                        </g:if>
                        <g:else>
                            <li><g:link controller="home"><i class="icon-home"></i> Home</g:link></li>
                        </g:else>
                        <shiro:hasRole name="ROLE_ADMIN">
                            <li class="dropdown">
                                <a href="#" class="dropdown-toggle" data-toggle="dropdown">Settings <b
                                        class="caret"></b></a>
                                <ul class="dropdown-menu">
                                    <li><g:link controller="profile"><i class="icon-user"></i> Account</g:link></li>
                                    <li><g:link controller="log"><i class="icon-file-alt"></i> Application Log</g:link>
                                    </li>
                                    <li><g:link controller="manageAccess"><i
                                            class="icon-group"></i> Manage Metridoc</g:link>
                                    </li>
                                </ul>
                            </li>
                        </shiro:hasRole>
                    </ul>

                </div><!--/.nav-collapse -->
            </div>
        </div>
    </div>

    <md:alerts model="${params}"/>
    <g:layoutBody/>


    <div id="spinner" class="spinner" style="display:none;">
        <g:message code="spinner.alt" default="Loading&hellip;"/>
    </div>
    <g:if test="${"home" != controllerName && applicationContext.manageConfigService.getReportIssueEmails()}">
        <div id="metridocFooter">
            <shiro:isLoggedIn>
                <a id="metridocReportIssues"
                   href="mailto:${applicationContext.manageConfigService.getReportIssueEmails()}?Subject=Metridoc%20Issue"
                   target="_blank">Report Issues</a>
            </shiro:isLoggedIn>
        </div>
    </g:if>
</div>

<g:if test="${"home" == controllerName && applicationContext.manageConfigService.getReportIssueEmails()}">
    <div id="metridocFooter">
        <shiro:isLoggedIn>
            <a id="metridocReportIssues"
               href="mailto:${applicationContext.manageConfigService.getReportIssueEmails()}?Subject=Metridoc%20Issue"
               target="_blank">Report Issues</a>
        </shiro:isLoggedIn>
    </div>
</g:if>

<r:layoutResources/>

</body>
</html>
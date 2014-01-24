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

<%@ page import="grails.util.Holders" %>
<%@ page import="metridoc.penn.bd.BorrowDirectService" %>
<!DOCTYPE html>
<html>
<head>
    <title>
        <g:if test="${BorrowDirectService.EZB_SERVICE_KEY.equals(serviceKey)}">
            <g:layoutTitle default="E-ZBorrow"/></g:if>
        <g:else><g:layoutTitle default="BorrowDirect"/></g:else>

    </title>
    <link rel="shortcut icon"
          href="${resource(dir: 'images', file: 'favicon.ico')}" type="image/x-icon"/>
    <g:layoutHead/>
    <r:require module="ezb_bd"/>
    <r:layoutResources/>
</head>

<body>
<div align="center">
    <table class="mainContainer">
        <tr class="header">
            <!-- 500 -->
            <td width="70%" bgcolor="#CC0000" align="left"><g:link action=""><span>
                <g:if test="${BorrowDirectService.EZB_SERVICE_KEY.equals(serviceKey)}">
                    E-ZBorrow</g:if>
                <g:else>BorrowDirect</g:else>
                Data Repository${Holders.applicationContext.grailsApplication.config.datafarm.title.ext}</span></g:link>
            </td><!-- 250 -->
            <td width="30%" bgcolor="#333366" align="center">
                <g:link url="/"><span>Penn Library DATA FARM</span></g:link>
            </td>

        </tr>
        <g:if test="${showTopLinks || reportName != null}">
            <tr>
                <td align="center" colspan="2">
                    <g:if test="${showTopLinks}">
                        <a href="mailto:datafarm@pobox.upenn.edu">Report a Problem</a>
                    </g:if>
                    <g:elseif test="${reportName != null}">
                        <div class="pageTitle" style="margin-bottom:0">${reportName}</div>
                    </g:elseif>
                </td>
            </tr>
        </g:if>
        <tr>
            <td colspan="2" align="center">
                <g:layoutBody/>
            </td>
        </tr>
        <tr>
            <td class="footer" colspan="2" align="center">University of Pennsylvania Library | Data Farm</td>
        </tr>
        <tr><td colspan="2" align="center">
            <g:link url="http://code.google.com/p/metridoc/"><img width="90" alt="metridoc"
                                                                  src="${resource(dir: 'images', file: 'E.jpg')}"
                                                                  style="border:0px"></g:link></td></tr>
    </table>
</div>
<r:layoutResources/>
</body>
</html>
<%@page import="org.codehaus.groovy.grails.commons.ConfigurationHolder"%>
<!DOCTYPE html>
<html>
<head>
    <title>
        <g:layoutTitle default="Voyager Fund Reports" />
    </title>
    <link rel="stylesheet" href="${resource(dir:'css',file:'funds_main.css')}" />
    <r:require module="funds"/>
    <r:layoutResources/>
    <g:layoutHead />
</head>
<body>
<div align="center">
    <table class="mainContainer">
        <tr class="header">
            <!-- 500 -->
            <td width="70%" bgcolor="#CC0000" align="left"><g:link action=""><span>
                Voyager Funds Report Builder</span></g:link>
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
                <g:layoutBody />
            </td>
        </tr>
        <tr>
            <td class="footer" colspan="2" align="center">University of Pennsylvania Library | Data Farm</td>
        </tr>
        <tr><td colspan="2" align="center">
            <g:link url="http://code.google.com/p/metridoc/"><img width="90" alt="metridoc" src="${resource(dir:'images',file:'E.jpg')}" style="border:0px"></g:link></td></tr>
    </table>
</div>
<r:layoutResources/>
</body>
</html>
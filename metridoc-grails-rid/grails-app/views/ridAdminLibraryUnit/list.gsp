<%@ page import="org.codehaus.groovy.grails.io.support.ClassPathResource; metridoc.rid.RidLibraryUnit" %>
<g:set var="entityName" value="${message(code: 'ridLibraryUnit.label', default: 'RidLibraryUnit')}"/>

<md:report>
    <r:require module="tableModule"/>

    <!--[if !IE]><!-->
    <r:external dir="css" file="floating_tables_for_admin_6.css" plugin="metridoc-rid"/>
    <!--<![endif]-->

    <div class="md-application-content">
        <g:render template="/ridAdminTransaction/toggle" plugin="metridoc-rid"/>

        <g:render template="/ridAdminTransaction/tabs" plugin="metridoc-rid"/>
        <g:render template="/ridAdminTransaction/modal" plugin="metridocRid"
                  model="[title: entityName + ' Create/Edit']"/>

        <div id="list-ridLibraryUnit" class="content scaffold-list" role="main">
            <h1>
                <g:message code="default.list.label" args="[entityName]"/>
                <a data-tooltip="Creating" href="create?dummy=${org.apache.commons.lang.math.RandomUtils.nextInt()}"
                   data-target="#myModal" data-toggle="modal">
                    <i title="Create Library Unit" class="icon-plus-sign-alt"></i>
                </a>
            </h1>

            <g:hasErrors bean="${ridDomainClassError}">
                <div class="errors">
                    <g:renderErrors bean="${ridDomainClassError}" as="list"/>
                </div>
            </g:hasErrors>

            <table class="table table-striped table-hover">
                <thead>
                <tr>
                    <g:sortableColumn property="name"
                                      title="${message(code: 'ridLibraryUnit.name.label', default: 'Name')}"/>
                    <th>Number of RidTransaction</th>
                    <th>Spreadsheet File</th>
                </tr>
                </thead>
                <tbody>
                <g:each in="${ridInstanceList}" var="ridInstance">
                    <tr>
                        <td>
                            <a data-toggle="modal"
                               href="edit/${ridInstance.id}?dummy=${org.apache.commons.lang.math.RandomUtils.nextInt()}"
                               data-target="#myModal">${fieldValue(bean: ridInstance, field: "name")}</a>
                        </td>
                        <g:if test="${session.transType == "consultation"}">
                            <td>${ridInstance?.ridConsTransaction?.size()}</td>
                        </g:if>
                        <g:else>
                            <td>${ridInstance?.ridInsTransaction?.size()}</td>
                        </g:else>
                        <%
                            File resource =
                                new File(System.getProperty("user.home") + "/.metridoc/files/rid/libraryUnit/" + ridInstance.name + '_Bulkload_Schematic.xlsx')
                        %>
                        <td>
                            <g:if test="${resource.exists()}">
                                <g:link action="download"
                                        id="ridLibraryUnit" name="ridInstance.name"
                                        params="${[sname: ridInstance.name + '_Bulkload_Schematic.xlsx']}">
                                    ${ridInstance.name + '_Bulkload_Schematic.xlsx'}
                                </g:link>
                            </g:if>
                            <g:else>
                                <span style="color: #ff0000;">WARNING: NO spreadsheet!</span>
                            </g:else>
                        </td>
                    </tr>
                </g:each>
                </tbody>
            </table>
            <g:if test="${ridInstanceTotal > 10}">
                <div class="pagination">
                    <g:paginate total="${ridInstanceTotal}"/>
                </div>
            </g:if>
        </div>
    </div>
</md:report>


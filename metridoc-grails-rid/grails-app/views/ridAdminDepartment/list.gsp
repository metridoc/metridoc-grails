<%@ page import="metridoc.rid.RidDepartment" %>
<g:set var="entityName"
       value="${message(code: 'ridDepartment.label', default: 'RidDepartment')}"/>

<md:report>
    <r:require module="tableModule"/>
    <!--[if !IE]><!-->
    <r:external dir="css" file="floating_tables_for_admin_5.css" plugin="metridoc-rid"/>
    <!--<![endif]-->

    <div class="md-application-content">
        <g:render template="/ridAdminTransaction/toggle" plugin="metridoc-rid"/>

        <g:render template="/ridAdminTransaction/tabs" plugin="metridoc-rid"/>
        <g:render template="/ridAdminTransaction/modal" plugin="metridocRid"
                  model="[title: entityName + ' Create/Edit']"/>

        <div id="list-ridDepartment" class="content scaffold-list" role="main">
            <h1>
                <g:message code="default.list.label" args="[entityName]"/>
                <a data-tooltip="Creating" href="create?dummy=${org.apache.commons.lang.math.RandomUtils.nextInt()}"
                   data-target="#myModal" data-toggle="modal">
                    <i title="Create Departmental Affiliation" class="icon-plus-sign-alt"></i>
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
                                      title="${message(code: 'ridDepartment.name.label', default: 'Name')}"/>
                    <g:sortableColumn property="fullName"
                                      title="${message(code: 'ridDepartment.fullName.label', default: 'Full Name')}"/>
                    <th>Number of RidTransaction</th>
                </tr>
                </thead>
                <tbody>
                <g:each in="${ridInstanceList}" var="ridInstance">
                    <tr>
                        <td>
                            <a data-toggle="modal"
                               href="edit/${ridInstance.id}?dummy=${org.apache.commons.lang.math.RandomUtils.nextInt()}"
                               data-target="#myModal">
                                ${fieldValue(bean: ridInstance, field: "name")}
                            </a>
                        </td>
                        <td>${fieldValue(bean: ridInstance, field: "fullName")}</td>
                        <g:if test="${session.transType == "consultation"}">
                            <td>${ridInstance?.ridConsTransaction?.size()}</td>
                        </g:if>
                        <g:else>
                            <td>${ridInstance?.ridInsTransaction?.size()}</td>
                        </g:else>
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

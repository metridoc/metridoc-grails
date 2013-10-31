<%@ page import="metridoc.rid.RidSessionType" %>
<g:set var="entityName" value="${message(code: 'ridSessionType.label', default: 'RidSessionType')}"/>

<md:report>
    <r:require module="tableModule"/>
    <!--[if !IE]><!-->
    <r:external dir="css" file="floating_tables_for_admin_4.css" plugin="metridoc-rid"/>
    <!--<![endif]-->

    <div class="md-application-content">
        <g:render template="/ridAdminTransaction/toggle" plugin="metridoc-rid"/>

        <g:render template="/ridAdminTransaction/tabs" plugin="metridoc-rid"/>
        <g:render template="/ridAdminTransaction/modal" plugin="metridocRid"
                  model="[title: entityName + ' Create/Edit']"/>

        <div id="list-ridSessionType" class="content scaffold-list" role="main">
            <h1>
                <g:message code="default.list.label" args="[entityName]"/>
                <a data-tooltip="Creating" href="create?dummy=${org.apache.commons.lang.math.RandomUtils.nextInt()}"
                   data-target="#myModal" data-toggle="modal">
                    <i title="Create Session Type" class="icon-plus-sign-alt"></i>
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
                                      title="${message(code: 'ridSessionType.name.label', default: 'Name')}"/>

                    <g:sortableColumn property="inForm"
                                      title="${message(code: 'ridSessionType.inForm.label', default: 'In Form')}"/>

                    <g:sortableColumn property="ridLibraryUnit"
                                      title="${message(code: 'ridSessionType.ridLibraryUnit.label', default: 'Library Unit')}"/>

                    <th>Number of RidTransaction</th>
                </tr>
                </thead>
                <g:render template="/ridAdminBase/baseListWithLibUnit" plugin="metridoc-rid"/>
            </table>
            <g:if test="${ridInstanceTotal > 10}">
                <div class="pagination">
                    <g:paginate total="${ridInstanceTotal}"/>
                </div>
            </g:if>
        </div>
    </div>
</md:report>

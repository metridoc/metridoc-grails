<%@ page import="metridoc.rid.RidLibraryUnit" %>
<g:set var="entityName" value="${message(code: 'ridLibraryUnit.label', default: 'RidLibraryUnit')}"/>

<div id="create-ridLibraryUnit" class="content scaffold-create" role="main">
    <h1><g:message code="default.create.label" args="[entityName]"/></h1>

    <g:form class="form-horizontal" action="save" enctype="multipart/form-data" useToken="true">
        <div style="margin-top: 2em">
            <tmpl:form/>
        </div>
        <fieldset class="buttons">
            <g:submitButton name="create" class="btn btn-danger" style="float: right"
                            value="${message(code: 'default.button.create.label', default: 'Create')}"/>
        </fieldset>
    </g:form>
</div>


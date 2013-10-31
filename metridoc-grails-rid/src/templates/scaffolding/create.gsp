<%=packageName%>
<g:set var="entityName" value="\${message(code: '${domainClass.propertyName}.label', default: '${className}')}"/>

%{--<md:report>--}%
%{--<div class="md-application-content">--}%

<div id="create-${domainClass.propertyName}" class="content scaffold-create" role="main">
    <h1><g:message code="default.create.label" args="[entityName]"/></h1>
    <g:hasErrors bean="\${${propertyName}}">
        <ul class="errors" role="alert">
            <g:eachError bean="\${${propertyName}}" var="error">
                <li <g:if test="\${error in org.springframework.validation.FieldError}">data-field-id="\${error.field}"</g:if>><g:message
                        error="\${error}"/></li>
            </g:eachError>
        </ul>
    </g:hasErrors>

    <g:form class="form-horizontal" action="save" <%=multiPart ? ' enctype="multipart/form-data"' : '' %>>
%{--<fieldset class="form">--}%
    <div style="margin-top: 2em">
        <tmpl:form/>
    </div>
    %{--</fieldset>--}%
    <fieldset class="buttons">
        <g:submitButton name="create" class="btn btn-danger"
                        value="\${message(code: 'default.button.create.label', default: 'Create')}"/>
    </fieldset>
    </g:form>
</div>
%{--</div>--}%
%{--</md:report>--}%

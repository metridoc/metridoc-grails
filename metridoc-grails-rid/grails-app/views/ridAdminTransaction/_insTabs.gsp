<g:render
        template="/ridTransaction/tabLabel"
        plugin="metridocRid"
        model="[controllerName: controllerName,
                actionName: actionName,
                linkController: 'ridAdminLocation',
                linkAction: 'list',
                linkText: 'Location',
        ]"/>
<g:render
        template="/ridTransaction/tabLabel"
        plugin="metridocRid"
        model="[controllerName: controllerName,
                actionName: actionName,
                linkController: 'ridAdminSessionType',
                linkAction: 'list',
                linkText: 'Session Type',
        ]"/>
<g:render
        template="/ridTransaction/tabLabel"
        plugin="metridocRid"
        model="[controllerName: controllerName,
                actionName: actionName,
                linkController: 'ridAdminInstructionalMaterials',
                linkAction: 'list',
                linkText: 'Materials',
        ]"/>
<g:render
        template="/ridTransaction/tabLabel"
        plugin="metridocRid"
        model="[controllerName: controllerName,
                actionName: actionName,
                linkController: 'ridAdminAudience',
                linkAction: 'list',
                linkText: 'Audience',
        ]"/>
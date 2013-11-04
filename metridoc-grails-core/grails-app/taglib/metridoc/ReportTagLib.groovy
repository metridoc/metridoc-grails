/*
  *Copyright 2013 Trustees of the University of Pennsylvania. Licensed under the
  *	Educational Community License, Version 2.0 (the "License"); you may
  *	not use this file except in compliance with the License. You may
  *	obtain a copy of the License at
  *
  *http://www.osedu.org/licenses/ECL-2.0
  *
  *	Unless required by applicable law or agreed to in writing,
  *	software distributed under the License is distributed on an "AS IS"
  *	BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
  *	or implied. See the License for the specific language governing
  *	permissions and limitations under the License.  */

package metridoc

import org.apache.commons.lang.StringUtils
import org.apache.log4j.Level
import org.springframework.util.Assert

class ReportTagLib {
    static namespace = 'md'
    def logService

    def errorAlert = { attrs, body ->

        if (!attrs) {
            attrs = [:]
        }

        if (!attrs.containsKey("showAlertIf")) {
            attrs.showAlertIf = attrs.alertMessage
        }

        out << render(
                template: "/reports/errorAlert",
                plugin: "metridoc-core",
                model: attrs
        )
    }

    def report = { attrs, body ->
        def layoutInConfig = grailsApplication.config.metridoc.style.layout ? grailsApplication.config.metridoc.style.layout : "main"
        def layout = attrs.layout ? attrs.layout : layoutInConfig
        def model = [layout: layout, body: body.call()]

        model.hasModule = false
        if (attrs.module) {
            model.module = attrs.module
            model.hasModule = true
        }

        //make this work in legacy code
        if ("none" == attrs.module) {
            model.hasModule = false
        }

        out << render(
                template: "/reports/defaultReport",
                plugin: "metridoc-core",
                model: model
        )
    }

    def header = { attrs, body ->
        out << "<strong>${body()}</strong><hr/>"
    }

    def outputLogFile = { attrs, body ->
        def file
        if (attrs.filePath) {
            String path = attrs.filePath
            log.debug "outputting logs from ${path}"
            file = new File(path)
        } else {
            def fileBody = attrs.fileBody
            Assert.notNull(fileBody)
            file = fileBody
        }
        logService.renderLog(out, file)
    }

    def logMsg = { attrs, body ->
        if (attrs['level'] != null) {
            String logLevel =
                Level.toLevel(attrs['level'] as String).toString().toLowerCase()
            log."$logLevel"(body())
        } else {
            log.debug(body())
        }
    }

    def alerts = { attrs ->
        ["alert", "warning", "info", "message"].each {
            def singleMessage = flash."${it}"
            if (singleMessage) {
                flash["${it}s"] << singleMessage
            }
        }
        out << render(
                template: "/reports/alerts",
                plugin: "metridocCore"
        )
    }

    /**
     * Creates a save / cancel bootstrap modal
     *
     * @attr name name of the modal, optional, defaults to controllerName
     * @attr header header of the modal, required
     * @attr action action of the form, optional
     * @attr method method of the form, optional
     * @attr controller controller of the form, optional
     * @attr id id of the form, optional
     * @attr save the value assigned to the save submit button, optional, 'Save changes' by deafault
     * @attr formClass class value for the form, optional
     */
    def saveModal = { attrs, body ->
        Assert.isTrue(attrs.header != null && attrs.header != StringUtils.EMPTY, "header variable MUST be added")
        def model = [
                modalBody: body(),
                modalName: attrs.name ?: controllerName,
                modalHeader: attrs.header,
                modalFormAction: attrs.action,
                modalFormMethod: attrs.method,
                modalFormController: attrs.controller,
                modalFormId: attrs.id,
                modalSaveChanges: attrs.save ?: "Save changes",
                modalFormClass: attrs.formClass
        ]

        out << render(
                template: "/reports/saveModal",
                plugin: "metridocCore",
                model: model
        )
    }

    /**
     * creates a bootstrap button surrounded by the control group tag
     *
     * @attr type type of button, submit by default
     * @attr icon font awesome icon
     *
     * body is the name on the button
     */
    def cgButton = {attrs, body ->
        assert body : "a body for the button must be specified"
        def model = [:]
        model.body = body()
        if(attrs.icon) {
            model.icon = "<i class=\"${attrs.icon}\"></i>"
        }
        model.type = attrs.type ?: "submit"

        out << render(
                template: "/reports/button",
                plugin: "metridocCore",
                model: model
        )
    }

    /**
     * Works the same as g:form, but detects if the url has novalidate in it.  This will turn off client side
     * validation, very helpful for testing.  The javadoc from g:form is copied below
     *
     * General linking to controllers, actions etc. Examples:<br/>
     *
     * &lt;g:form action="myaction"&gt;...&lt;/g:form&gt;<br/>
     * &lt;g:form controller="myctrl" action="myaction"&gt;...&lt;/g:form&gt;<br/>
     *
     * @attr action the name of the action to use in the link, if not specified the default action will be linked
     * @attr controller the name of the controller to use in the link, if not specified the current controller will be linked
     * @attr id The id to use in the link
     * @attr url A map containing the action,controller,id etc.
     * @attr name A value to use for both the name and id attribute of the form tag
     * @attr useToken Set whether to send a token in the request to handle duplicate form submissions. See Handling Duplicate Form Submissions
     * @attr method the form method to use, either 'POST' or 'GET'; defaults to 'POST'
     */
    def form = {attrs, body ->
        boolean novalidate = params.containsKey("novalidate") ? params.getBoolean("novalidate") : false

        def rendered = g.form(attrs, body)
        if(novalidate) {
            rendered = rendered.toString().replaceFirst(">", " novalidate>")
        }

        out << rendered
    }
}

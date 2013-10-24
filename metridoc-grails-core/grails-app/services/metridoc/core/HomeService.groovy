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

package metridoc.core

import org.codehaus.groovy.grails.commons.GrailsClass
import org.codehaus.groovy.grails.commons.GrailsClassUtils
import org.codehaus.groovy.grails.web.mapping.LinkGenerator

class HomeService {

    static final HOME_DATA_FIELD = "homePage"
    def categories = [
            admin: new CategoryFeatures(iconName: "icon-cog", appName: "Administration", adminOnly: true),
            application: new CategoryFeatures(iconName: "icon-bar-chart", appName: "Available Applications", adminOnly: false)
    ]
    def grailsApplication
    LinkGenerator grailsLinkGenerator

    static class CategoryFeatures {
        String appName
        String iconName
        boolean adminOnly
        List<ControllerInfo> controllerInfo = []
    }

    static class ControllerInfo {
        String title
        String description
        String link
    }

    def addControllerData(GrailsClass controller) {
        def dataField = GrailsClassUtils.getStaticFieldValue(controller.clazz, HOME_DATA_FIELD)
        if (dataField != null) {
            def info = new ControllerInfo()
            info.title = dataField.getAt("title") ?: "${controller.naturalName}"
            info.description = dataField.getAt("description") ?: """No Description"""
            info.link = controller.name

            def category
            if (dataField["adminOnly"] != null) {
                category = categories.admin
            }
            else {
                category = categories.application
            }
            log.debug "Adding controller Data for ${controller.naturalName}, category ${category}"
            category.controllerInfo << info
        }
        else {
            log.debug "${controller.naturalName} doesn't have home page metadata"
        }
    }

    def addApplicationsAndCategories() {
        grailsApplication.controllerClasses.each { GrailsClass controller ->
            addControllerData(controller)
        }

        categories.application.controllerInfo = categories.application.controllerInfo.sort {it.title}
        categories.admin.controllerInfo = categories.admin.controllerInfo.sort {it.title}
    }
}
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

import org.codehaus.groovy.grails.commons.GrailsControllerClass

class ManageReportService {

    def pluginManager
    def grailsApplication
    def initAuthService

    Map getRoleMaps() {
        pluginManager.getGrailsPluginForClassName("ShiroGrailsPlugin").instance.roleMaps
    }

    Set<String> getRolesByController(String controllerName) {
        getRolesByControllerAndRoleMap(controllerName, roleMaps)
    }

    static Set<String> getRolesByControllerAndRoleMap(String controllerName, Map roleMaps) {
        def normRoleMap = getNormalizedRoleMapsHelper(roleMaps)
        (normRoleMap[controllerName] ?: []) as Set<String>
    }

    boolean isProtected(String controllerName) {
        def reportPermission = ManageReport.findByControllerName(controllerName)

        if (reportPermission) {
            if (reportPermission.roles) {
                return true
            }
            if (reportPermission.isProtected) {
                return true
            }
        }

        if (getRolesByController(controllerName)) {
            return true
        }
        def controller = grailsApplication.getArtefactByLogicalPropertyName("Controller", controllerName)
        return isControllerGrailsClassProtected(controller as GrailsControllerClass)
    }

    static boolean isControllerGrailsClassProtected(GrailsControllerClass clazz) {
        if (clazz.hasProperty("isProtected")) {
            return clazz.getPropertyValue("isProtected", Boolean)
        }

        return false
    }

    Map<String, Map<String, Object>> getControllerDetails() {
        def result = [:]
        grailsApplication.controllerClasses.each {
            def controllerName = it.logicalPropertyName
            def controllerResult = [:]
            controllerResult.roles = getRolesByController(controllerName)
            controllerResult.isProtected = isProtected(controllerName)
            controllerResult.controllerName = controllerName
            result[controllerName] = controllerResult
        }

        return result
    }

    static Map getNormalizedRoleMapsHelper(Map roleMaps) {

        def result = [:]

        roleMaps.each { String controllerName, Map roleMap ->
            def roles = [] as Set
            roleMap.each {
                roles.addAll(it.value)
            }
            result[controllerName] = roles
        }

        return result
    }

    void updateController(params, flash, String controllerName) {

        def roles = params.roles
        boolean isProtected = params.isProtected ? true : false
        if (roles == null) {
            roles = []
        }

        if (roles instanceof String) {
            roles = [roles]
        }

        if (controllerName) {
            def report = ManageReport.findByControllerName(controllerName)
            if (report == null) {
                report = new ManageReport(controllerName: controllerName)
            }

            def persistedRoles = report.roles
            if (persistedRoles) {
                persistedRoles.clear()
            }
            if (roles) {
                report.isProtected = true
            } else {
                report.isProtected = isProtected
            }

            roles.each { String roleName ->
                def role = ShiroRole.findByName(roleName)
                report.addToRoles(role)
            }

            if (report.save(flush: true)) {
                flash.info = "updated security for controller [$controllerName]"
            } else {
                log.error report.errors
                flash.alert = "errors occurred trying to save $controllerName"
            }

        }
        //make changes to the role map
        initAuthService.init()

    }
}

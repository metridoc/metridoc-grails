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

import org.apache.shiro.SecurityUtils
import org.apache.shiro.authc.AuthenticationException
import org.apache.shiro.authc.UsernamePasswordToken

class RestController {

    def restService
    static final DEFAULT_ACTION = "index"

    def index() {
        def controllerForward = params.controllerForward
        def actionForward = params.actionForward ?: DEFAULT_ACTION

        def authHeader = request.getHeader("authorization")
        if (!authHeader) {
            response.addHeader("WWW-Authenticate", "Basic realm=\"metridoc-core\"")
            response.sendError(401)
            return
        }
        def userPWBytes = authHeader.replace("Basic ", "").replace("=", "").decodeBase64()
        def userPW = new String(userPWBytes).split(":")
        if (userPW.length < 2) {
            response.addHeader("WWW-Authenticate", "Basic realm=\"metridoc-core\"")
            response.sendError(401)
            return
        }

        if (SecurityUtils.subject.principal) {
            if (!SecurityUtils.subject.principal.equals(userPW[0])) {
                response.addHeader("WWW-Authenticate", "Basic realm=\"metridoc-core\"")
                response.sendError(401)
                return
            }
        }

        try {
            def authToken = new UsernamePasswordToken(userPW[0], userPW[1])
            SecurityUtils.subject.login(authToken)

            def allRoles = ShiroRole.list()
            def userRoles = []
            for (role in allRoles) {
                if (SecurityUtils.subject.hasRole(role.name)) userRoles.add(role)
            }
            def controllerReport = ManageReport.findByControllerName("${controllerForward}")
            if (controllerReport == null) {
                controllerReport = new ManageReport(controllerName: controllerForward.capitalize())
            }
            def controllerRoles = controllerReport.getRoles()
            log.info controllerRoles
            if (userRoles?.size() != 0) {
                if (!controllerRoles || restService.hasCommonRoles(userRoles, controllerRoles)) {
                    def key = UUID.randomUUID().toString()
                    restService.addToRestCache(key, userPW[0])
                    params.put("restKey", key)
                    forward(controller: controllerForward, action: actionForward, params: params)
                } else {
                    response.sendError(403, "You do not have access to see this page")
                    return
                }
            } else if (!controllerRoles) {
                def key = UUID.randomUUID().toString()
                restService.addToRestCache(key, userPW[0])
                params.put("restKey", key)
                forward(controller: controllerForward, action: actionForward, params: params)
            } else {
                response.sendError(403, "You do not have access to see this page")
                return
            }
        } catch (AuthenticationException ex) {
            response.addHeader("WWW-Authenticate", "Basic realm=\"metridoc-core\"")
            response.sendError(401)
            return
        }
    }
}
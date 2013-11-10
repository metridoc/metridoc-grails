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

import org.apache.commons.lang.math.RandomUtils
import org.apache.shiro.SecurityUtils
import org.apache.shiro.authc.UsernamePasswordToken
import org.apache.shiro.crypto.hash.Sha256Hash
import org.codehaus.groovy.grails.web.servlet.mvc.GrailsParameterMap

class AuthService {

    def mailService
    def grailsApplication
    /*A table of Time when the ResetPassword link is generated corresponding to each ResetPassword ID*/
    def dateById = [:]
    public static final FIFTEEN_MINUTES = 1000 * 60 * 15
    //
    def resetableUserById = [:]

    void updateUser(ShiroUser shiroUserInstance, GrailsParameterMap params) {

        if (params.emailAddress) {
            shiroUserInstance.emailAddress = params.emailAddress
        }

        if (params.changePW) {
            def onProfilePage = params.controller == "profile"
            shiroUserInstance.validatePasswords = true
            if (onProfilePage) {
                shiroUserInstance.oldPassword = params.oldPassword
            }
            else {
                shiroUserInstance.oldPassword = params.password
                shiroUserInstance.passwordHash = shiroUserInstance.saltedHash(params.password)
            }
            shiroUserInstance.password = params.password
            shiroUserInstance.confirm = params.confirm
            def valid = shiroUserInstance.validate()
            if (valid) {
                shiroUserInstance.passwordHash = shiroUserInstance.saltedHash()
                shiroUserInstance.hashAgainstOldPassword = false
            }

            log.info "password and user details for ${shiroUserInstance.username} are changing"
        } else {
            log.info "user details for ${shiroUserInstance.username} are changing, password will remain the same"
        }
    }

    def addUserById(id, user) {
        resetableUserById[id] = user
    }

    def getUserById(id) {
        resetableUserById.remove(id)
    }

    def canReset(id) {
        canDoReset(id, new Date().getTime())
    }

    private canDoReset(id, now) {
        def date = dateById.remove(id)

        if (date) {
            def validTime = now < (date.time + FIFTEEN_MINUTES)
            def dateNotNull = date != null
            def canReset = dateNotNull && validTime

            return canReset
        }

        return false
    }

    def addResetLink() {
        def id = RandomUtils.nextInt()
        dateById[id] = new Date()
        return id
    }

    def sendResetPasswordEmail(String emailAddress) {
        def id = addResetLink()
        def link = grailsApplication.config.grails.serverURL + "/auth/doResetPassword?id=${id}"
        def user = ShiroUser.findByEmailAddress(emailAddress)
        if (user) {
            mailService.sendMail {
                to "${emailAddress}"
                subject "Reset Password"
                body "Go here to reset your password: ${link}"
            }
            addUserById(id, user)
        }
    }

    boolean isPasswordValid(String password) {
        return (password.length() >= 5 && password.length() <= 15)
    }

    boolean isPasswordMatch(String password, String confirm) {
        return password == confirm
    }


    def newResetLink(user) {
        def id = addResetLink()
        addUserById(id, user)
        def link = grailsApplication.config.grails.serverURL + "/auth/doResetPassword?id=${id}"
        return link
    }

    def resetPassword(user, password, confirm) {
        def passwordHash = new Sha256Hash(password).toHex()
        log.info "reseting password for ${user.username}"
        ShiroUser.withTransaction {
            def userToUpdate = ShiroUser.findByUsername(user.username)
            userToUpdate.password = password
            userToUpdate.confirm = confirm
            userToUpdate.passwordHash = passwordHash
            userToUpdate.save(flush: true)
        }
        def authToken = new UsernamePasswordToken(user.username, password as String)
        SecurityUtils.subject.login(authToken)
    }
}

class AuthChangeStatus {
    String errorMessage
    boolean success = true
}
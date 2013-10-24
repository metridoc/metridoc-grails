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

class UserService {

    static final ROLE_ANONYMOUS = "ROLE_ANONYMOUS"

    def addRolesToUser(userInstance, roles) {

        def rolesToAdd = [ROLE_ANONYMOUS] as Set //there by default
        if (roles) {
            rolesToAdd.addAll roles
        }

        if (userInstance.roles == null) {
            userInstance.roles = []
        }

        rolesToAdd.each {
            def role = ShiroRole.findByName(it)
            userInstance.roles.add(role)
        }
    }
}

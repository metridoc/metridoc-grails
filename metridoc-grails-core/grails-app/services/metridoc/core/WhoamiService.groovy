package metridoc.core

import org.apache.shiro.SecurityUtils

class WhoamiService {

    def restService
    static final ANONYMOUS = "anonymous"

    String getUsername(Map params) {
        if (params.get("restKey")) {
            return restService.getFromRestCache(params.get("restKey"))
        }
        return SecurityUtils.subject.principal ?: ANONYMOUS
    }

    Map getUserData(Map params) {
        def result = [:]
        result.username = getUsername(params)
        result.roles = getRolesForUser()

        return result
    }

    List getRolesForUser() {
        def roles = []

        ShiroRole.list().each {ShiroRole role ->
            if(SecurityUtils.subject.hasRole(role.name)) {
                roles << role.name
            }
        }

        return roles
    }
}

package metridoc.core

class RoleService {

    boolean roleAttachedToUsers(long roleId) {
        def role = ShiroRole.get(roleId)
        role.users?.size() > 0
    }

    boolean roleAttachedToLdapMapping(long roleId) {
        boolean attached = false
        LdapRoleMapping.list().each {mapping ->
            if(mapping.roles.find {role -> role.id == roleId}) {
                attached = true
            }
        }

        return attached
    }

    void deletedRole(long roleId) {
        assert !roleAttachedToLdapMapping(roleId)
        assert !roleAttachedToUsers(roleId)
        ShiroRole.get(roleId).delete()
    }
}

package com.auditlog

import com.security.Role
import com.security.User
import com.security.UserRole

class UserService {

    User register(UserCO userCO) {
        if (!userCO.validate()) {
            return null
        }
        User user = new User()
        user.username = userCO.username
        user.password = userCO.password
        user.name = userCO.name
        user.save(flush: true)
        Role role = Role.findByAuthority("ROLE_USER")
        UserRole.create(user, role)
        return user
    }
}

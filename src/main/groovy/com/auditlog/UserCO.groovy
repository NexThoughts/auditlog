package com.auditlog

import com.security.User
import grails.validation.Validateable

class UserCO implements Validateable {
    String username
    String password
    String confirmPassword
    String name

    static constraints = {
        username(nullable: false, blank: false, email: true, validator: { val, obj ->
            if (User.findByUsername(val)) {
                return "user.already.exists"
            } else {
                return true
            }
        })
        password(nullable: false, blank: false, minLength: 6)
        confirmPassword(validator: { val, obj ->
            if (!val.equals(obj.confirmPassword)) {
                return false
            }
        })
    }
}

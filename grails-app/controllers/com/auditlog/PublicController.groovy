package com.auditlog

import com.security.User
import grails.plugin.springsecurity.annotation.Secured

class PublicController {

    def springSecurityService
    def userService

    @Secured(['permitAll'])
    def register() {
        render(view: "/public/register", model: [userCO: new UserCO()])
    }

    @Secured(['permitAll'])
    def saveRegister(UserCO userCO) {
        if (userCO.validate()) {
            User user = userService.register(userCO)
            if (user) {
                springSecurityService.reauthenticate(userCO.username)
                flash.msg = "You have been succesfully registerd. Please complete your profile."
                redirect(controller: "public", action: 'dashboard')
            } else {
                render(view: "/public/register", model: [userCO: userCO])
            }
        }
    }

    @Secured(['ROLE_USER', 'ROLE_ADMIN'])
    def dashboard() {
        redirect(controller: 'product', action: 'index')
    }
}

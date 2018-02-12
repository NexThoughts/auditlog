package com.auditlog

import com.security.User
import grails.plugin.springsecurity.annotation.Secured

@Secured(['ROLE_USER', 'ROLE_ADMIN'])
class ProductController {
    def userService
    def springSecurityService

    def index() {
        render(view: 'index', model: [products: Product.findAll()])
    }

    def updateName() {
        User user = springSecurityService.currentUser as User
        String uniqueid = params.pk
        String newValue = params.value
        Product product = Product.findByUniqueid(uniqueid)
        String oldValue = product.name
        product.name = newValue
        product.save(flush: true)
        userService.createAuditLog(user?.username, "${Product.class}", uniqueid, "name", oldValue, newValue)
        render 200
    }

    def auditLog() {
        List<AuditLog> auditLogs = AuditLog.createCriteria().list() {}
        List<String> data = []

        auditLogs.each {
            data.add(userService.fetchAuditData(it))
        }
        render data
    }
}

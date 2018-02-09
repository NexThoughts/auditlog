package com.auditlog

import grails.plugin.springsecurity.annotation.Secured

@Secured(['ROLE_USER', 'ROLE_ADMIN'])
class ProductController {

    def index() {
        render(view: 'index', model: [products: Product.findAll()])
    }

    def updateName() {
        println params
        String uniqueid = params.pk
        String newValue = params.value
        Product product = Product.findByUniqueid(uniqueid)
        product.name = newValue
        product.save(flush: true)
        render 200
    }
}

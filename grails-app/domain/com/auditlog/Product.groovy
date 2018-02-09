package com.auditlog

import com.security.User

class Product {
    String uniqueid = UUID.randomUUID().toString()
    String name
    Date lastUpdated
    Date dateCreated

    static constraints = {
        name nullable: false, blank: true
    }
}

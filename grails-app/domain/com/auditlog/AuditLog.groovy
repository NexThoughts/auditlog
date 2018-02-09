package com.auditlog

class AuditLog {
    String auditor
    String domainName
    String instanceId
    String prop
    String olderValue
    String newValue
    Date lastUpdated
    Date dateCreated

    static constraints = {
        domainName nullable: true, blank: false
        auditor nullable: true, blank: false
        prop nullable: true, blank: false
        olderValue nullable: true, blank: true
        newValue nullable: true, blank: true
        instanceId nullable: true, blank: false
    }
}

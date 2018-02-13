package com.auditlog

import com.security.Role
import com.security.User
import com.security.UserRole

class UserService {

    def grailsApplication

    User register(UserCO userCO) {
        if (!userCO.validate()) {
            return null
        }
        User user = new User()
        user.username = userCO.username
        user.password = userCO.password
        user.name = userCO.name
        user.accountAddress = createAccount()
        user.save(flush: true)
        Role role = Role.findByAuthority("ROLE_USER")
        UserRole.create(user, role)
        return user
    }

    def createAuditLog(String username, String domainName, String uniqueId, String propName, String oldValue, String newValue) {
        AuditLog auditLog = new AuditLog()
        auditLog.auditor = username
        auditLog.domainName = domainName
        auditLog.instanceId = uniqueId
        auditLog.prop = propName
        auditLog.olderValue = oldValue
        auditLog.newValue = newValue
        auditLog.save(flush: true)
        auditLog.address = registerAuditLog(auditLog, uniqueId)
        auditLog.save(flush: true)
    }

    def createAccount() {
        String serverUrl = grailsApplication.config.blocklittle.url
        BlockLittleApi api = new BlockLittleApi("vijay@nexthoughts.com", "123456", serverUrl)
        String inputs = "{'password':'nextdefault'}"
        return api.createAccount(inputs)
    }

    String registerAuditLog(AuditLog auditLog, String uniqueId) {
        String serverUrl = grailsApplication.config.blocklittle.url
        BlockLittleApi api = new BlockLittleApi("vijay@nexthoughts.com", "123456", serverUrl)
        String inputs = "audit=${auditLog}&account=account&id=${uniqueId}"
        return api.registerAuditLog(inputs)
    }

    String fetchAuditData(AuditLog auditLog) {
        if (auditLog?.address) {
            String serverUrl = grailsApplication.config.blocklittle.url
            BlockLittleApi api = new BlockLittleApi("vijay@nexthoughts.com", "123456", serverUrl)
            String inputs = "address=${auditLog?.address}&account=account"
            return api.fetchAuditData(inputs)
        } else {
            return ""
        }
    }
}

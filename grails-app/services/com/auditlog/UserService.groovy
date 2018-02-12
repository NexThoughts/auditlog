package com.auditlog

import com.security.Role
import com.security.User
import com.security.UserRole
import groovy.json.JsonSlurper

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

    def createAccount() {
        String serverUrl = grailsApplication.config.grails.serverURL
        URL url = new URL("${serverUrl}accounts/create/")
        HttpURLConnection connection = (HttpURLConnection) url.openConnection()
        connection.setDoOutput(true)
        connection.setRequestMethod("POST")
        connection.setRequestProperty("Accept", "application/json")
        connection.setRequestProperty("Content-Type", "application/json")
        connection.setRequestProperty("X-Auth-Token", generateAccessToken())
        String inputs = "{'password':'nextdefault'}"
        OutputStream outputStream = connection.outputStream
        outputStream.write(inputs.bytes)
        outputStream.flush()

        if (connection.getResponseCode() != 200) {
            throw new RuntimeException("Failed : HTTP error code : " + connection.responseCode)
        }

        BufferedReader br = new BufferedReader(new InputStreamReader((connection.inputStream)))
        String output = ""
        String data = ""
        while ((output = br.readLine()) != null) {
            System.out.println(output)
            data += output
        }
        connection.disconnect()
        def jsonSlurper = new JsonSlurper()
        def object = jsonSlurper.parseText(data)
        return object.address
    }

    String generateAccessToken() {
        String serverUrl = grailsApplication.config.grails.serverURL
        URL url = new URL("${serverUrl}login")

        HttpURLConnection connection = (HttpURLConnection) url.openConnection()
        connection.setDoOutput(true)
        connection.setRequestMethod("POST")
        connection.setRequestProperty("Accept", "application/json")
        connection.setRequestProperty("Content-Type", "application/json")

        String inputs = "{\"username\":\"vijay@nexthoughts.com\", \"password\":\"123456\"}"

        OutputStream outputStream = connection.outputStream
        outputStream.write(inputs.bytes)
        outputStream.flush()

        if (connection.getResponseCode() != 200) {
            throw new RuntimeException("Failed : HTTP error code : " + connection.responseCode)
        }

        BufferedReader br = new BufferedReader(new InputStreamReader((connection.inputStream)))
        String output = ""
        String data = ""
        while ((output = br.readLine()) != null) {
            data += output
        }
        println "???????????????????? ********8 " + output
        println "???????????????????? ********8 " + data
        connection.disconnect()
        def jsonSlurper = new JsonSlurper()
        def object = jsonSlurper.parseText(data)
        return object.access_token as String
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
        auditLog.address = registerAuditLog(auditLog, uniqueId, generateAccessToken())
        auditLog.save(flush: true)
    }

    String registerAuditLog(AuditLog auditLog, String uniqueId, String access_token) {
        String serverUrl = grailsApplication.config.grails.serverURL
        URL url = new URL("${serverUrl}auditLog/deploy")
        HttpURLConnection connection = (HttpURLConnection) url.openConnection()
        connection.setDoOutput(true)
        connection.setRequestMethod("GET")
        connection.setRequestProperty("User-Agent", "Mozilla/5.0")
        connection.setRequestProperty("Accept", "application/json")
        connection.setRequestProperty("X-Auth-Token", access_token)

//        String inputs = "{\"audit\":\"${auditLog}\",\"account\":\"account\", \"id\":\"${uniqueId}\"}"

        String inputs = "audit=${auditLog}&account=account&id=${uniqueId}"
        DataOutputStream outputStream = new DataOutputStream(connection.outputStream)
        outputStream.writeBytes(inputs)
        outputStream.flush()
        outputStream.close()

        if (connection.getResponseCode() != 200) {
            throw new RuntimeException("Failed : HTTP error code : " + connection.responseCode)
        }

        BufferedReader br = new BufferedReader(new InputStreamReader((connection.inputStream)))
        String output = ""
        StringBuffer response = new StringBuffer()
        while ((output = br.readLine()) != null) {
            System.out.println(output)
            response.append(output)
        }
        br.close()
        connection.disconnect()
        def jsonSlurper = new JsonSlurper()
        def object = jsonSlurper.parseText(response.toString())
        return object.address
    }

    String fetchAuditData(AuditLog auditLog) {
        String serverUrl = grailsApplication.config.grails.serverURL
        URL url = new URL("${serverUrl}auditLog/read")
        HttpURLConnection connection = (HttpURLConnection) url.openConnection()
        connection.setDoOutput(true)
        connection.setRequestMethod("GET")
        connection.setRequestProperty("Accept", "application/json")
        connection.setRequestProperty("X-Auth-Token", generateAccessToken())

        String inputs = "address=${auditLog?.address}&account=account"
        DataOutputStream outputStream = new DataOutputStream(connection.outputStream)
        outputStream.writeBytes(inputs)
        outputStream.flush()
        outputStream.close()

        if (connection.getResponseCode() != 200) {
            throw new RuntimeException("Failed : HTTP error code : " + connection.responseCode)
        }

        BufferedReader br = new BufferedReader(new InputStreamReader((connection.inputStream)))
        String output = ""
        StringBuffer data = new StringBuffer()
        while ((output = br.readLine()) != null) {
            System.out.println(output)
            data.append(output)
        }
        connection.disconnect()
        def jsonSlurper = new JsonSlurper()
        def object = jsonSlurper.parseText(data.toString())
        return object.data
    }
}

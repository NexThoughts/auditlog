package com.auditlog

import groovy.json.JsonSlurper

class BlockLittleApi {
    String url = ""
    JsonSlurper jsonSlurper = null
    String username = ""
    String password = ""

    BlockLittleApi() {

    }

    BlockLittleApi(String username, String password, String url) {
        this.url = url
        this.username = username
        this.password = password
        this.jsonSlurper = new JsonSlurper()
    }

    String registerAuditLog(String inputs) {
        String sourceUrl = "${this.url}auditLog/deploy"
        String data = apiCall(sourceUrl, inputs)
        def object = jsonSlurper.parseText(data)
        return object.address
    }

    String fetchAuditData(String inputs) {
        String sourceUrl = "${this.url}auditLog/read"
        String data = apiCall(sourceUrl, inputs)
        def object = jsonSlurper.parseText(data.toString())
        return object.data
    }

    def createAccount(String inputs) {
        String sourceUrl = "${this.url}accounts/create/"
        String data = apiCall(sourceUrl, inputs)
        def object = jsonSlurper.parseText(data)
        return object.address
    }

    String generateAccessToken() {
        URL url = new URL("${this.url}login")

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
        connection.disconnect()
        def jsonSlurper = new JsonSlurper()
        def object = jsonSlurper.parseText(data)
        return object.access_token as String
    }

    String apiCall(String sourceUrl, String inputs) {
        URL url = new URL(sourceUrl)
        HttpURLConnection connection = (HttpURLConnection) url.openConnection()
        connection.setDoOutput(true)
        connection.setRequestMethod("GET")
        connection.setRequestProperty("User-Agent", "Mozilla/5.0")
        connection.setRequestProperty("Accept", "application/json")
        connection.setRequestProperty("X-Auth-Token", generateAccessToken())

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
        return response.toString()
    }
}

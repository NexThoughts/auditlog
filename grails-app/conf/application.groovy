dataSource {
    pooled = true
    driverClassName = "com.mysql.jdbc.Driver"
    dialect = 'org.hibernate.dialect.MySQL5InnoDBDialect'
}

environments {
    development {
        dataSource {
            dbCreate = "update"
            url = "jdbc:mysql://localhost:3306/auditlog?autoreconnect=true"
            username = "root"
            logSql = false
            password = "nextdefault"
        }
        server.port = 8090
        blocklittle.url = "http://dev.ethereum.com:8080/api/"
    }
    test {
        dataSource {
            dbCreate = "create-drop"
            url = "jdbc:mysql://localhost:3306/auditlog?autoreconnect=true"
            username = "root"
            logSql = false
            password = "nextdefault"
        }
    }
    production {
        grails.serverURL = "http://audit.blocklittle.com"
        dataSource {
            username = "block_little"
            password = "Gh6vL8Enykm9qZ"
            dbCreate = "update"
            url = "jdbc:mysql://localhost:3306/auditlog_prod?autoreconnect=true&useUnicode=yes&characterEncoding=UTF-8"
            pooled = true
            properties {
                maxActive = -1
                minEvictableIdleTimeMillis = 1800000
                timeBetweenEvictionRunsMillis = 1800000
                numTestsPerEvictionRun = 3
                testOnBorrow = true
                testWhileIdle = true
                testOnReturn = true
                validationQuery = "SELECT 1"
            }
        }
        server.port = 8090
        blocklittle.url = "http://blocklittle.com/api/"
    }
}

grails.gorm.default.constraints = {
    '*'(nullable: true)
}

// Added by the Spring Security Core plugin:
grails.plugin.springsecurity.userLookup.userDomainClassName = 'com.security.User'
grails.plugin.springsecurity.userLookup.authorityJoinClassName = 'com.security.UserRole'
grails.plugin.springsecurity.authority.className = 'com.security.Role'
grails.plugin.springsecurity.logout.postOnly = false
grails.plugin.springsecurity.auth.loginFormUrl = '/'
grails.plugin.springsecurity.failureHandler.defaultFailureUrl = '/?login_error=1'
grails.plugin.springsecurity.successHandler.defaultTargetUrl = '/public/dashboard/'
grails.plugin.springsecurity.logout.afterLogoutUrl = '/'
grails.plugin.springsecurity.controllerAnnotations.staticRules = [
        [pattern: '/', access: ['permitAll']],
        [pattern: '/error', access: ['permitAll']],
        [pattern: '/index', access: ['permitAll']],
        [pattern: '/index.gsp', access: ['permitAll']],
        [pattern: '/shutdown', access: ['permitAll']],
        [pattern: '/assets/**', access: ['permitAll']],
        [pattern: '/**/js/**', access: ['permitAll']],
        [pattern: '/**/css/**', access: ['permitAll']],
        [pattern: '/**/images/**', access: ['permitAll']],
        [pattern: '/**/fonts/**', access: ['permitAll']],
        [pattern: '/**/favicon.ico', access: ['permitAll']]
]

grails.plugin.springsecurity.filterChain.chainMap = [
        [pattern: '/assets/**', filters: 'none'],
        [pattern: '/**/js/**', filters: 'none'],
        [pattern: '/**/css/**', filters: 'none'],
        [pattern: '/**/images/**', filters: 'none'],
        [pattern: '/**/favicon.ico', filters: 'none'],
        [pattern: '/**', filters: 'JOINED_FILTERS']
]


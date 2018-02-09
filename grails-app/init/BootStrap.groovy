import com.auditlog.Product
import com.security.Role
import com.security.User
import com.security.UserRole

class BootStrap {

    def init = { servletContext ->
        Role userRole = Role.findByAuthority("ROLE_USER") ?: new Role(authority: "ROLE_USER").save(flush: true)
        Role adminRole = Role.findByAuthority("ROLE_ADMIN") ?: new Role(authority: "ROLE_ADMIN").save(flush: true)

        if (!User.count()) {
            User user = new User(username: "vijay@nexthoughts.com", password: '123456', name: "Vijay Shukla").save(flush: true)
            User admin = new User(username: "admin@nexthoughts.com", password: 'auditdefault', name: "Audit Log").save(flush: true)

            UserRole.create(user, userRole)
            UserRole.create(admin, adminRole)
        }
        if (!Product.count())
            (1..10).each {
                new Product(name: "Car-$it").save(flush: true)
            }

    }
    def destroy = {
    }
}

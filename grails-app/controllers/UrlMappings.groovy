class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?(.$format)?" {
            constraints {
                // apply constraints here
            }
        }

        "/"(view: "/index")
        "/register"(controller: 'public', action: 'register', method: 'GET')
        "/register"(controller: 'public', action: 'saveRegister', method: 'POST')
        "500"(view: '/error')
        "404"(view: '/notFound')
    }
}

package com.example.plugins

import io.ktor.server.plugins.swagger.*
import io.ktor.server.routing.*
import io.ktor.server.plugins.defaultheaders.*
import io.ktor.server.application.*

fun Application.configureHTTP() {
    routing {
        swaggerUI(path = "openapi", swaggerFile = "swagger/openapi.yaml")
    }
    install(DefaultHeaders) {
        header("X-Engine", "Ktor") // will send this header with each response
    }
}

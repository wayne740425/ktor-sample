package com.example

import com.example.dao.DatabaseFactory
import com.example.plugins.*
import io.ktor.server.application.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.cors.routing.*

fun main(args: Array<String>): Unit = EngineMain.main(args)

fun Application.module() {
    DatabaseFactory.init(environment.config)
    install(CORS) {
        anyHost()
    }
    configureMonitoring()
    configureHTTP()
    configureSecurity()
    configureSerialization()
    configureRouting()
}

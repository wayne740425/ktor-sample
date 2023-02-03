package com.example.routes

import com.example.dao.dao
import com.example.models.Customer
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.customerRouting() {
    route("/customer") {
        get {
            val allCustomers = dao.allCustomers()
            if (allCustomers.isNotEmpty()) {
                call.respond(allCustomers)
            } else {
                call.respondText("No customers found", status = HttpStatusCode.OK)
            }
        }
        get("{id?}") {
            val id = call.parameters["id"]?.toInt() ?: return@get call.respondText(
                "Missing id",
                status = HttpStatusCode.BadRequest
            )
            val customer = dao.customer(id) ?: return@get call.respondText(
                "No customer with id $id",
                status = HttpStatusCode.NotFound
            )
            call.respond(customer)
        }
        post {
            val customer = call.receive<Customer>()
            dao.addCustomer(customer.firstName, customer.lastName, customer.email)
            call.respondText("Customer stored correctly", status = HttpStatusCode.Created)
        }
        delete("{id?}") {
            val id = call.parameters["id"]?.toInt() ?: return@delete call.respond(HttpStatusCode.BadRequest)
            if (dao.deleteCustomer(id)) {
                call.respondText("Customer removed correctly", status = HttpStatusCode.Accepted)
            } else {
                call.respondText("Not Found", status = HttpStatusCode.NotFound)
            }
        }
    }
}
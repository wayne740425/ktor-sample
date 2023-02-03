package com.example.models

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table

@Serializable
data class Customer(val id: Int, val firstName: String, val lastName: String, val email: String)

object CustomerTable : Table() {
    val id = integer("id").autoIncrement()
    val firstName = varchar("first_name", 128)
    val lastName = varchar("last_name", 128)
    val email = varchar("email", 320)

    override val primaryKey = PrimaryKey(id)
}

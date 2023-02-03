package com.example.dao

import com.example.dao.DatabaseFactory.dbQuery
import com.example.models.Customer
import com.example.models.CustomerTable
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class DAOCustomerImpl : DAOCustomer {
    private fun resultRowToCustomer(row: ResultRow) = Customer(
        id = row[CustomerTable.id],
        firstName = row[CustomerTable.firstName],
        lastName = row[CustomerTable.lastName],
        email = row[CustomerTable.email],
    )

    override suspend fun allCustomers(): List<Customer> = dbQuery {
        CustomerTable.selectAll().map { resultRowToCustomer(it) }
    }

    override suspend fun customer(id: Int): Customer? = dbQuery {
        CustomerTable.select { CustomerTable.id eq id }.map { resultRowToCustomer(it) }.singleOrNull()
    }

    override suspend fun addCustomer(firstName: String, lastName: String, email: String): Customer? = dbQuery {
        val insertStatement = CustomerTable.insert {
            it[this.firstName] = firstName
            it[this.lastName] = lastName
            it[this.email] = email
        }
        insertStatement.resultedValues?.singleOrNull()?.let { resultRowToCustomer(it) }
    }

    override suspend fun updateCustomer(customer: Customer): Boolean = dbQuery {
        CustomerTable.update({ CustomerTable.id eq customer.id }) {
            it[this.firstName] = customer.firstName
            it[this.lastName] = customer.lastName
            it[this.email] = customer.email
        } > 0
    }

    override suspend fun deleteCustomer(id: Int): Boolean = dbQuery {
        CustomerTable.deleteWhere { CustomerTable.id eq id } > 0
    }
}

val dao: DAOCustomer = DAOCustomerImpl().apply {
    runBlocking {
        if (allCustomers().isEmpty()) {
            addCustomer(
                firstName = "Wayne",
                lastName = "Chen",
                email = "wayne@okmaster.com",
            )
        }
    }
}
package com.example.dao

import com.example.models.Customer

interface DAOCustomer {
    suspend fun allCustomers(): List<Customer>
    suspend fun customer(id: Int): Customer?
    suspend fun addCustomer(firstName: String, lastName: String, email: String): Customer?
    suspend fun updateCustomer(customer: Customer): Boolean
    suspend fun deleteCustomer(id: Int): Boolean
}
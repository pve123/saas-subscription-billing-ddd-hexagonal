package io.github.hslee.saasbilling.customer.application.port.`in`

import io.github.hslee.saasbilling.customer.domain.Customer

interface CreateCustomerUseCase {
    fun createCustomer(customer: Customer) : Customer
}
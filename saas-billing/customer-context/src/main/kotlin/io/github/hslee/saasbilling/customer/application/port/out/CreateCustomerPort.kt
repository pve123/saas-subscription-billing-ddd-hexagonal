package io.github.hslee.saasbilling.customer.application.port.out

import io.github.hslee.saasbilling.customer.domain.Customer

interface CreateCustomerPort {

    fun save(customer: Customer) : Customer

}
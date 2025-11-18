package io.github.hslee.saasbilling.customer.application.service

import io.github.hslee.saasbilling.customer.application.port.`in`.CreateCustomerUseCase
import io.github.hslee.saasbilling.customer.application.port.out.CreateCustomerPort
import io.github.hslee.saasbilling.customer.domain.Customer


class CustomerService (
    private val createCustomerPort: CreateCustomerPort,
) : CreateCustomerUseCase{




    override fun createCustomer(customer: Customer): Customer {
        val customer = createCustomerPort.save(customer)
        return customer
    }


}
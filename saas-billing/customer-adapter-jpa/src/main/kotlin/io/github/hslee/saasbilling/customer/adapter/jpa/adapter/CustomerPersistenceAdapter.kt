package io.github.hslee.saasbilling.customer.adapter.jpa.adapter

import io.github.hslee.saasbilling.customer.adapter.jpa.mapper.CustomerPersistenceMapper
import io.github.hslee.saasbilling.customer.adapter.jpa.repository.CustomerRepository
import io.github.hslee.saasbilling.customer.application.port.out.CreateCustomerPort
import io.github.hslee.saasbilling.customer.domain.Customer

class CustomerPersistenceAdapter(
    private val customerRepository: CustomerRepository,
    private val customerPersistenceMapper: CustomerPersistenceMapper
) : CreateCustomerPort {


    override fun save(customer: Customer): Customer {
        val customerJpaEntity = customerPersistenceMapper.toJpaEntity(customer)
        val saveCustomerJpaEntity = customerRepository.save(customerJpaEntity)
        val customer = customerPersistenceMapper.toDomain(saveCustomerJpaEntity)
        return customer
    }
}
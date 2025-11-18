package io.github.hslee.saasbilling.customer.adapter.jpa.mapper

import io.github.hslee.saasbilling.customer.adapter.jpa.entity.CustomerJpaEntity
import io.github.hslee.saasbilling.customer.domain.Customer
import org.mapstruct.Mapper


@Mapper(componentModel = "spring")
interface CustomerPersistenceMapper {

    fun toJpaEntity(customer: Customer) : CustomerJpaEntity
    fun toDomain(customerJpaEntity: CustomerJpaEntity) : Customer
}
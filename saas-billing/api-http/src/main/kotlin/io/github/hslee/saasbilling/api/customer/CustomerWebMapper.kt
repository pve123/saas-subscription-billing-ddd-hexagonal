package io.github.hslee.saasbilling.api.customer

import io.github.hslee.saasbilling.api.customer.dto.CreateCustomerRequest
import io.github.hslee.saasbilling.api.customer.dto.CreateCustomerResponse
import io.github.hslee.saasbilling.customer.domain.Customer
import org.mapstruct.Mapper


@Mapper(componentModel = "spring")
interface CustomerWebMapper {

    fun toDomain(createCustomerRequest: CreateCustomerRequest) : Customer
    fun toCreateCustomerMember(customer: Customer) : CreateCustomerResponse
}
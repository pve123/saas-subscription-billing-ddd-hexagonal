package io.github.hslee.saasbilling.customer.adapter.jpa.repository

import io.github.hslee.saasbilling.customer.adapter.jpa.entity.CustomerJpaEntity
import org.springframework.data.jpa.repository.JpaRepository

interface CustomerRepository : JpaRepository<CustomerJpaEntity, String> {
}
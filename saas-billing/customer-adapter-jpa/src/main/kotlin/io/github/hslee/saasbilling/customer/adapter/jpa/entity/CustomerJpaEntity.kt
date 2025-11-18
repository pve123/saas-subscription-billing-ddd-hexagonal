package io.github.hslee.saasbilling.customer.adapter.jpa.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table


@Entity
@Table(name = "customers")
data class CustomerJpaEntity(

    @Id
    @Column(nullable = false, updatable = false, length = 26)
    var id: String,

    @Column(nullable = false, updatable = false, length = 255)
    var email: String,

    @Column(nullable = false, updatable = false,length = 255)
    var name: String
)

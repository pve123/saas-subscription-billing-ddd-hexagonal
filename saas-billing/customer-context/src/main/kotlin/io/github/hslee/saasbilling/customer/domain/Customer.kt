package io.github.hslee.saasbilling.customer.domain

import java.time.LocalDateTime

data class Customer(
    val id: String,
    val email: String,
    val name: String,
    val createdAt: LocalDateTime,

)

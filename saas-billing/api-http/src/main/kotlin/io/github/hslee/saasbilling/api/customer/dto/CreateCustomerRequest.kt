package io.github.hslee.saasbilling.api.customer.dto

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class CreateCustomerRequest(

    @field:NotBlank(message = "이메일은 필수입니다.")
    @field:Size(max = 255, message = "이메일은 최대 255자까지 허용됩니다.")
    @field:Email(message = "올바른 이메일 형식이어야 합니다.")
    @param:Schema(description = "이메일", example = "test@example.com")
    val email: String,

    @field:NotBlank(message = "이름은 필수입니다.")
    @field:Size(max = 255, message = "이름은 최대 255자까지 허용됩니다.")
    @param:Schema(description = "이름", example = "이호성")
    val name: String
)

package io.github.hslee.saasbilling.api.customer

import io.github.hslee.saasbilling.api.customer.dto.CreateCustomerRequest
import io.github.hslee.saasbilling.api.customer.dto.CreateCustomerResponse
import io.github.hslee.saasbilling.customer.application.port.`in`.CreateCustomerUseCase
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*


@Tag(name = "고객", description = "고객 생성")
@RestController
@RequestMapping("/v1/customers")
class CustomerController(
    private val createCustomerUseCase: CreateCustomerUseCase,
    private val customerWebMapper: CustomerWebMapper
) {
    @Operation(
        summary = "고객 생성",
        requestBody = io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = CreateCustomerRequest::class)
                )
            ]
        ),
        responses = [
            ApiResponse(
                responseCode = "201",
                description = "고객 생성 완료",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = CreateCustomerResponse::class)
                    )
                ]
            )
        ]
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createCustomer(
            @Valid @RequestBody createCustomerRequest: CreateCustomerRequest
    ): CreateCustomerResponse {
        val customer = customerWebMapper.toDomain(createCustomerRequest)
        val createdCustomer = createCustomerUseCase.createCustomer(customer)
        return customerWebMapper.toCreateCustomerMember(createdCustomer)
    }
}
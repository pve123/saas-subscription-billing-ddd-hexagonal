package io.github.hslee.saasbilling.api.config

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import org.springframework.context.annotation.Configuration


@Configuration
@OpenAPIDefinition(
    info = Info(
        title = "Saas 빌링 결제 시스템",
        description = "SaaS Subscription Billing service using DDD and Hexagonal architecture",
        version = "v1"
    )
)
class SwaggerConfig {

}
package io.github.hslee.saasbilling

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SaasBillingApplication

fun main(args: Array<String>) {
	runApplication<SaasBillingApplication>(*args)
}

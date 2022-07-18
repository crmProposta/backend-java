package com.proposta.crm

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CrmApplication

fun main(args: Array<String>) {
	runApplication<CrmApplication>(*args)
}

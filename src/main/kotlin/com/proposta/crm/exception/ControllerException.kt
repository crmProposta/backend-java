package com.proposta.crm.exception

open class ControllerException(val code: String, override val message: String) : RuntimeException(message) {
}
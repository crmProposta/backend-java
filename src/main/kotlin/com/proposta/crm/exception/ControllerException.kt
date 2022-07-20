package com.proposta.crm.exception

class ControllerException(val code: String, override val message: String) : RuntimeException(message) {
}
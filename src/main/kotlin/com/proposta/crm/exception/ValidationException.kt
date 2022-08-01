package com.proposta.crm.exception

class ValidationException(override val message: String) : ControllerException("validationError", message) {
}
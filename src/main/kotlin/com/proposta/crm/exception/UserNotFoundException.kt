package com.proposta.crm.exception

class UserNotFoundException(override val message: String) : ControllerException("userNotFound", message) {
}
package com.proposta.crm.model

import io.swagger.v3.oas.annotations.media.Schema

@Schema(name = "user roles", enumAsRef = true)
enum class RoleEnum(code: Int) {
    CUSTOMER(5),
    ATTENDANT(4),
    COORDINATOR(3),
    ADMIN(2),
    MASTER(1);

    object Code {
        const val CUSTOMER = "ROLE_CUSTOMER"
        const val ATTENDANT = "ROLE_ATTENDANT"
        const val COORDINATOR = "ROLE_COORDINATOR"
        const val ADMIN = "ROLE_ADMIN"
        const val MASTER = "ROLE_MASTER"
    }
}
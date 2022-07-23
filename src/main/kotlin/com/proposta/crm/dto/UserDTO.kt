package com.proposta.crm.dto

import com.proposta.crm.model.RoleEnum

data class UserDTO(
    val id: Long?,
    val username: String,
    val enabled: Boolean,
    val roles: Set<RoleEnum>,
)
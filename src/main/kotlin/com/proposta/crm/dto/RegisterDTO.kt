package com.proposta.crm.dto

import com.proposta.crm.model.RoleEnum
import io.swagger.v3.oas.annotations.Parameter

data class RegisterDTO(
    val loginLabel: String,
    val password: String,
    val roles: List<RoleEnum>
)
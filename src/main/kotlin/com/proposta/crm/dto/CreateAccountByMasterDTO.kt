package com.proposta.crm.dto

import com.proposta.crm.model.RoleEnum
import org.hibernate.validator.constraints.Length
import javax.validation.constraints.Min
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull
import kotlin.math.max
import kotlin.math.min

data class CreateAccountByMasterDTO(
    @NotNull
    val loginLabel: String,
    @Length(min = 8, max = 25)
    val password: String,
    val enabled: Boolean = false,
    val confirmPassword: String,
    @NotEmpty
    val roles: List<RoleEnum> = emptyList()
)

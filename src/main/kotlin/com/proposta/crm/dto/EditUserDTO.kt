package com.proposta.crm.dto

import com.proposta.crm.model.RoleEnum
import org.jetbrains.annotations.NotNull
import javax.validation.constraints.NotEmpty

data class EditUserDTO(
    @NotNull
    val id: Long,
    @NotNull
    val loginLabel: String,
    val newPassword: String?,
    val isToChangePassword: Boolean,
    val enabled: Boolean = false,
    @NotEmpty
    val roles: List<RoleEnum> = emptyList()
)

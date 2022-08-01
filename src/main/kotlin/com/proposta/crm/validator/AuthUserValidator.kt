package com.proposta.crm.validator

import com.proposta.crm.dto.EditUserDTO
import com.proposta.crm.dto.LoginDTO
import com.proposta.crm.dto.RegisterDTO
import com.proposta.crm.entity.AuthUser
import com.proposta.crm.exception.IncorrectCredentialsException
import org.springframework.security.core.userdetails.UsernameNotFoundException

object AuthUserValidator {
    fun checkIfUserIsEnabled(user: AuthUser) {
        if (!user.enabled) throw UsernameNotFoundException("User is disabled")
    }

    fun validateRegisterParameters(registerDTO: RegisterDTO) {
        if (registerDTO.password.length !in 8..25)
            throw IncorrectCredentialsException("password must be greater than 8 and shorter than 25")

        if (registerDTO.roles.isEmpty())
            throw IncorrectCredentialsException("Need to pass at least one role")
    }

    fun validateEditParameters(editUserDTO: EditUserDTO) {
        if (editUserDTO.isToChangePassword &&
            !editUserDTO.newPassword.isNullOrBlank() &&
            editUserDTO.newPassword.length !in 8..25
        )
            throw IncorrectCredentialsException("password must be greater than 8 and shorter than 25")

        if (editUserDTO.roles.isEmpty())
            throw IncorrectCredentialsException("Need to pass at least one role")
    }
}
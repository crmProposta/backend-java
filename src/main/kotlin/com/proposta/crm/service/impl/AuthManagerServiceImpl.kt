package com.proposta.crm.service.impl

import com.proposta.crm.dto.LoginDTO
import com.proposta.crm.service.AuthManagerService
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.stereotype.Service

@Service
class AuthManagerServiceImpl(
    private var authenticationManager: AuthenticationManager
) : AuthManagerService {

    override fun checkIfPasswordIsCorrect(loginDTO: LoginDTO) {
        val usernamePasswordAuthenticationToken =
            UsernamePasswordAuthenticationToken(loginDTO.loginLabel, loginDTO.password)
        authenticationManager.authenticate(usernamePasswordAuthenticationToken)
    }
}
package com.proposta.crm.service

import com.proposta.crm.dto.JwtTokensDTO
import com.proposta.crm.dto.LoginDTO

interface AuthUserService {

    fun registerUser(loginDTO: LoginDTO)

    fun loginUser(loginDTO: LoginDTO): JwtTokensDTO

    fun generateNewAccessToken(accessToken: String): JwtTokensDTO

    fun deleteUser(deleteDTO: LoginDTO)
}
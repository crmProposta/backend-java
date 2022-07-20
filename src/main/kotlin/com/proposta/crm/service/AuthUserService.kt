package com.proposta.crm.service

import com.proposta.crm.dto.CreateAccountByMasterDTO
import com.proposta.crm.dto.JwtTokensDTO
import com.proposta.crm.dto.LoginDTO
import com.proposta.crm.dto.RegisterDTO

interface AuthUserService {

    fun registerUser(registerDTO: RegisterDTO)

    fun loginUser(loginDTO: LoginDTO): JwtTokensDTO

    fun generateNewAccessToken(refreshToken: String): JwtTokensDTO

    fun deleteUser(deleteDTO: LoginDTO)
    fun createAccountByMasterAccount(user: CreateAccountByMasterDTO)
}
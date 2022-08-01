package com.proposta.crm.service

import com.proposta.crm.dto.*

interface AuthUserService {

    fun registerUser(registerDTO: RegisterDTO)

    fun loginUser(loginDTO: LoginDTO): JwtTokensDTO

    fun generateNewAccessToken(refreshToken: String): JwtTokensDTO

    fun deleteUser(deleteDTO: LoginDTO)
    fun createAccountByMasterAccount(user: CreateAccountByMasterDTO)
    fun listAccountByMasterAccount(): List<UserDTO>
    fun enableAccount(id: Int): Boolean
    fun disableAccount(id: Int): Boolean
    fun getAccountById(id: Long): UserDTO
    fun editAccount(formUser: EditUserDTO): UserDTO
}
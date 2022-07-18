package com.proposta.crm.service

import com.proposta.crm.dto.LoginDTO

interface AuthManagerService {

    fun checkIfPasswordIsCorrect(loginDTO: LoginDTO)
}
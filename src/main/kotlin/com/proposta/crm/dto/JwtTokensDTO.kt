package com.proposta.crm.dto

data class JwtTokensDTO(
    val accessToken: String = "",
    val refreshToken: String = ""
)

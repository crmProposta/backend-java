package com.proposta.crm.entity

import javax.persistence.FetchType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.OneToOne

data class UserRefreshToken(
    @Id
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AuthUser.id")
    val id: Long,
    val refreshToken: String,
)
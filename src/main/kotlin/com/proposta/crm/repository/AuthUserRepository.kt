package com.proposta.crm.repository

import com.proposta.crm.entity.AuthUser
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AuthUserRepository : JpaRepository<AuthUser, Long> {

    fun findByUsername(username: String) : AuthUser?

    fun findByUsernameAndEnabledTrue(username: String) : AuthUser?

    fun findByRefreshToken(refreshToken: String): AuthUser?
}
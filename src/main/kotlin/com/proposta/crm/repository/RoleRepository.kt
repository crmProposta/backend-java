package com.proposta.crm.repository

import com.proposta.crm.entity.Role
import com.proposta.crm.model.RoleEnum
import org.springframework.data.jpa.repository.JpaRepository

interface RoleRepository : JpaRepository<Role, RoleEnum> {

    fun findByRoleCode(roleEnum: RoleEnum) : Role
    fun existsByRoleCode(roleEnum: RoleEnum) : Boolean
}
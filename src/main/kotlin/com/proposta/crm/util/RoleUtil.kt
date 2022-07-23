package com.proposta.crm.util

import com.proposta.crm.entity.Role
import com.proposta.crm.model.RoleEnum

object RoleUtil {

    fun roleRepositoryToRoleEnum(roles: Collection<Role>): Set<RoleEnum> {
        return roles.map { it.roleCode }.toSet()
    }

    fun roleRepositoryToRoleEnum(role: Role): RoleEnum {
        return role.roleCode
    }
}
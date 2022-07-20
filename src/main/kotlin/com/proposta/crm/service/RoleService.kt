package com.proposta.crm.service

import com.proposta.crm.entity.Role
import com.proposta.crm.model.RoleEnum

interface RoleService {

    fun findRoleByRoleEnum(roleEnum: RoleEnum): Role

    fun findRolesByRoleEnum(listOfRoleEnum: List<RoleEnum>): List<Role>

}
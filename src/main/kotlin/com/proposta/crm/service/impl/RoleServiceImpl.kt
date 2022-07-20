package com.proposta.crm.service.impl

import com.proposta.crm.entity.Role
import com.proposta.crm.model.RoleEnum
import com.proposta.crm.repository.RoleRepository
import com.proposta.crm.service.RoleService
import org.springframework.stereotype.Service

@Service
class RoleServiceImpl(
    val roleRepository: RoleRepository
    ): RoleService{
    override fun findRoleByRoleEnum(roleEnum: RoleEnum): Role {
        return roleRepository.findByRoleCode(roleEnum)
    }

    override fun findRolesByRoleEnum(listOfRoleEnum: List<RoleEnum>): List<Role> {
        val roles = mutableListOf<Role>()
        listOfRoleEnum.forEach {
             roles.add(
                 roleRepository.findByRoleCode(roleEnum = it)
             )
        }
        return roles;
    }
}
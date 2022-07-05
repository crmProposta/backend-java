package com.proposta.crm.config.clr

import com.proposta.crm.entity.Role
import com.proposta.crm.model.RoleEnum
import com.proposta.crm.repository.RoleRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.core.Ordered
import org.springframework.stereotype.Component

@Component
class UpdateRoleTableInitialization(
    var roleRepository: RoleRepository
) : CommandLineRunner, Ordered {

    private val logger: Logger = LoggerFactory.getLogger(UpdateRoleTableInitialization::class.java)

    override fun run(vararg args: String?) {
        logger.info("Updating table: Role")
        RoleEnum.values().forEach {
            val existRole = roleRepository.existsByRoleCode(it)
            if (!existRole) addRole(it)
        }
        logger.info("Table Role updated")
    }

    fun addRole(roleEnum: RoleEnum) {
        logger.info("Adding a new Role: " + roleEnum.name)
        roleRepository.save(Role(roleEnum))
    }

    override fun getOrder(): Int {
        return 1
    }
}
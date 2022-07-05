package com.proposta.crm.config.clr

import com.proposta.crm.entity.AuthUser
import com.proposta.crm.entity.Role
import com.proposta.crm.model.RoleEnum
import com.proposta.crm.repository.AuthUserRepository
import com.proposta.crm.repository.RoleRepository
import com.sun.istack.logging.Logger
import org.springframework.stereotype.Component
import org.springframework.boot.CommandLineRunner
import org.springframework.security.crypto.password.PasswordEncoder

@Component
class AddAdminIfNotExists(
    private val authUserRepository: AuthUserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val roleRepository: RoleRepository,
) : CommandLineRunner {

    private val logger: Logger = Logger.getLogger(AddAdminIfNotExists::class.java)

    override fun run(vararg args: String?) {
        val user = authUserRepository.findByUsername("admin")
        if (user == null) {
            logger.info("Creating master user")
            createUserMaster()
            logger.info("Master user created")
        } else {
            logger.info("Updating master roles")
            updateUserRoles(user)
            logger.info("Updated master roles")
        }
    }

    private fun updateUserRoles(user: AuthUser) {

        val roles = roleRepository.findAll().toSet()
        val rolesMissing = roles.filter { role -> !user.roles.contains(role) }
        if (rolesMissing.isNotEmpty())
            authUserRepository.save(
                user.copy(roles = roles)
            )
    }

    private fun createUserMaster() {
        val roles = roleRepository.findAll()
        println(roles.toString())
        authUserRepository.save(
            AuthUser(
                null,
                "admin",
                passwordEncoder.encode("admin123456"),
                roles = emptySet()
            )
        )

        with(authUserRepository.findByUsername("admin")) {
            roles.addAll(roles)
            authUserRepository.save(this!!)
        }

    }

    private fun getAllRoles(): Set<Role> = RoleEnum.values()
        .map { Role(it) }
        .toSet()
}
package com.proposta.crm.service.impl

import com.proposta.crm.dto.CreateAccountByMasterDTO
import com.proposta.crm.dto.JwtTokensDTO
import com.proposta.crm.dto.LoginDTO
import com.proposta.crm.dto.RegisterDTO
import com.proposta.crm.entity.AuthUser
import com.proposta.crm.entity.Role
import com.proposta.crm.exception.ControllerException
import com.proposta.crm.exception.IncorrectCredentialsException
import com.proposta.crm.repository.AuthUserRepository
import com.proposta.crm.service.AuthManagerService
import com.proposta.crm.service.AuthUserService
import com.proposta.crm.service.RoleService
import com.proposta.crm.util.JwtUtil
import com.proposta.crm.validator.AuthUserValidator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthUserServiceImpl : AuthUserService {

    @Autowired
    private lateinit var authUserRepository: AuthUserRepository

    @Autowired
    private lateinit var userDetailsService: UserDetailsService

    @Autowired
    private lateinit var authManagerService: AuthManagerService

    @Autowired
    private lateinit var bCryptPasswordEncoder: PasswordEncoder

    @Autowired
    private lateinit var roleService: RoleService

    override fun registerUser(registerDTO: RegisterDTO) {
        AuthUserValidator.validateRegisterParameters(registerDTO)

        val passwordEncoded = bCryptPasswordEncoder.encode(registerDTO.password)
        val roles = registerDTO.roles.map { Role(it) }.toSet()

        val user = AuthUser(null, registerDTO.loginLabel, passwordEncoded, roles = roles)
        authUserRepository.save(user)

    }

    override fun loginUser(loginDTO: LoginDTO): JwtTokensDTO {
        val user = authUserRepository.findByUsername(loginDTO.loginLabel)
            ?: throw IncorrectCredentialsException("user doesn't exists")

        AuthUserValidator.checkIfUserIsEnabled(user)
        authManagerService.checkIfPasswordIsCorrect(loginDTO)

        val userDetails = userDetailsService.loadUserByUsername(user.username)
        val tokens: JwtTokensDTO = JwtUtil.generateAuthTokens(user)

        saveRefreshToken(user, tokens.refreshToken)

        return tokens
    }

    override fun generateNewAccessToken(refreshToken: String): JwtTokensDTO {
        val user = authUserRepository.findByRefreshToken(refreshToken)
            ?: throw IncorrectCredentialsException("User not found")

        val userDetails = userDetailsService.loadUserByUsername(user.username)
        val tokens = JwtUtil.generateAuthTokens(user)
        saveRefreshToken(user, tokens.refreshToken)

        return tokens
    }

    private fun saveRefreshToken(user: AuthUser, refreshToken: String) {
        authUserRepository.save(user.copy(refreshToken = refreshToken))
    }

    override fun deleteUser(deleteDTO: LoginDTO) {
        val user = authUserRepository.findByUsername(deleteDTO.loginLabel)
            ?: throw IncorrectCredentialsException("User not found")

        AuthUserValidator.checkIfUserIsEnabled(user)
        authManagerService.checkIfPasswordIsCorrect(deleteDTO)

        disableUser(user)
    }

    override fun createAccountByMasterAccount(user: CreateAccountByMasterDTO) {
        val userRoles = roleService.findRolesByRoleEnum(user.roles)
        val passwordEncoded = bCryptPasswordEncoder.encode(user.password)

        if (userRoles.isEmpty()) throw ControllerException("roleEmpty","Specify at least one role for the user")
        val authUser: AuthUser = AuthUser(
            null,
            user.loginLabel,
            passwordEncoded,
            user.enabled,
            "",
            userRoles.toSet()
        )

        authUserRepository.save(authUser)
    }

    private fun disableUser(user: AuthUser) {
        user.enabled = false
        authUserRepository.save(user)
    }


}
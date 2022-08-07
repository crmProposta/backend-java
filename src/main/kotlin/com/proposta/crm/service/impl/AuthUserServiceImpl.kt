package com.proposta.crm.service.impl

import com.proposta.crm.dto.*
import com.proposta.crm.entity.AuthUser
import com.proposta.crm.entity.Role
import com.proposta.crm.exception.ControllerException
import com.proposta.crm.exception.IncorrectCredentialsException
import com.proposta.crm.exception.UserNotFoundException
import com.proposta.crm.exception.ValidationException
import com.proposta.crm.model.RoleEnum
import com.proposta.crm.repository.AuthUserRepository
import com.proposta.crm.service.AuthManagerService
import com.proposta.crm.service.AuthUserService
import com.proposta.crm.service.RoleService
import com.proposta.crm.util.JwtUtil
import com.proposta.crm.util.RoleUtil
import com.proposta.crm.validator.AuthUserValidator
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthUserServiceImpl(
    private val authUserRepository: AuthUserRepository,
    private val userDetailsService: UserDetailsService,
    private val authManagerService: AuthManagerService,
    private val bCryptPasswordEncoder: PasswordEncoder,
    private val roleService: RoleService,
) : AuthUserService {
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
        val user =
            authUserRepository.findByRefreshToken(refreshToken) ?: throw IncorrectCredentialsException("User not found")

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

        if (userRoles.isEmpty()) throw ControllerException("roleEmpty", "Specify at least one role for the user")
        val authUser: AuthUser = AuthUser(
            null, user.loginLabel, passwordEncoded, user.enabled, "", userRoles.toSet()
        )

        authUserRepository.save(authUser)
    }

    private fun disableUser(user: AuthUser) {
        user.enabled = false
        authUserRepository.save(user)
    }

    override fun listAccountByMasterAccount(): List<UserDTO> {
        val roles = authUserRepository.findAllByOrderByIdAsc().map { user ->
            UserDTO(user.id, user.username, user.enabled, RoleUtil.roleRepositoryToRoleEnum(user.roles))
        }.toList()

        return roles
    }

    override fun enableAccount(id: Int): Boolean {
        val authUser = authUserRepository.findById(id.toLong()).orElseThrow { UserNotFoundException("user not found") }

        if (RoleUtil.roleRepositoryToRoleEnum(authUser.roles)
                .contains(RoleEnum.MASTER)
        ) throw ValidationException("cannot disable master's user")
        if (authUser.enabled) throw ValidationException("user already enabled")

        authUser.enabled = true;

        authUserRepository.save(authUser)

        return true;
    }

    override fun disableAccount(id: Int): Boolean {
        val authUser = authUserRepository.findById(id.toLong()).orElseThrow { UserNotFoundException("user not found") }

        if (RoleUtil.roleRepositoryToRoleEnum(authUser.roles)
                .contains(RoleEnum.MASTER)
        ) throw ValidationException("cannot disable master's user")
        if (!authUser.enabled) throw ValidationException("user already disabled")

        authUser.enabled = false;

        authUserRepository.save(authUser)

        return true;
    }

    override fun getAccountById(id: Long): UserDTO {
        val account = authUserRepository.findById(id).orElseThrow { UserNotFoundException("ID not valid") }

        return UserDTO(
            account.id,
            account.username,
            account.enabled,
            RoleUtil.roleRepositoryToRoleEnum(account.roles)
        )
    }

    override fun editAccount(formUser: EditUserDTO): UserDTO {
        val account: AuthUser =
            authUserRepository.findById(formUser.id).orElseThrow { UserNotFoundException("ID not valid") }

        if (RoleUtil.roleRepositoryToRoleEnum(account.roles)
                .contains(RoleEnum.MASTER)
        ) throw ValidationException("cannot edit master's user")

        AuthUserValidator.validateEditParameters(formUser)

        val accountEdited = account.copy(
            username = formUser.loginLabel,
            password = getOldOrNewPassword(account, formUser.isToChangePassword, formUser.password),
            enabled = formUser.enabled,
            roles = roleService.findRolesByRoleEnum(formUser.roles).toSet()
        )

        authUserRepository.save(accountEdited)
        return UserDTO(
            accountEdited.id,
            accountEdited.username,
            accountEdited.enabled,
            RoleUtil.roleRepositoryToRoleEnum(accountEdited.roles)
        )
    }

    private fun getOldOrNewPassword(account: AuthUser, isToChangePassword: Boolean, newPassword: String?): String {
        return if (isToChangePassword && (newPassword != null))
            bCryptPasswordEncoder.encode(newPassword) else account.password
    }


}
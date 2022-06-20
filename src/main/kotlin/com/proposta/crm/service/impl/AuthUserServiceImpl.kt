package com.proposta.crm.service.impl

import com.proposta.crm.dto.JwtTokensDTO
import com.proposta.crm.dto.LoginDTO
import com.proposta.crm.entity.AuthUser
import com.proposta.crm.exception.IncorrectCredentialsException
import com.proposta.crm.repository.AuthUserRepository
import com.proposta.crm.service.AuthUserService
import com.proposta.crm.util.Constants
import com.proposta.crm.util.JwtUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthUserServiceImpl : AuthUserService{

    @Autowired
    private lateinit var authUserRepository: AuthUserRepository

    @Autowired
    private lateinit var userDetailsService: UserDetailsService

    @Autowired
    private lateinit  var authenticationManager: AuthenticationManager

    private final val bCryptPasswordEncoder: PasswordEncoder = BCryptPasswordEncoder()

    override fun registerUser(loginDTO: LoginDTO) {
        if (loginDTO.password.length !in 8..25)
            throw IncorrectCredentialsException("password must be greater than 8 and shorter than 25")

        val passwordEncoded = bCryptPasswordEncoder.encode(loginDTO.password)
        val user = AuthUser(null, loginDTO.loginLabel, passwordEncoded)
        authUserRepository.save(user);

    }

    override fun loginUser(loginDTO: LoginDTO): JwtTokensDTO {
        val user = authUserRepository.findByUsername(loginDTO.loginLabel)
            ?: throw IncorrectCredentialsException("user doesn't exists")

        if (!user.enabled) throw UsernameNotFoundException("User is disabled")

        val usernamePasswordAuthenticationToken = UsernamePasswordAuthenticationToken(loginDTO.loginLabel, loginDTO.password)
        authenticationManager.authenticate(usernamePasswordAuthenticationToken)
        /*
        val encryptedPassword = bCryptPasswordEncoder.encode(loginDTO.password)

        if (user.password != encryptedPassword) {
            throw IncorrectCredentialsException("incorrect password")
        }

         */

        val userDetails = userDetailsService.loadUserByUsername(user.username)

        val tokens =  JwtTokensDTO(
            JwtUtil.generateAccessToken(userDetails, Constants.URL),
            JwtUtil.generateRefreshToken(userDetails, Constants.URL)
        )
        val userWithRefreshToken = user.copy(refreshToken = tokens.refreshToken);
        authUserRepository.save(userWithRefreshToken)

        return tokens
    }

    override fun generateNewAccessToken(refreshToken: String): JwtTokensDTO {
        val user = authUserRepository.findByRefreshToken(refreshToken)
            ?: throw IncorrectCredentialsException("User not found")

        val userDetails = userDetailsService.loadUserByUsername(user.username)
        val tokens = JwtTokensDTO(
            JwtUtil.generateAccessToken(userDetails, Constants.URL),
            JwtUtil.generateRefreshToken(userDetails, Constants.URL)
        )

        authUserRepository.save(user.copy(refreshToken = tokens.refreshToken))
        return tokens;
    }

    override fun deleteUser(deleteDTO: LoginDTO) {
        val user = authUserRepository.findByUsername(deleteDTO.loginLabel)
            ?: throw IncorrectCredentialsException("User not found")

        if (user.enabled) throw RuntimeException("User already disabled")

        val usernamePasswordAuthenticationToken = UsernamePasswordAuthenticationToken(deleteDTO.loginLabel, deleteDTO.password)
        val result = authenticationManager.authenticate(usernamePasswordAuthenticationToken)

        user.enabled = false
        authUserRepository.save(user)

    }


}
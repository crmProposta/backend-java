package com.proposta.crm.service.impl

/*
import com.proposta.crm.ApplicationConfigTest
import com.proposta.crm.dto.LoginDTO
import com.proposta.crm.entity.AuthUser
import com.proposta.crm.repository.AuthUserRepository
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import java.util.*


@ExtendWith(MockitoExtension::class)
@DisplayName("AuthUserServiceTest")
class AuthUserServiceImplTest : ApplicationConfigTest() {

    @MockBean
    private lateinit var authUserRepository: AuthUserRepository

    @MockBean
    private lateinit var userDetailsService: UserDetailsService

    @MockBean
    private lateinit var authenticationManager: AuthenticationManager

    @MockBean
    private lateinit var bCryptPasswordEncoder: PasswordEncoder

    @Autowired
    private lateinit var authUserServiceImpl: AuthUserServiceImpl

    @Test
    fun `should disable user`() {
        //given
        val deleteDTO = LoginDTO(
            "user",
            "user123456"
        )
        val username = deleteDTO.loginLabel

        val authUser = Mockito.mock(AuthUser::class.java)
        Mockito.`when`(authUserRepository.findByUsername(ArgumentMatchers.eq(username))).thenReturn(authUser)
        authUserServiceImpl.deleteUser(deleteDTO)
        authUserServiceImpl.deleteUser(authUser)
    }
}
 */
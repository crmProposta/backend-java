package com.proposta.crm

import com.proposta.crm.entity.AuthUser
import com.proposta.crm.entity.Role
import com.proposta.crm.model.RoleEnum
import com.proposta.crm.repository.AuthUserRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestPropertySource

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = [CrmApplication::class])
@TestPropertySource(
    locations = ["classpath:application-test.properties"]
)
class AuthUserServiceTest(
) {
    @Autowired
    lateinit var authUserRepository: AuthUserRepository
    val testUser = AuthUser(
        null,
        "test",
        "12345678",
        roles = setOf(Role(RoleEnum.ADMIN))
    )

    @BeforeAll
    fun `register user`() { authUserRepository.save(testUser) }

    fun `validate if user is registered`() {

        val user = authUserRepository.findByUsername("test")
        assertNotNull(user)

        user?.let {
            assertEquals(user.username, testUser.username)
        }


    }
}
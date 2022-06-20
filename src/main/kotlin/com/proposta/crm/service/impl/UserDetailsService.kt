package com.proposta.crm.service.impl

import com.proposta.crm.repository.AuthUserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserDetailsService : org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    private lateinit var authUserRepository: AuthUserRepository

    override fun loadUserByUsername(username: String?): UserDetails {
        val user = authUserRepository.findByUsername(username!!)
            ?: throw UsernameNotFoundException("user doesn't exists")

        //TODO(Add authorities to DB and get here)
        val authorities: Collection<SimpleGrantedAuthority> = mutableListOf()

        return User(username, user.password, authorities)
    }
}
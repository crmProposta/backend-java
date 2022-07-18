package com.proposta.crm.config

import com.proposta.crm.security.filter.CorsFilter
import com.proposta.crm.security.filter.TokenAuthorizationFilter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig : WebSecurityConfigurerAdapter(){

    @Autowired
    lateinit var userDetailsService: UserDetailsService

    override fun configure(http: HttpSecurity?) {
        http?.let {
            //Disable default security of spring security
            it.csrf().disable()
            it.httpBasic().disable()
            it.formLogin().disable()

            it.authorizeRequests().antMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
            it.authorizeRequests().antMatchers("/auth/**").permitAll()
            it.authorizeRequests().anyRequest().authenticated()
            it.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

            it.headers().frameOptions().disable()
            it.cors().disable()

            it.userDetailsService(userDetailsService)

            it.addFilterBefore(TokenAuthorizationFilter(userDetailsService), UsernamePasswordAuthenticationFilter::class.java)
            it.addFilterBefore(CorsFilter(), TokenAuthorizationFilter::class.java)

        }
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    @Bean
    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
    }

}
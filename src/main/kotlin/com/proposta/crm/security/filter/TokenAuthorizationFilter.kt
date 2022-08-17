package com.proposta.crm.security.filter

import com.auth0.jwt.interfaces.DecodedJWT
import com.fasterxml.jackson.databind.ObjectMapper
import com.proposta.crm.handler.ResponseHandler
import com.proposta.crm.util.Constants.PREFIX_AUTHORIZATION
import com.proposta.crm.util.JwtUtil
import org.springframework.http.HttpHeaders.AUTHORIZATION
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


class TokenAuthorizationFilter(private val userDetailsService: UserDetailsService) : OncePerRequestFilter() {


    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val servletPath = request.servletPath
        val authorizationHeader = request.getHeader(AUTHORIZATION)

        if (isOnAuthPath(servletPath) || !authorizationHeaderIsValid(authorizationHeader)) {
            filterChain.doFilter(request, response)
            return
        }

        try {
            val decodedJWT: DecodedJWT = JwtUtil.checkAndGetDecodedJWT(authorizationHeader)
            val username: String = decodedJWT.subject
            authenticateUser(username)
            filterChain.doFilter(request, response)
        } catch (e: Exception) {
            showErrorOnResponse(response, e.localizedMessage)
        }
    }

    private fun isOnAuthPath(servletPath: String) = servletPath.startsWith("/auth")

    private fun authorizationHeaderIsValid(authorizationHeader: String?): Boolean {
        return authorizationHeader != null && authorizationHeader.startsWith(PREFIX_AUTHORIZATION)
    }

    private fun authenticateUser(username: String) {
        val userDetails = userDetailsService.loadUserByUsername(username)
        SecurityContextHolder.getContext().authentication =
            UsernamePasswordAuthenticationToken(userDetails.username, null, userDetails.authorities)
    }

    private fun showErrorOnResponse(response: HttpServletResponse, errorMessage: String) {
        response.status = HttpStatus.FORBIDDEN.value()
        response.contentType = APPLICATION_JSON_VALUE
        ObjectMapper().writeValue(
            response.outputStream,
            ResponseHandler.Error("tokenError", errorMessage)
        )
    }

}
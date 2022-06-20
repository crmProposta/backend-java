package com.proposta.crm.util

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import com.proposta.crm.util.Constants.PREFIX_AUTHORIZATION
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.util.*

@Component
object JwtUtil {
    private final const val MINUTES = 60 * 1000;
    private final const val ACCESS_TOKEN_VALIDITY = 30L * MINUTES;
    private final const val REFRESH_TOKEN_VALIDITY = 60L * MINUTES;

    private final val signatureAlgorithm: Algorithm = Algorithm.HMAC256(Constants.SECRET)

    fun getDecodedJWTFromToken(token: String): DecodedJWT {
        val verifier = JWT.require(signatureAlgorithm).build()
        return verifier.verify(token)
    }

    fun generateAccessToken(user: UserDetails, url: String): String =  JWT.create()
        .withSubject(user.username)
        .withExpiresAt(Date(System.currentTimeMillis() + ACCESS_TOKEN_VALIDITY))
        .withIssuer(url)
        .sign(signatureAlgorithm)

    fun generateRefreshToken(user: UserDetails, url: String): String = JWT.create()
        .withSubject(user.username)
        .withExpiresAt(Date(System.currentTimeMillis() + REFRESH_TOKEN_VALIDITY))
        .withIssuer(url)
        .sign(signatureAlgorithm)

    fun checkAndGetDecodedJWT(authorizationHeader: String): DecodedJWT {
        val accessToken = authorizationHeader.substring(PREFIX_AUTHORIZATION.length)
        return getDecodedJWTFromToken(accessToken);
    }


}
package com.proposta.crm.controller

import com.proposta.crm.dto.JwtTokensDTO
import com.proposta.crm.dto.LoginDTO
import com.proposta.crm.dto.RegisterDTO
import com.proposta.crm.handler.ResponseHandler
import com.proposta.crm.model.ResponseStatusEnum
import com.proposta.crm.service.AuthUserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController {

    @Autowired
    lateinit var userService: AuthUserService

    @PostMapping("/login")
    fun login(@RequestBody loginDTO: LoginDTO) : ResponseEntity<ResponseHandler<Any>> {
        val tokens: JwtTokensDTO = userService.loginUser(loginDTO);

        return ResponseEntity.ok(
            ResponseHandler.Success(ResponseStatusEnum.SUCCESS, "success", tokens)
        )
    }

    @PostMapping("/refreshToken")
    fun refreshToken(@RequestBody tokens: JwtTokensDTO): ResponseEntity<ResponseHandler<Any>> {
        val tokens: JwtTokensDTO = userService.generateNewAccessToken(tokens.refreshToken)

        return ResponseEntity.ok(
            ResponseHandler.Success(ResponseStatusEnum.SUCCESS, "success", tokens)
        )
    }

    @PostMapping("/register")
    fun register(@RequestBody registerDTO: RegisterDTO): ResponseEntity<ResponseHandler<Any>> {
        userService.registerUser(registerDTO)

        return ResponseEntity.ok(
            ResponseHandler.Success(ResponseStatusEnum.SUCCESS, "success", null)
        )
    }

    @DeleteMapping("/delete")
    fun delete(@RequestBody deleteDTO: LoginDTO) : ResponseEntity<ResponseHandler<Any>> {
        userService.deleteUser(deleteDTO)

        return ResponseEntity.ok(
            ResponseHandler.Success(ResponseStatusEnum.SUCCESS, "success", null)
        )
    }
}
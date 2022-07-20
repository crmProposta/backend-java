package com.proposta.crm.controller

import com.proposta.crm.dto.CreateAccountByMasterDTO
import com.proposta.crm.handler.ResponseHandler
import com.proposta.crm.model.ResponseStatusEnum
import com.proposta.crm.model.RoleEnum
import com.proposta.crm.service.AuthUserService
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/master")
class MasterController(
    private val authUserService: AuthUserService,
) {

    @PostMapping("/create-account")
    @PreAuthorize("hasRole(${RoleEnum.Code.MASTER})")
    fun createAccount(@RequestBody @Valid user: CreateAccountByMasterDTO): ResponseEntity<ResponseHandler<Any>> {
        authUserService.createAccountByMasterAccount(user)

        return ResponseEntity.ok(
            ResponseHandler.Success(ResponseStatusEnum.SUCCESS, "success", "account created succesfully")
        )
    }
}
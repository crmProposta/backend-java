package com.proposta.crm.controller

import com.proposta.crm.dto.CreateAccountByMasterDTO
import com.proposta.crm.dto.EditUserDTO
import com.proposta.crm.dto.UserDTO
import com.proposta.crm.dto.UserIdDTO
import com.proposta.crm.handler.ResponseHandler
import com.proposta.crm.model.ResponseStatusEnum
import com.proposta.crm.model.RoleEnum
import com.proposta.crm.service.AuthUserService
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
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

    @GetMapping("/list-account")
    @PreAuthorize("hasRole(${RoleEnum.Code.MASTER})")
    fun listAccount() : ResponseEntity<ResponseHandler<Any>> {
        val accounts: List<UserDTO> = authUserService.listAccountByMasterAccount()
        
        return ResponseEntity.ok(
            ResponseHandler.Success(ResponseStatusEnum.SUCCESS, codeMessage = "success", data = accounts)
        )
    }

    @PostMapping("/enable-account")
    @PreAuthorize("hasRole(${RoleEnum.Code.MASTER})")
    fun enableAccount(@RequestBody @Valid userIdDTO: UserIdDTO): ResponseEntity<ResponseHandler<Any>> {
        val accountEnabled: Boolean = authUserService.enableAccount(userIdDTO.id)

        return ResponseEntity.ok(
            ResponseHandler.Success(ResponseStatusEnum.SUCCESS, codeMessage = "success", data = null)
        )
    }

    @PostMapping("/disable-account")
    @PreAuthorize("hasRole(${RoleEnum.Code.MASTER}")
    fun disableAccount(@RequestBody @Valid userIdDTO: UserIdDTO): ResponseEntity<ResponseHandler<Any>> {
        val accountDisabled: Boolean = authUserService.disableAccount(userIdDTO.id)

        return ResponseEntity.ok(
            ResponseHandler.Success(ResponseStatusEnum.SUCCESS, codeMessage = "success", data = null)
        )
    }

    @GetMapping("/get-account-by-id/{id}")
    @PreAuthorize("hasRole(${RoleEnum.Code.MASTER}")
    fun getAccountById(@PathVariable id: Long): ResponseEntity<ResponseHandler<Any>> {
        val account: UserDTO = authUserService.getAccountById(id)

        return ResponseEntity.ok(
            ResponseHandler.Success(ResponseStatusEnum.SUCCESS, codeMessage = "success", data = account)
        )
    }

    @PostMapping("/edit-account")
    @PreAuthorize("hasRole(${RoleEnum.Code.MASTER}")
    fun editAccount(@RequestBody formUser: EditUserDTO): ResponseEntity<ResponseHandler<Any>> {
        val account: UserDTO = authUserService.editAccount(formUser)

        return ResponseEntity.ok(
            ResponseHandler.Success(ResponseStatusEnum.SUCCESS, codeMessage = "success", data = account)
        )
    }
}
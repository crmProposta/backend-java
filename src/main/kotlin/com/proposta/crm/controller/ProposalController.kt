package com.proposta.crm.controller

import com.proposta.crm.dto.ProposalDTO
import com.proposta.crm.dto.ProposalResponseDTO
import com.proposta.crm.handler.ResponseHandler
import com.proposta.crm.model.ResponseStatusEnum
import com.proposta.crm.service.ProposalService
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.enums.ParameterIn
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/proposal")
class ProposalController(
    private val proposalService: ProposalService
) {


    @PostMapping("/add-proposal")
    @Parameter(
        name = "Authorization",
        `in` = ParameterIn.HEADER,
        description = "Access Token",
        required = true,
        allowEmptyValue = false,
        ref = "header",
        example = "Bearer access_token"
    )
    fun addProposal(@RequestBody proposalDTO: ProposalDTO): ResponseEntity<ResponseHandler<Any>> {
        proposalService.addProposal(proposalDTO)

        return ResponseEntity.ok(
            ResponseHandler.Success(ResponseStatusEnum.SUCCESS, "success", "proposal added successfully!")
        )
    }

    @GetMapping("/list-proposal/{page}")
    fun listProposal(@PathVariable page: Int): ResponseEntity<ResponseHandler<Any>> {
        val listProposalResponseDTO: List<ProposalResponseDTO> = proposalService.listProposal(page)
        return ResponseEntity.ok(
            ResponseHandler.Success(ResponseStatusEnum.SUCCESS, "success", listProposalResponseDTO)
        )
    }

    @GetMapping("/get-count")
    fun getCount(): ResponseEntity<ResponseHandler<Any>> {
        val proposalCountDTO = proposalService.getCount()

        return ResponseEntity.ok(
            ResponseHandler.Success(ResponseStatusEnum.SUCCESS, "success", proposalCountDTO)
        )
    }
}
package com.proposta.crm.controller

import com.proposta.crm.dto.ProposalDTO
import com.proposta.crm.service.ProposalService
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.enums.ParameterIn
import org.springframework.stereotype.Controller
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
    fun addProposal(@RequestBody proposalDTO: ProposalDTO) {
        proposalService.addProposal(proposalDTO)
    }
}
package com.proposta.crm.validator

import com.proposta.crm.dto.ProposalDTO
import com.proposta.crm.exception.ControllerException
import java.util.*

class ProposalValidator(val proposalDTO: ProposalDTO) : ParamsValidation {

    private fun validateProposalDTO() {
        val regexCpfAndCnpj =
            "(^\\d{3}\\.\\d{3}\\.\\d{3}\\-\\d{2}$)|(^\\d{2}\\.\\d{3}\\.\\d{3}\\/\\d{4}\\-\\d{2}$)".toRegex()
        if (!regexCpfAndCnpj.matches(proposalDTO.customer.cpf))
            throw ControllerException("cpf or cnpj not valid", "customer cpf/cnpj not valid")

        if (proposalDTO.customer.birthDate >= Date())
            throw ControllerException("invalidDate", "Date must to be minor than actual time")

        val regexOnlyNumber = "^[0-9]*$".toRegex()
        if (!regexOnlyNumber.matches(proposalDTO.proposalNumber))
            throw ControllerException("invalidProposalNumber", "proposalNumber needs to have only numbers")
    }
    override fun validate() {
        validateProposalDTO()
    }
}
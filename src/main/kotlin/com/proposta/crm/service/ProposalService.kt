package com.proposta.crm.service

import com.proposta.crm.dto.ProposalCountDTO
import com.proposta.crm.dto.ProposalDTO
import com.proposta.crm.dto.ProposalResponseDTO
import com.proposta.crm.entity.Proposal

interface ProposalService {

    fun addProposal(proposalDTO: ProposalDTO)
    fun listProposal(page: Int): List<ProposalResponseDTO>

    fun getCount(): ProposalCountDTO
}
package com.proposta.crm.service

import com.proposta.crm.dto.ProposalDTO
import com.proposta.crm.entity.Proposal

interface ProposalService {

    fun addProposal(proposalDTO: ProposalDTO)
}
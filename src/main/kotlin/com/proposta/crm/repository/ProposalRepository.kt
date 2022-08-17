package com.proposta.crm.repository

import com.proposta.crm.entity.Proposal
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProposalRepository : JpaRepository<Proposal, Long> {
}
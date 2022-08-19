package com.proposta.crm.repository

import com.proposta.crm.entity.Proposal
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface ProposalRepository : PagingAndSortingRepository<Proposal, Long> {


}
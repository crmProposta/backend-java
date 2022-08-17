package com.proposta.crm.repository

import com.proposta.crm.entity.ProposalCustomer
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProposalCustomerRepository : JpaRepository<ProposalCustomer, Long> {
}
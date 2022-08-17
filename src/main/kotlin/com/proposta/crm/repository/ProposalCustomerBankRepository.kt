package com.proposta.crm.repository

import com.proposta.crm.entity.ProposalCustomerBank
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProposalCustomerBankRepository : JpaRepository<ProposalCustomerBank, Long> {
}
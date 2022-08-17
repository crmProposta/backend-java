package com.proposta.crm.repository

import com.proposta.crm.entity.Phone
import org.springframework.data.jpa.repository.JpaRepository

interface ProposalPhoneRepository : JpaRepository<Phone, Long> {
}
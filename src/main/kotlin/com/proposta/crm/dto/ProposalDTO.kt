package com.proposta.crm.dto

import com.proposta.crm.entity.enums.ProposalMediatorBank
import com.proposta.crm.entity.enums.ProposalPromoter
import com.proposta.crm.entity.enums.ProposalType
import com.proposta.crm.entity.enums.SaleSource

data class ProposalDTO(
    val id: Long?,
    val customer: ProposalCustomerDTO,
    val proposalType: ProposalType,
    val registeredByUserId: Long,
    val userSellerId: Long,
    val proposalCustomerBank: ProposalCustomerBankDTO,
    val transactionMediatorBank: ProposalMediatorBank,
    val promoter: ProposalPromoter,
    val saleSource: SaleSource,
    val proposalNumber: String,
)

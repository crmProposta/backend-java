package com.proposta.crm.data.utils

import com.proposta.crm.dto.ProposalCustomerBankDTO
import com.proposta.crm.dto.ProposalCustomerDTO
import com.proposta.crm.dto.ProposalDTO
import com.proposta.crm.entity.enums.*
import java.text.DateFormat
import java.util.*

object ProposalDTOData {

    fun getProposalDTO() = ProposalDTO(
        null,
        ProposalCustomerDTO(
            null,
            "000.000.00-00",
            "Rua tal lugar tal",
            DateFormat.getDateInstance().parse("2002-04-20"),
            MaritalStatus.SINGLE,
            emptyList()
        ),
        ProposalType.LOOT_ANTICIPATION,
        2L,
        2L,
        ProposalCustomerBankDTO(
            "222-222-22-22",
            "ITAU"
        ),
        ProposalMediatorBank.PAN,
        ProposalPromoter.PAN,
        SaleSource.WHATSAPP,
        "29292923"
    )
}
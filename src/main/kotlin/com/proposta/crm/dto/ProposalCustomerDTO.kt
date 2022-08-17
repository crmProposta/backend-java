package com.proposta.crm.dto

import com.proposta.crm.entity.enums.MaritalStatus
import java.util.Date

data class ProposalCustomerDTO(
    val id: Long?,
    val cpf: String,
    val address: String,
    val birthDate: Date,
    val maritalStatus: MaritalStatus,
    val contactPhones: List<PhoneDTO> = emptyList(),

    )

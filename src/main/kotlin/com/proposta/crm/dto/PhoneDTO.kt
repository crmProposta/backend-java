package com.proposta.crm.dto

data class PhoneDTO(
    val id: Long? = null,
    val phoneNumber: String,
) {
    constructor(phoneNumber: String) : this(null, phoneNumber)
}

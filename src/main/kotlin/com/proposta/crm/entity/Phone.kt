package com.proposta.crm.entity

import javax.persistence.*

@Entity
@Table
data class Phone(
    @Id
    @Column(name = "id", insertable = false, updatable = false)
    val id: Long,
    val phoneNumber: String,

    @ManyToOne
    val customer: ProposalCustomer

)

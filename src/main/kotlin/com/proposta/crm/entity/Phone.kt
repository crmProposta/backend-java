package com.proposta.crm.entity

import javax.persistence.*

@Entity
@Table
data class Phone(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,
    val phoneNumber: String,

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    val customer: ProposalCustomer?

)

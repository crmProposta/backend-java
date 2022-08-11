package com.proposta.crm.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.MapsId
import javax.persistence.OneToOne
import javax.persistence.Table

@Entity
@Table
data class ProposalCustomerBank(
    @Id
    @Column(name = "proposal_id")
    val proposalId: Long,
    val accountNumber: String,
    val bankName: String,
    @OneToOne
    @MapsId
    @JoinColumn(name = "proposal_id")
    val proposal: Proposal
)

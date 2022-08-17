package com.proposta.crm.entity

import javax.persistence.*

@Entity
@Table
data class ProposalCustomerBank(
    @Id @Column(name = "proposal_id")
    val id: Long?,
    @MapsId
    @OneToOne(mappedBy = "customerBank", fetch = FetchType.LAZY)
    @JoinColumn(name = "proposal_id")
    val proposal: Proposal,
    val accountNumber: String,
    val bankName: String,
) {
    constructor(proposal: Proposal, accountNumber: String, bankName: String) :
                this(id = null, proposal, accountNumber, bankName)
}

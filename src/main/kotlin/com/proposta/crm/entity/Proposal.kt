package com.proposta.crm.entity

import com.proposta.crm.annotation.EnumWIthConverter
import com.proposta.crm.entity.enums.ProposalMediatorBank
import com.proposta.crm.entity.enums.ProposalPromoter
import com.proposta.crm.entity.enums.ProposalType
import com.proposta.crm.entity.enums.SaleSource
import com.proposta.crm.entity.converter.ProposalTypeConverter
import com.proposta.crm.entity.converter.SaleSourceConverter
import javax.persistence.*

@Entity
@Table
data class Proposal(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    @ManyToOne
    @JoinColumn(name="proposal_customer_id", nullable = false)
    val customer: ProposalCustomer,

    @EnumWIthConverter(ProposalTypeConverter::class)
    val proposalType: ProposalType,

    @ManyToOne
    @JoinColumn(name = "registered_user_id", nullable = false)
    val registeredByUser: AuthUser,
    @ManyToOne
    @JoinColumn(name =  "seller_user_id", nullable = false)
    val userSeller: AuthUser,

    @OneToOne(mappedBy = "proposal", cascade = [CascadeType.ALL])
    @PrimaryKeyJoinColumn
    val customerBank: ProposalCustomerBank,
    @Enumerated(EnumType.STRING)
    val transactionMediatorBank: ProposalMediatorBank,
    @Enumerated(EnumType.STRING)
    val promoter: ProposalPromoter,

    @EnumWIthConverter(SaleSourceConverter::class)
    val saleSource: SaleSource,

    val proposalNumber: String,
)

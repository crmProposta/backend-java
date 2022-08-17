package com.proposta.crm.entity

import com.proposta.crm.annotation.EnumWIthConverter
import com.proposta.crm.entity.enums.ProposalMediatorBank
import com.proposta.crm.entity.enums.ProposalPromoter
import com.proposta.crm.entity.enums.ProposalType
import com.proposta.crm.entity.enums.SaleSource
import com.proposta.crm.entity.converter.ProposalTypeConverter
import com.proposta.crm.entity.converter.SaleSourceConverter
import org.hibernate.annotations.Fetch
import javax.persistence.*
import javax.validation.Valid

@Entity
@Table
data class Proposal(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,
    @ManyToOne
    @JoinColumn(name = "proposal_customer_id", nullable = false)
    val customer: ProposalCustomer,

    @EnumWIthConverter(ProposalTypeConverter::class)
    val proposalType: ProposalType,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "registered_user_id", nullable = false)
    val registeredByUser: AuthUser,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_user_id", nullable = false)
    val userSeller: AuthUser,

    @OneToOne
    @PrimaryKeyJoinColumn
    val customerBank: ProposalCustomerBank? = null,
    @Enumerated(EnumType.STRING)
    val transactionMediatorBank: ProposalMediatorBank,
    @Enumerated(EnumType.STRING)
    val promoter: ProposalPromoter,

    @EnumWIthConverter(SaleSourceConverter::class)
    val saleSource: SaleSource,

    val proposalNumber: String,
) {
    constructor(
        id: Long? = null,
        customer: ProposalCustomer,
        proposalType: ProposalType,
        registeredByUser: AuthUser,
        userSeller: AuthUser,
        transactionMediatorBank: ProposalMediatorBank,
        promoter: ProposalPromoter,
        saleSource: SaleSource,
        proposalNumber: String
    ) : this(
        id,
        customer,
        proposalType,
        registeredByUser,
        userSeller,
        null,
        transactionMediatorBank,
        promoter,
        saleSource,
        proposalNumber
    )
}

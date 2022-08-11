package com.proposta.crm.entity

import com.proposta.crm.entity.enums.MaritalStatus
import javax.persistence.*

@Entity
@Table
data class ProposalCustomer(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    val cpf: String,
    val address: String,
    val birthDate: String,
    @Enumerated(EnumType.STRING)
    val maritalStatus: MaritalStatus,
    @JoinColumn(name = "id", nullable = false)
    @OneToMany(mappedBy = "customer")
    val contactPhones: List<Phone>,

    @OneToMany(mappedBy = "customer")
    val proposals: List<Proposal>

)

package com.proposta.crm.entity

import com.proposta.crm.entity.enums.MaritalStatus
import org.springframework.format.annotation.DateTimeFormat
import java.util.Date
import javax.persistence.*
import javax.validation.constraints.Pattern

@Entity
@Table
data class ProposalCustomer(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,
    val cpf: String,
    val address: String,
    val birthDate: Date,
    @Enumerated(EnumType.STRING)
    val maritalStatus: MaritalStatus,

    @OneToMany(mappedBy = "customer", cascade = [CascadeType.ALL])
    val contactPhones: List<Phone> = emptyList(),

    @OneToMany(mappedBy = "customer")
    val proposals: List<Proposal> = emptyList()

)

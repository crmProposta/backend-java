package com.proposta.crm.entity

import com.proposta.crm.model.RoleEnum
import org.springframework.security.core.GrantedAuthority
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.Id
import javax.validation.constraints.NotBlank

@Entity
data class Role(
    @Id
    @Enumerated(EnumType.STRING)
    val roleCode: RoleEnum,
    @NotBlank
    val roleName: String,
)  : GrantedAuthority {
    override fun getAuthority(): String {
        return roleCode.toString()
    }

    constructor(roleEnum: RoleEnum) : this(roleEnum, roleEnum.name.lowercase())
}
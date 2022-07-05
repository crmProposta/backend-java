package com.proposta.crm.entity

import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import org.hibernate.validator.constraints.Length
import javax.persistence.*
import javax.validation.constraints.NotBlank

@Entity
@Table(
    uniqueConstraints= [
        UniqueConstraint(columnNames=["username"])
    ]
)
data class AuthUser(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,
    @NotBlank
    @Length(min= 3, max = 25)
    val username: String,
    @Length(min = 8)
    val password: String,
    var enabled: Boolean = true,
    val refreshToken: String = "",

    @ManyToMany(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinTable(
        name="user_role",
        joinColumns = [JoinColumn(name="user_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name="role_code", referencedColumnName = "roleCode")]

    )
    val roles: Set<Role>,
)

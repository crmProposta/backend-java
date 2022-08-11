package com.proposta.crm.entity.enums

enum class ProposalType(val id: Int) {
    LOOT_ANTICIPATION(0);

    companion object {
        fun fromId(id: Int): ProposalType {
            return when (id) {
                0 -> LOOT_ANTICIPATION
                else -> throw IllegalArgumentException("${this::class.simpleName} not found with id $id")
            }
        }
    }


}

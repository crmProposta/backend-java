package com.proposta.crm.entity.converter

import com.proposta.crm.entity.enums.ProposalType
import javax.persistence.AttributeConverter
import javax.persistence.Converter

@Converter(autoApply = true)
class ProposalTypeConverter : AttributeConverter<ProposalType, Int> {
    override fun convertToDatabaseColumn(p0: ProposalType): Int = p0.id

    override fun convertToEntityAttribute(p0: Int): ProposalType = ProposalType.fromId(p0)
}
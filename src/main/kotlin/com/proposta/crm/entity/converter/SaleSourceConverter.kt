package com.proposta.crm.entity.converter

import com.proposta.crm.entity.enums.SaleSource
import javax.persistence.AttributeConverter
import javax.persistence.Converter

@Converter(autoApply = true)
class SaleSourceConverter : AttributeConverter<SaleSource, Int> {
    override fun convertToDatabaseColumn(p0: SaleSource): Int = p0.id

    override fun convertToEntityAttribute(p0: Int?): SaleSource =
        SaleSource.values().first{ code -> code.id == p0 }
}
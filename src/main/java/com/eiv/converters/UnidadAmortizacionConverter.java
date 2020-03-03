package com.eiv.converters;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.eiv.enums.UnidadAmortizacionEnum;

@Converter
public class UnidadAmortizacionConverter 
        implements AttributeConverter<UnidadAmortizacionEnum, Character> {

    @Override
    public Character convertToDatabaseColumn(UnidadAmortizacionEnum attribute) {
        return attribute.getValue();
    }

    @Override
    public UnidadAmortizacionEnum convertToEntityAttribute(Character dbData) {
        return UnidadAmortizacionEnum.of(dbData);
    }
}

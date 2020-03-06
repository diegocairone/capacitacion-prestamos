package com.eiv.converters;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.eiv.enums.TasaTipoEnum;

@Converter(autoApply = true)
public class TasaTipoConverter implements AttributeConverter<TasaTipoEnum, Character> {

    @Override
    public Character convertToDatabaseColumn(TasaTipoEnum attribute) {
        return attribute.getValue();
    }

    @Override
    public TasaTipoEnum convertToEntityAttribute(Character dbData) {
        return dbData == null ? null : TasaTipoEnum.of(dbData);
    }
}

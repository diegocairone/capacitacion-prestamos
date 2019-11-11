package com.eiv.converters;

import javax.persistence.AttributeConverter;

import com.eiv.enums.GeneroEnum;

public class GeneroConverter implements AttributeConverter<GeneroEnum, Character> {

    @Override
    public Character convertToDatabaseColumn(GeneroEnum attribute) {
        return attribute.getValue();
    }

    @Override
    public GeneroEnum convertToEntityAttribute(Character dbData) {
        return GeneroEnum.of(dbData);
    }
}

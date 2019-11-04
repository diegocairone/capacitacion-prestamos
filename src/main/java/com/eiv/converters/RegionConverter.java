package com.eiv.converters;

import javax.persistence.AttributeConverter;

import com.eiv.enums.RegionEnum;

public class RegionConverter implements AttributeConverter<RegionEnum, String> {

    @Override
    public String convertToDatabaseColumn(RegionEnum attribute) {
        return attribute.getRegion();
    }

    @Override
    public RegionEnum convertToEntityAttribute(String dbData) {
        return RegionEnum.of(dbData);
    }
}

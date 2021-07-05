package com.github.greennlab.ddul.entity;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
@SuppressWarnings("unused")
public class BooleanToCharYNConverter
    implements AttributeConverter<Boolean, Character> {

  @Override
  public Character convertToDatabaseColumn(Boolean attribute) {
    return attribute == null || !attribute ? 'N' : 'Y';
  }

  @Override
  public Boolean convertToEntityAttribute(Character dbData) {
    return dbData != null && dbData == 'Y';
  }
}

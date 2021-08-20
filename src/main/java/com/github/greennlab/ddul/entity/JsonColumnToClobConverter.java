package com.github.greennlab.ddul.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Properties;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.stereotype.Component;

@Component
@Converter(autoApply = true)
@RequiredArgsConstructor
public class JsonColumnToClobConverter implements
    AttributeConverter<Properties, String> {

  private final ObjectMapper mapper;


  @Override
  public String convertToDatabaseColumn(Properties attribute) {
    try {
      return mapper.writeValueAsString(attribute);
    } catch (JsonProcessingException e) {
      throw new DataAccessResourceFailureException(e.getMessage());
    }
  }

  @Override
  public Properties convertToEntityAttribute(String dbData) {
    try {
      return mapper.readValue(dbData, Properties.class);
    } catch (JsonProcessingException e) {
      throw new DataAccessResourceFailureException(e.getMessage());
    }
  }
}

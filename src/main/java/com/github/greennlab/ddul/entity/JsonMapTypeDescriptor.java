package com.github.greennlab.ddul.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Clob;
import java.sql.SQLException;
import org.hibernate.engine.jdbc.CharacterStream;
import org.hibernate.engine.jdbc.internal.CharacterStreamImpl;
import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.AbstractTypeDescriptor;
import org.hibernate.type.descriptor.java.ImmutableMutabilityPlan;


public class JsonMapTypeDescriptor extends AbstractTypeDescriptor<JsonMap> {

  private static final long serialVersionUID = -1442402723638727650L;

  public static final JsonMapTypeDescriptor INSTANCE = new JsonMapTypeDescriptor();


  private final ObjectMapper objectMapper = new ObjectMapper();

  @SuppressWarnings("unchecked")
  public JsonMapTypeDescriptor() {
    super(JsonMap.class, ImmutableMutabilityPlan.INSTANCE);
  }

  @Override
  public JsonMap fromString(String string) {
    try {
      return objectMapper.readValue(string, JsonMap.class);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }

    return null;
  }

  @Override
  public <X> X unwrap(JsonMap value, Class<X> type, WrapperOptions options) {
    if (value == null) {
      return null;
    }

    if (CharacterStream.class.isAssignableFrom(type)) {
      try {
        @SuppressWarnings("unchecked") final X stream =
            (X) new CharacterStreamImpl(objectMapper.writeValueAsString(value));

        return stream;
      } catch (JsonProcessingException e) {
        e.printStackTrace();
      }
    }

    throw unknownUnwrap(type);
  }

  @Override
  public <X> JsonMap wrap(X value, WrapperOptions options) {
    if (value == null) {
      return null;
    }

    if (value instanceof Clob) {
      try {
        final BufferedReader reader = new BufferedReader(((Clob) value).getCharacterStream());
        final StringBuilder result = new StringBuilder();

        String line;
        while (null != (line = reader.readLine())) {
          result.append(line);
        }

        return objectMapper.readValue(result.toString(), JsonMap.class);
      } catch (SQLException | IOException e) {
        e.printStackTrace();
      }
    }

    throw unknownWrap(value.getClass());
  }
}

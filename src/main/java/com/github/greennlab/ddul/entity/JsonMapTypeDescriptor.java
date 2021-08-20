package com.github.greennlab.ddul.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.hibernate.engine.jdbc.CharacterStream;
import org.hibernate.engine.jdbc.internal.CharacterStreamImpl;
import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.AbstractTypeDescriptor;
import org.hibernate.type.descriptor.java.ImmutableMutabilityPlan;
import org.springframework.stereotype.Component;

@Component
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

  @SneakyThrows
  @Override
  public <X> JsonMap wrap(X value, WrapperOptions options) {
    if (value == null) {
      return null;
    }

    if (value instanceof String) {
      return objectMapper.readValue((String) value, JsonMap.class);
    }

    throw unknownWrap(value.getClass());
  }
}

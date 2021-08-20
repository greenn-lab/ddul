package com.github.greennlab.ddul.entity;

import org.hibernate.type.AbstractSingleColumnStandardBasicType;
import org.hibernate.type.descriptor.sql.ClobTypeDescriptor;

@SuppressWarnings("java:S2055")
public class JsonMapType extends
    AbstractSingleColumnStandardBasicType<JsonMap> {

  private static final long serialVersionUID = 8738814670176159598L;

  public static final String NAME = JsonMapType.class.getName();


  public JsonMapType() {
    super(
        ClobTypeDescriptor.DEFAULT,
        JsonMapTypeDescriptor.INSTANCE
    );
  }

  @Override
  public String getName() {
    return "json-string";
  }

  @Override
  protected boolean registerUnderJavaType() {
    return true;
  }

}

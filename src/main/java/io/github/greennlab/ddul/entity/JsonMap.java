package io.github.greennlab.ddul.entity;

import java.util.HashMap;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class JsonMap extends HashMap<String, Object> {

  private static final long serialVersionUID = -5024721637386731608L;

  public static final String TYPE = "io.github.greennlab.ddul.entity.JsonMapType";

  public JsonMap(JsonMap jsonMap) {
    this.putAll(jsonMap);
  }

}

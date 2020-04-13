package com.cedrus.aeolion.kafkaspringpong.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SpringPongRequest {
  @JsonProperty private String id;
  @JsonProperty private String color;

  public SpringPongRequest(String id, String color) {
    this.id = id;
    this.color = color;
  }
}

package com.cedrus.aeolion.kafkaspringpong.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SpringPongRequest {
    // more fields to be added
    @JsonProperty private String id;
    @JsonProperty private String color;
}
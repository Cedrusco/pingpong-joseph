package com.cedrus.aeolion.kafkaspringpong.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SpringPongResponse {
    @JsonProperty private boolean successIndicator;
    @JsonProperty private String responseMessage;

    public SpringPongResponse(boolean successIndicator, String responseMessage) {
        this.successIndicator = successIndicator;
        this.responseMessage = responseMessage;
    }

    public SpringPongResponse() { }
}
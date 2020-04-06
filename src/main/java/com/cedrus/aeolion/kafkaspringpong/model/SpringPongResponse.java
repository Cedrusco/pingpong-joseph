package com.cedrus.aeolion.kafkaspringpong.model;

import lombok.Data;

@Data
public class SpringPongResponse {
    private boolean successIndicator;
    private String responseMessage;
}
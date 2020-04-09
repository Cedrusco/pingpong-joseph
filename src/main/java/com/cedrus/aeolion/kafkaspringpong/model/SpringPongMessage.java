package com.cedrus.aeolion.kafkaspringpong.model;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

@Data
public class SpringPongMessage {
    private String topic;
    private String message;

    public SpringPongMessage() {}

    @Autowired
    public SpringPongMessage(String topic, String message) {
        this.topic = topic;
        this.message = message;
    }
}
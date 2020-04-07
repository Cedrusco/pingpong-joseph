package com.cedrus.aeolion.kafkaspringpong.model;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

@Data
public class Message {
    private String topic;
    private String message;

    public Message() {}

    @Autowired
    public Message(String topic, String message) {
        this.topic = topic;
        this.message = message;
    }
}
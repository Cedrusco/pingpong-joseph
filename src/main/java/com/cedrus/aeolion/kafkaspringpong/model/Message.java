package com.cedrus.aeolion.kafkaspringpong.model;

import lombok.Data;

@Data
public class Message {
    private String topic;
    private String message;

    public Message(String topic, String message) {
        this.topic = topic;
        this.message = message;
    }
}
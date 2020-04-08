package com.cedrus.aeolion.kafkaspringpong.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "app.topic")
public class TopicConfig {
    private String ping;
    private String pong;
    private String pingPongTopic;
}
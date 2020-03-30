package com.cedrus.aeolion.kafkaspringpong.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "kafka")
public class KafkaConfig {
    private String bootstrapServers;
    private String groupId;
    private String enableAutoCommit;
    private String autoCommitInterval;
    private String serializer;
    private String deserializer;
    private String kafkaAppId;
}
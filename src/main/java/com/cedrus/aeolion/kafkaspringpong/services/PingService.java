package com.cedrus.aeolion.kafkaspringpong.services;

import com.cedrus.aeolion.kafkaspringpong.config.AppConfig;
import com.cedrus.aeolion.kafkaspringpong.config.KafkaConfig;
import com.cedrus.aeolion.kafkaspringpong.config.TopicConfig;
import com.cedrus.aeolion.kafkaspringpong.kafka.SpringPongConsumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PingService {
    private TopicConfig topicConfig;
    private KafkaConfig kafkaConfig;
    private AppConfig appConfig;
    private SpringPongConsumer consumer;

    @Autowired
    public PingService (TopicConfig topicConfig, KafkaConfig kafkaConfig, AppConfig appConfig, SpringPongConsumer consumer) {
        this.topicConfig = topicConfig;
        this.kafkaConfig = kafkaConfig;
        this.appConfig = appConfig;
        this.consumer = consumer;
    }

    public void startPing() {
        consumer.listen(topicConfig.getPing());
    }
}
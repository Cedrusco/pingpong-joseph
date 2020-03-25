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
public class PongService {
    @Autowired private TopicConfig topicConfig;
    @Autowired private KafkaConfig kafkaConfig;
    @Autowired private AppConfig appConfig;

    @Autowired private SpringPongConsumer consumer;

    public void startPong() {
        consumer.listen(topicConfig.getPong());
    }
}
package com.cedrus.aeolion.kafkaspringpong.services;

import com.cedrus.aeolion.kafkaspringpong.config.AppConfig;
import com.cedrus.aeolion.kafkaspringpong.config.KafkaConfig;
import com.cedrus.aeolion.kafkaspringpong.config.TopicConfig;
import com.cedrus.aeolion.kafkaspringpong.kafka.SpringPongConsumer;
import com.cedrus.aeolion.kafkaspringpong.kafka.SpringPongProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PongService {
    private TopicConfig topicConfig;
    private KafkaConfig kafkaConfig;
    private AppConfig appConfig;
    private SpringPongProducer producer;
    private SpringPongConsumer consumer;

    @Autowired
    public PongService (TopicConfig topicConfig, KafkaConfig kafkaConfig, AppConfig appConfig, SpringPongProducer producer, SpringPongConsumer consumer) {
        this.topicConfig = topicConfig;
        this.kafkaConfig = kafkaConfig;
        this.appConfig = appConfig;
        this.producer = producer;
        this.consumer = consumer;
    }

//    public void startPong() {
//        consumer.listen(topicConfig.getPong());
//    }
}
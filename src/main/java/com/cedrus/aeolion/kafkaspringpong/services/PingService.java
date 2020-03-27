package com.cedrus.aeolion.kafkaspringpong.services;

import com.cedrus.aeolion.kafkaspringpong.config.AppConfig;
import com.cedrus.aeolion.kafkaspringpong.config.KafkaConfig;
import com.cedrus.aeolion.kafkaspringpong.config.TopicConfig;
import com.cedrus.aeolion.kafkaspringpong.kafka.SpringPongConsumer;
import com.cedrus.aeolion.kafkaspringpong.kafka.SpringPongProducer;
import com.cedrus.aeolion.kafkaspringpong.model.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.function.Consumer;

@Slf4j
@Service
public class PingService {
    private TopicConfig topicConfig;
    private KafkaConfig kafkaConfig;
    private AppConfig appConfig;
    private SpringPongProducer producer;
    private SpringPongConsumer consumer;

    @Autowired
    public PingService(TopicConfig topicConfig, KafkaConfig kafkaConfig, AppConfig appConfig, SpringPongProducer producer, SpringPongConsumer consumer) {
        this.topicConfig = topicConfig;
        this.kafkaConfig = kafkaConfig;
        this.appConfig = appConfig;
        this.producer = producer;
        this.consumer = consumer;
    }

    public void startPing() {
        consumer.listen(topicConfig.getPing(), respond);
    }

    Consumer<Message> respond = message -> {
        int minDelay = appConfig.getMinDelaySeconds();
        int maxDelay = appConfig.getMaxDelaySeconds();

        Random random = new Random();
        int sleepDuration = random.nextInt((maxDelay - minDelay)) + minDelay;

        try {
            Thread.sleep(sleepDuration * 1000);
            producer.sendMessage(topicConfig.getPong(), "message");
        } catch (Exception e) {
            log.info(e.toString());
        }
    };
}
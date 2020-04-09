package com.cedrus.aeolion.kafkaspringpong.services;

import com.cedrus.aeolion.kafkaspringpong.config.TopicConfig;
import com.cedrus.aeolion.kafkaspringpong.kafka.SpringPongProducer;
import com.cedrus.aeolion.kafkaspringpong.model.SpringPongMessage;
import com.cedrus.aeolion.kafkaspringpong.model.SpringPongBall;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BallAdderService {
    private SpringPongProducer producer;
    private ObjectMapper objectMapper;
    private TopicConfig topicConfig;

    @Autowired
    public BallAdderService(SpringPongProducer producer, ObjectMapper objectMapper, TopicConfig topicConfig) {
        this.producer = producer;
        this.objectMapper = objectMapper;
        this.topicConfig = topicConfig;
    }

    public void addBall(SpringPongBall ball) {
        try {
            String jsonBall = objectMapper.writeValueAsString(ball);
            SpringPongMessage ballMessage = new SpringPongMessage(topicConfig.getPingPongTopic(), jsonBall);
            producer.sendMessage(ballMessage);
            log.info("Ball added.");
        } catch (Exception e) {
            log.error("Error adding ball.");
            log.info(e.toString());
        }
    }
}
package com.cedrus.aeolion.kafkaspringpong.services;

import com.cedrus.aeolion.kafkaspringpong.config.TopicConfig;
import com.cedrus.aeolion.kafkaspringpong.kafka.SpringPongProducer;
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

    @Autowired
    public BallAdderService(SpringPongProducer producer, ObjectMapper objectMapper) {
        this.producer = producer;
        this.objectMapper = objectMapper;
    }

    public void addBall(SpringPongBall ball) {
        try {
            String jsonBall = objectMapper.writeValueAsString(ball);
            producer.sendMessage(jsonBall);
            log.info("Ball added.");
        } catch (Exception e) {
            log.error("Error adding ball.");
            log.info(e.toString());
        }
    }
}
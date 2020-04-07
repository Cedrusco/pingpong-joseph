package com.cedrus.aeolion.kafkaspringpong.controller;

import com.cedrus.aeolion.kafkaspringpong.config.TopicConfig;
import com.cedrus.aeolion.kafkaspringpong.kafka.SpringPongProducer;
import com.cedrus.aeolion.kafkaspringpong.model.Message;
import com.cedrus.aeolion.kafkaspringpong.model.SpringPongRequest;
import com.cedrus.aeolion.kafkaspringpong.model.SpringPongResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class SpringPongController {
    private TopicConfig topicConfig;
    private SpringPongProducer producer;

    @Autowired
    public SpringPongController(TopicConfig topicConfig, SpringPongProducer producer) {
        this.topicConfig = topicConfig;
        this.producer = producer;
    }

    @RequestMapping(value = "/ping", method = RequestMethod.POST)
    @ResponseBody
    public void producePing() throws JsonProcessingException {
        addBall(topicConfig.getPing());
    }

    @RequestMapping(value = "/pong", method = RequestMethod.POST)
    @ResponseBody
    public void producePong() throws JsonProcessingException {
        addBall(topicConfig.getPong());
    }

    @PostMapping(value = "/ball")
    public ResponseEntity<SpringPongResponse> introduceBall(@RequestBody SpringPongRequest request) {
        SpringPongResponse responseObj = new SpringPongResponse();

        try {
            if (request.getId() == null) {
                throw new Exception("Request creation failed.");
            } else {
                addBall(topicConfig.getPing());
            }
        } catch (Exception e) {
            responseObj.setResponseMessage(e.getMessage());
            responseObj.setSuccessIndicator(false);
            return new ResponseEntity<>(responseObj, HttpStatus.BAD_REQUEST);
        }

        responseObj.setSuccessIndicator(true);
        responseObj.setResponseMessage("OK!");

        return new ResponseEntity<>(responseObj, HttpStatus.OK);
    }


    private void addBall(String topic) throws JsonProcessingException {
        producer.sendMessage(new Message(topic, "1"));
    }
}

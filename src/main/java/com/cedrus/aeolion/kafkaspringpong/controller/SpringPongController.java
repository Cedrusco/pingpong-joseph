package com.cedrus.aeolion.kafkaspringpong.controller;

import com.cedrus.aeolion.kafkaspringpong.config.TopicConfig;
import com.cedrus.aeolion.kafkaspringpong.kafka.SpringPongProducer;
import com.cedrus.aeolion.kafkaspringpong.model.*;
import com.cedrus.aeolion.kafkaspringpong.services.BallAdderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// TODO: identify unchecked/unsafe operations

@Slf4j
@RestController
public class SpringPongController {
    private TopicConfig topicConfig;
    private SpringPongProducer producer;
    private BallAdderService ballAdder;

    @Autowired
    public SpringPongController(TopicConfig topicConfig, SpringPongProducer producer, BallAdderService ballAdder) {
        this.topicConfig = topicConfig;
        this.producer = producer;
        this.ballAdder = ballAdder;
    }

//    @RequestMapping(value = "/ping", method = RequestMethod.POST)
//    @ResponseBody
//    public void producePing() throws JsonProcessingException {
//        addBall(topicConfig.getPing());
//    }
//
//    @RequestMapping(value = "/pong", method = RequestMethod.POST)
//    @ResponseBody
//    public void producePong() throws JsonProcessingException {
//        addBall(topicConfig.getPong());
//    }

    @PostMapping(value = "/ball")
    public ResponseEntity<SpringPongResponse> introduceBall(@RequestBody SpringPongRequest request) {
        log.info("Received request to add ball.");
        log.debug("Request = {}", request);

        SpringPongResponse responseObj = new SpringPongResponse();

        try {
            if (request.getId() == null) {
                throw new Exception("Request creation failed.");
            } else {
                addBall(request);
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


//    private void addBall(String topic) throws JsonProcessingException {
//        producer.sendMessage(new Message(topic, "1"));
//    }

    private ResponseEntity<SpringPongResponse> addBall(SpringPongRequest request) {
        try {
            SpringPongBall ball = new SpringPongBall(request.getId(), request.getColor(), Target.PING);
            ballAdder.addBall(ball);
            SpringPongResponse response = new SpringPongResponse(true, request.toString());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.toString());
            e.printStackTrace();
            SpringPongResponse response = new SpringPongResponse(false, e.getMessage());
            return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

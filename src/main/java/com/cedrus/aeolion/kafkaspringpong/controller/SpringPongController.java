package com.cedrus.aeolion.kafkaspringpong.controller;

import com.cedrus.aeolion.kafkaspringpong.config.TopicConfig;
import com.cedrus.aeolion.kafkaspringpong.kafka.SpringPongProducer;
import com.cedrus.aeolion.kafkaspringpong.model.Message;
import com.cedrus.aeolion.kafkaspringpong.services.PingService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SpringPongController {
    private TopicConfig topicConfig;
    private SpringPongProducer producer;
    private PingService pingService;

    @Autowired
    public SpringPongController(TopicConfig topicConfig, SpringPongProducer producer, PingService pingService) {
        this.topicConfig = topicConfig;
        this.producer = producer;
        this.pingService = pingService;
    }

    @RequestMapping(value = "/ping", method = RequestMethod.POST)
    @ResponseBody
    public void producePing() throws JsonProcessingException {
        createResponse(topicConfig.getPing());
    }

    @RequestMapping(value = "/pong", method = RequestMethod.POST)
    @ResponseBody
    public void producePong() throws JsonProcessingException {
        createResponse(topicConfig.getPong());
    }

    private void createResponse(String topic) throws JsonProcessingException {
//        producer.sendMessage(topic, String.format("\"topic: %s\"", topic));
        producer.sendMessage(new Message(topic, "1"));
    }
}

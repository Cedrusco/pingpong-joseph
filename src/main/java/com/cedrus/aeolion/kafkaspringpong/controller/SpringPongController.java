package com.cedrus.aeolion.kafkaspringpong.controller;

import com.cedrus.aeolion.kafkaspringpong.config.TopicConfig;
import com.cedrus.aeolion.kafkaspringpong.kafka.SpringPongProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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
    public void producePing() {
        createResponse(topicConfig.getPing());
    }

    @RequestMapping(value = "/pong", method = RequestMethod.POST)
    @ResponseBody
    public void producePong() {
        createResponse(topicConfig.getPong());
    }

    private void createResponse(String topic) {
        producer.sendMessage(topic, String.format("\"topic: %s\"", topic));
    }
}

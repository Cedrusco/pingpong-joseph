package com.cedrus.aeolion.kafkaspringpong.controller;

import com.cedrus.aeolion.kafkaspringpong.kafka.SpringPongProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SpringPongController {
    @Autowired private SpringPongProducer producer;

    @RequestMapping(value = "/ball", method = RequestMethod.POST)
    @ResponseBody
    public void producePing() {
        createBall("ping");
    }

    private void createBall(String topic) {
        producer.sendMessage(topic, "Ball Created");
    }
}
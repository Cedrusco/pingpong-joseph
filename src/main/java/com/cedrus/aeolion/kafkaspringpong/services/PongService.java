package com.cedrus.aeolion.kafkaspringpong.services;


import com.cedrus.aeolion.kafkaspringpong.config.TopicConfig;
import com.cedrus.aeolion.kafkaspringpong.streams.SpringPongStream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PongService {
    private TopicConfig topicConfig;
    private SpringPongStream pongStream;

    @Autowired
    public PongService (TopicConfig topicConfig, SpringPongStream pongStream) {
        this.topicConfig = topicConfig;
        this.pongStream = pongStream;
    }

    public void startPong() {
        pongStream.createKStream(topicConfig.getPong());
    }
}

package com.cedrus.aeolion.kafkaspringpong.services;

import com.cedrus.aeolion.kafkaspringpong.config.TopicConfig;
import com.cedrus.aeolion.kafkaspringpong.streams.SpringPongStream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PingService {
    private TopicConfig topicConfig;
    private SpringPongStream pingStream;

    @Autowired
    public PingService(TopicConfig topicConfig, SpringPongStream pingStream) {
        this.topicConfig = topicConfig;
        this.pingStream = pingStream;
    }

    public void startPing() {
        pingStream.createKStream(topicConfig.getPing());
    }
}

package com.cedrus.aeolion.kafkaspringpong.services;


import com.cedrus.aeolion.kafkaspringpong.config.KafkaConfig;
import com.cedrus.aeolion.kafkaspringpong.config.TopicConfig;
import com.cedrus.aeolion.kafkaspringpong.model.Target;
import com.cedrus.aeolion.kafkaspringpong.streams.SpringPongStream;
import com.cedrus.aeolion.kafkaspringpong.streams.SpringPongTopology;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Properties;

import static com.cedrus.aeolion.kafkaspringpong.model.Target.*;

@Slf4j
@Service
public class PongService {
    private TopicConfig topicConfig;
    private SpringPongStream pongStream;
    private SpringPongTopology springPongTopology;
    private KafkaConfig kafkaConfig;

    @Autowired
    public PongService (TopicConfig topicConfig, SpringPongStream pongStream, SpringPongTopology springPongTopology, KafkaConfig kafkaConfig) {
        this.topicConfig = topicConfig;
        this.pongStream = pongStream;
        this.springPongTopology = springPongTopology;
        this.kafkaConfig = kafkaConfig;
    }

    public void startPong() {
        pongStream.createKStream(topicConfig.getPong());
    }

    public void startPongStream() {
        log.info("Starting Pong stream...");
        Properties pongStreamConfiguration = new Properties();

        pongStreamConfiguration.put(StreamsConfig.APPLICATION_ID_CONFIG, kafkaConfig.getKafkaAppId() + PONG);
        pongStreamConfiguration.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConfig.getBootstrapServers());

        KafkaStreams pongStream = new KafkaStreams(springPongTopology.getSPTopology(PONG), pongStreamConfiguration);

        pongStream.start();
    }
}

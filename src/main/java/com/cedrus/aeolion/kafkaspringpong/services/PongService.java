package com.cedrus.aeolion.kafkaspringpong.services;

import com.cedrus.aeolion.kafkaspringpong.config.KafkaConfig;
import com.cedrus.aeolion.kafkaspringpong.streams.SpringPongTopology;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Properties;

import static com.cedrus.aeolion.kafkaspringpong.model.Target.*;

@Slf4j
@Service
public class PongService {
    private final SpringPongTopology springPongTopology;
    private final KafkaConfig kafkaConfig;

    @Autowired
    public PongService (SpringPongTopology springPongTopology, KafkaConfig kafkaConfig) {
        this.springPongTopology = springPongTopology;
        this.kafkaConfig = kafkaConfig;
    }

    public final void startPongStream() {
        log.info("Starting Pong stream...");
        final Properties pongStreamConfiguration = new Properties();

        pongStreamConfiguration.put(StreamsConfig.APPLICATION_ID_CONFIG, kafkaConfig.getKafkaAppId() + PONG);
        pongStreamConfiguration.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConfig.getBootstrapServers());

        final KafkaStreams pongStream = new KafkaStreams(springPongTopology.getSPTopology(PONG), pongStreamConfiguration);

        pongStream.start();
    }
}

package com.cedrus.aeolion.kafkaspringpong.services;

import com.cedrus.aeolion.kafkaspringpong.config.KafkaConfig;
import com.cedrus.aeolion.kafkaspringpong.config.TopicConfig;
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
public class PingService {
    private SpringPongTopology springPongTopology;
    private KafkaConfig kafkaConfig;

    @Autowired
    public PingService(SpringPongTopology springPongTopology, KafkaConfig kafkaConfig) {
        this.springPongTopology = springPongTopology;
        this.kafkaConfig = kafkaConfig;
    }

    public void startPingStream() {
        log.info("Starting Ping stream...");
        Properties pingStreamConfiguration = new Properties();

        pingStreamConfiguration.put(StreamsConfig.APPLICATION_ID_CONFIG, kafkaConfig.getKafkaAppId() + PING);
        pingStreamConfiguration.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConfig.getBootstrapServers());

        KafkaStreams pingStream = new KafkaStreams(springPongTopology.getSPTopology(PING), pingStreamConfiguration);

        pingStream.start();
    }
}

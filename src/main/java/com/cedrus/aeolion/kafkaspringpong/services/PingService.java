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
public class PingService {
    private TopicConfig topicConfig;
    private SpringPongStream pingStream;
    private SpringPongTopology springPongTopology;
    private KafkaConfig kafkaConfig;

    @Autowired
    public PingService(TopicConfig topicConfig, SpringPongStream pingStream, SpringPongTopology springPongTopology, KafkaConfig kafkaConfig) {
        this.topicConfig = topicConfig;
        this.pingStream = pingStream;
        this.springPongTopology = springPongTopology;
        this.kafkaConfig = kafkaConfig;
    }

    public void startPing() {
        pingStream.createKStream(topicConfig.getPing());
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

package com.cedrus.aeolion.kafkaspringpong.streams;

import com.cedrus.aeolion.kafkaspringpong.config.AppConfig;
import com.cedrus.aeolion.kafkaspringpong.config.KafkaConfig;
import com.cedrus.aeolion.kafkaspringpong.config.TopicConfig;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Properties;

public class SpringPongStream {
    private AppConfig appConfig;
    private KafkaConfig kafkaConfig;
    private TopicConfig topicConfig;

    @Autowired
    public SpringPongStream(AppConfig appConfig, KafkaConfig kafkaConfig, TopicConfig topicConfig) {
        this.appConfig = appConfig;
        this.kafkaConfig = kafkaConfig;
        this.topicConfig = topicConfig;
    }

    public void createKStream(String initTopic) {
        Serde<String> stringSerde = Serdes.String();
        Properties streamsConfiguration = new Properties();

        streamsConfiguration.put(StreamsConfig.APPLICATION_ID_CONFIG, kafkaConfig.getKafkaAppId());
        streamsConfiguration.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConfig.getBootstrapServers());

        StreamsBuilder builder = new StreamsBuilder();

        /* From StreamsBuilder documentation:
        The Consumed class is used to define the optional parameters when using StreamsBuilder to build instances of KStream, KTable, and GlobalKTable.
        For example, you can read a topic as KStream with a custom timestamp extractor and specify the corresponding key and value serdes. */
        KStream kStream = builder.stream(initTopic, Consumed.with(stringSerde, stringSerde));
        

    }
}
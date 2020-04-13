package com.cedrus.aeolion.kafkaspringpong.kafka;

import com.cedrus.aeolion.kafkaspringpong.config.KafkaConfig;
import com.cedrus.aeolion.kafkaspringpong.config.TopicConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Slf4j
@Component
public class SpringPongProducer {
    private final KafkaConfig kafkaConfig;
    private final TopicConfig topicConfig;

    @Autowired
    public SpringPongProducer (KafkaConfig kafkaConfig, TopicConfig topicConfig) {
        this.kafkaConfig = kafkaConfig;
        this.topicConfig = topicConfig;
    }

    public void sendMessage(String message) {
        log.debug("Sending message: " + message +" on topic " + topicConfig.getPingPongTopic());

        String serializer = kafkaConfig.getSerializer();

        Properties kafkaProps = new Properties();
        kafkaProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConfig.getBootstrapServers());
        kafkaProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, serializer);
        kafkaProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, serializer);
        Producer<String, String> producer = new KafkaProducer<>(kafkaProps);
        producer.send(new ProducerRecord<>(topicConfig.getPingPongTopic(), null, message));
        producer.close();
    }
}

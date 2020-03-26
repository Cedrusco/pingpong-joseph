package com.cedrus.aeolion.kafkaspringpong.kafka;

import com.cedrus.aeolion.kafkaspringpong.config.KafkaConfig;
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
    private KafkaConfig kafkaConfig;

    @Autowired
    public SpringPongProducer (KafkaConfig kafkaConfig) {
        this.kafkaConfig = kafkaConfig;
    }

    public void sendMessage(String topic, String message) {
        log.info("Sending message: " + message);

        String serializer = kafkaConfig.getSerializer();

        Properties kafkaProps = new Properties();
        kafkaProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConfig.getBootstrapServers());
        kafkaProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, serializer);
        kafkaProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, serializer);
        Producer<String, String> producer = new KafkaProducer<>(kafkaProps);
        producer.send(new ProducerRecord<>(topic, null, message));
        producer.close();
    }
}
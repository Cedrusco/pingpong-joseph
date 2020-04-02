package com.cedrus.aeolion.kafkaspringpong.streams;

import com.cedrus.aeolion.kafkaspringpong.config.AppConfig;
import com.cedrus.aeolion.kafkaspringpong.config.KafkaConfig;
import com.cedrus.aeolion.kafkaspringpong.config.TopicConfig;
import com.cedrus.aeolion.kafkaspringpong.model.Message;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.*;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Properties;
import java.util.Random;

@Component
@Slf4j
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

    public void createKStream(String initialTopic) {
        log.info("Listening on " + initialTopic + " --------");
        Properties streamsConfiguration = new Properties();

        streamsConfiguration.put(StreamsConfig.APPLICATION_ID_CONFIG, kafkaConfig.getKafkaAppId() + initialTopic);
        streamsConfiguration.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConfig.getBootstrapServers());
        streamsConfiguration.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        streamsConfiguration.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());

        StreamsBuilder builder = new StreamsBuilder();
        Serde<String> stringSerde = Serdes.String();

        // TODO: look into generics for the KStreams; use wherever possible
        KStream initialStream = builder.stream(initialTopic, Consumed.with(stringSerde, stringSerde));
        String nextTopic = initialTopic.equals(topicConfig.getPing()) ? topicConfig.getPong() : topicConfig.getPing();
        KStream nextStream = initialStream.transformValues(delayVts());
        nextStream.to(nextTopic, Produced.with(stringSerde, stringSerde));
        KafkaStreams nextTopicStream = new KafkaStreams(builder.build(), streamsConfiguration);
        nextTopicStream.start();
    }

    /* From Kafka docs:
    The ValueTransformer interface for stateful mapping of a value to a new value (with possible new type).
    This is a stateful record-by-record operation, i.e, transform(Object) is invoked individually for each record of
    a stream and can access and modify a state that is available beyond a single call of transform(Object)
    (cf. ValueMapper for stateless value transformation). Additionally, this ValueTransformer can schedule a method
    to be called periodically with the provided context. If ValueTransformer is applied to a KeyValue pair record
    the record's key is preserved.
    Use ValueTransformerSupplier to provide new instances of ValueTransformer to Kafka Stream's runtime. */

    private ValueTransformerSupplier<String, String> delayVts() {
        return () -> new ValueTransformer<String, String>() {
            @Override
            public void init(ProcessorContext context) { }

            @Override
            public String transform(String message) {
                Message messageObj = new Message(message, message);

                try {
                    messageObj = new ObjectMapper().readValue(message, Message.class);
                } catch (Exception e) {
                    log.info(e.toString());
                }

                String newTopic = messageObj.getTopic().equals(topicConfig.getPing()) ? topicConfig.getPong() : topicConfig.getPing();
                log.info("newTopic: " + newTopic);
                messageObj.setTopic(newTopic);
                messageObj.setMessage(Integer.toString(Integer.parseInt(messageObj.getMessage()) + 1));

                log.info(messageObj.getTopic() + "! " + messageObj.getMessage());

                int minDelay = appConfig.getMinDelaySeconds();
                int maxDelay = appConfig.getMaxDelaySeconds();

                Random random = new Random();
                int sleepDuration = random.nextInt((maxDelay - minDelay)) + minDelay;

                try {
                    Thread.sleep(sleepDuration * 1000);
                    return new ObjectMapper().writeValueAsString(messageObj);
                } catch (Exception e) {
                    log.info(e.toString());
                }

                return null;
            }

            @Override
            public void close() { }
        };
    }
}

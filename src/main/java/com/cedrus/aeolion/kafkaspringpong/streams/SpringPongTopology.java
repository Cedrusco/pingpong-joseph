package com.cedrus.aeolion.kafkaspringpong.streams;

import com.cedrus.aeolion.kafkaspringpong.config.AppConfig;
import com.cedrus.aeolion.kafkaspringpong.config.TopicConfig;
import com.cedrus.aeolion.kafkaspringpong.model.SpringPongBall;
import com.cedrus.aeolion.kafkaspringpong.model.Target;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.*;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Random;

@Slf4j
@Component
public class SpringPongTopology {
    private TopicConfig topicConfig;
    private AppConfig appConfig;
    private ObjectMapper objectMapper;

    @Autowired
    public SpringPongTopology(TopicConfig topicConfig, AppConfig appConfig, ObjectMapper objectMapper) {
        this.topicConfig = topicConfig;
        this.appConfig = appConfig;
        this.objectMapper = objectMapper;
    }

    public Topology getSPTopology(Target target) {
        Serde<String> stringSerde = Serdes.String();
        StreamsBuilder builder = new StreamsBuilder();

        KStream<String, String> initialStream = builder.stream(topicConfig.getPingPongTopic(), Consumed.with(stringSerde, stringSerde));
        KStream<String, String> filteredStream = initialStream.branch(getBranchPredicate(target))[0];
        KStream<String, String> nextStream = filteredStream.transformValues(sleepAndSerializeBall());
        nextStream.to(topicConfig.getPingPongTopic(), Produced.with(stringSerde, stringSerde));
        return builder.build();
    }

    private Predicate<String, String> getBranchPredicate(Target target) {
        return new Predicate<String, String>() {
            @Override
            public boolean test(String key, String value) {
                SpringPongBall ball = getBallFromString(value);
                return ball.getTarget().equals(target);
            }
        };
    }

    private SpringPongBall getBallFromString(String spbAsString) {
        try {
            return objectMapper.readValue(spbAsString, SpringPongBall.class);
        } catch (Exception e) {
            log.error("Ball deserialization error.");
            throw new RuntimeException(e);
        }
    }

    private String writeBallAsString(SpringPongBall springPongBall) {
        try {
            return objectMapper.writeValueAsString(springPongBall);
        } catch (Exception e) {
            log.error("Ball serialization error.");
            throw new RuntimeException(e);
        }
    }

    private ValueTransformerSupplier<String, String> sleepAndSerializeBall() {
        return () -> new ValueTransformer<String, String>() {
            @Override
            public void init(ProcessorContext context) { }

            @Override
            public String transform(String ballAsString) {
                log.info("Transforming ball: {}", ballAsString);
                SpringPongBall springPongBall = getBallFromString(ballAsString);

                int sleepDuration = getSleepDurationInSeconds();
                log.info("Ball {} sleeping for {} seconds.", springPongBall.getId(), sleepDuration);
                try {
                    Thread.sleep(sleepDuration * 1000);
                    springPongBall.changeTarget();
                    return writeBallAsString(springPongBall);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void close() { }

            public int getSleepDurationInSeconds() {
                int minDelay = appConfig.getMinDelaySeconds();
                int maxDelay = appConfig.getMaxDelaySeconds();
                Random random = new Random();

                return random.nextInt((maxDelay - minDelay)) + minDelay;
            }
        };
    }
}
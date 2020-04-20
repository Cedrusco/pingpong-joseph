package com.cedrus.aeolion.kafkaspringpong;

import com.cedrus.aeolion.kafkaspringpong.services.PingService;
import com.cedrus.aeolion.kafkaspringpong.services.PongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class KafkaSpringPongApplication {
  private final PingService pingService;
  private final PongService pongService;

  @Autowired
  public KafkaSpringPongApplication(PingService pingService, PongService pongService) {
    this.pingService = pingService;
    this.pongService = pongService;
  }

  public static void main(String[] args) {
    SpringApplication.run(KafkaSpringPongApplication.class, args);
  }

  @Bean
  public CommandLineRunner pingRunner(ApplicationContext context) {
    return args -> {
      ((PingService) context.getBean("pingService")).startPingStream();
    };
  }

  @Bean
  public CommandLineRunner pongRunner(ApplicationContext context) {
    return args -> {
      ((PongService) context.getBean("pongService")).startPongStream();
    };
  }
}

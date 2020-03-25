package com.cedrus.aeolion.kafkaspringpong;

import com.cedrus.aeolion.kafkaspringpong.services.PingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class KafkaSpringPongApplication {
	@Autowired private PingService pingService;

	public static void main(String[] args) {
		SpringApplication.run(KafkaSpringPongApplication.class, args);
	}

	@Bean
	public CommandLineRunner pingRunner(ApplicationContext context) {
		return args -> {
			((PingService) context.getBean("pingService")).startPing();
		};
	}
}

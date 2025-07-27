package com.jmsmq.amqsample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class AmqSampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(AmqSampleApplication.class, args);
	}

}

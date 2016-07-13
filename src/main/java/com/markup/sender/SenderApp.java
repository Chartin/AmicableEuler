package com.markup.sender;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SenderApp {

	public static void main(String[] args) {
		SpringApplicationBuilder senderBuilder = new SpringApplicationBuilder(SenderApp.class);
		//The receiver application is using 8080 by default
		senderBuilder.properties("server.port=8081");
		senderBuilder.run(args);
	}
}

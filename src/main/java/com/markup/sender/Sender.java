package com.markup.sender;

import java.util.Random;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.markup.common.Message;

@Component
public class Sender {
	
	final static int MILLIS_PER_SECOND = 1000;
	final static int SECONDS_PER_REQUEST = 3;
	RestTemplate restTemplate = new RestTemplate();
	long missionId = Long.MIN_VALUE;
	Random random = new Random();
	ObjectNode node = new ObjectMapper().createObjectNode();
	
	@Scheduled(initialDelay=60*MILLIS_PER_SECOND, fixedRate=SECONDS_PER_REQUEST*MILLIS_PER_SECOND)
	public void sendMessage() {
		node.put("missionId", missionId++);
		int randomNum = random.nextInt((Message.MAX_SEED - Message.MIN_SEED) + 1) + Message.MIN_SEED;
		node.put("seed", randomNum);
		ResponseEntity<ObjectNode> response = restTemplate.postForEntity("http://localhost:8080/messages", node, ObjectNode.class);
		if(response != null && response.getStatusCode() == HttpStatus.OK) {
			ObjectNode responseNode = response.getBody();
			JsonNode answerNode = responseNode.get("answer");
			System.out.println("" + answerNode.asInt() + " is the amicable sum for " + randomNum);
		}
	}

}

package com.markup.receiver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.markup.common.Message;

@RestController
@EnableAutoConfiguration
public class Receiver {

	Set<Long> missionIds = new TreeSet<Long>();
	final static Map<Integer, Integer> amicableNumbers = new TreeMap<Integer, Integer>();

	@RequestMapping(value = "/messages", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    ResponseEntity<String> receiveMessage(@RequestBody Message message) {
		int seed = message.getSeed();
		if(seed < Message.MIN_SEED || seed > Message.MAX_SEED) {
			return ResponseEntity.badRequest().body(
					"seed must be between " + Message.MIN_SEED + " and " + Message.MAX_SEED);
		}
		
		long missionId = message.getMissionId();
		if(missionIds.contains(missionId)) {
			return ResponseEntity.badRequest().body(
					"missionId " + missionId + " already used.");
		}
		else {
			missionIds.add(missionId);
		}
		
		int sum = calculateSum(seed);
		
		ObjectNode node = createSumObject(sum);
		
        return ResponseEntity.ok(node.toString());
    }

	private int calculateSum(int seed) {
		int sum = 0;
		for(Entry<Integer, Integer> numberPair: amicableNumbers.entrySet()) {
			int key = numberPair.getKey();
			int value = numberPair.getValue();
			//If key > value, we already counted the pair
			if(key < value && value < seed) {
				sum += key + value;
			}
			else if(key >= seed) {
				break;
			}
		}
		return sum;
	}

	private ObjectNode createSumObject(int sum) {
		ObjectNode node = new ObjectMapper().createObjectNode();
		node.put("answer", sum);
		return node;
	}

    public static void main(String[] args) throws Exception {
    	initializeAmicableNumbers();
        SpringApplication.run(Receiver.class, args);
    }
    
    private static void initializeAmicableNumbers() {
    	for(int i = 2; i < Message.MAX_SEED; i++){
    		if(amicableNumbers.containsKey(i)) {
    			continue;
    		}
    		int sum = getSumOfFactors(i);
    		if(sum != i && i == getSumOfFactors(sum)) {
    			amicableNumbers.put(i, sum);
    			amicableNumbers.put(sum, i);
    		}
    	}
    }
    
    private static int getSumOfFactors(int number) {
    	int[] factors = Factorizer.getFactors(number);
		return Arrays.stream(factors).sum();
    }
}
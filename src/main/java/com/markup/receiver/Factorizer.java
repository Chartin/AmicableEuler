package com.markup.receiver;

import java.util.ArrayList;

public class Factorizer {

	public static int[] getFactors(int seed) {
		ArrayList<Integer> factors = new ArrayList<Integer>();
		int max = seed;
		factors.add(1);
		//If the number itself isn't a factor of itself
		//Then 1 should not be considered to be a valid number to be factored
		for(int i = 2; i < max; i++) {
			if(seed % i == 0) {
				factors.add(i);
				int factor = seed / i;
				if(factor > i) {
					factors.add(factor);
				}
				max = factor;
			}
		}
		
		//I thought Java 8's streams could have already handled this
		int[] result = new int[factors.size()];
		for(int i = 0; i < result.length; i++) {
			result[i] = factors.get(i);
		}
		
		return result;
	}

}

package com.salemcreative;

import java.util.Random;

import junit.framework.TestCase;

public class NumberManagerTest extends TestCase {
	
	private static final boolean DEBUG_MODE = false;
	private static final int NR_OF_ITERATIONS = 100;
	
	public void testRandomIntInclusiveUpperBound() {
		
		int lowerBound;
		int upperBound;
		
		for (int i = 0; i < NR_OF_ITERATIONS; i++) {
			lowerBound = new Random().nextInt(42);
			upperBound = lowerBound + new Random().nextInt(10);
			
			int randomIntInclusive = NumberManager.getRandomIntInclusiveUpperBound(lowerBound, upperBound);
			log("randomIntInclusive = " +randomIntInclusive + " given range ["+lowerBound+","+upperBound+"]");
			
			assertTrue(randomIntInclusive >=  lowerBound);
			assertTrue(randomIntInclusive <= upperBound);
		}
	}
	
	public void testRandomIntExlusive() {
		int lowerBound;
		int upperBound;
		
		for (int i = 0; i < NR_OF_ITERATIONS; i++) {
			lowerBound = new Random().nextInt(7);
			upperBound = lowerBound + new Random().nextInt(10) + 1;
			
			int randomIntExclusive = NumberManager.getRandomIntExlusiveUpperBound(lowerBound, upperBound);
			log("randomIntExclusive = " +randomIntExclusive + " given range ["+lowerBound+","+upperBound+"]");
			
			assertTrue(randomIntExclusive >=  lowerBound);
			assertTrue(randomIntExclusive < upperBound);
		}
	}
	
	public void testPercentageInt() {
		int nominator = 5;
		int denominator = 10;
		double percentage = NumberManager.calculatPercentage(nominator, denominator);
		assertEquals(50.0, percentage);
	}
	
	public void testPercentageDouble() {
		double nominator = 5;
		double denominator = 10;
		double percentage = NumberManager.calculatPercentage(nominator, denominator);
		assertEquals(50.0, percentage);
	}
	
	public void testPercentageIsInfinite() {
		double nominator = 5;
		double denominator = 0;
		double percentage = NumberManager.calculatPercentage(nominator, denominator);
		assertTrue(Double.isInfinite(percentage));
	}
	
	public void testPercentageIsZero() {
		double nominator = 0;
		double denominator = 10;
		double percentage = NumberManager.calculatPercentage(nominator, denominator);
		assertEquals(0.0, percentage);
	}
	
	private void log(String message) {
		if (DEBUG_MODE) {
			System.out.println(message);
		}
	}
}

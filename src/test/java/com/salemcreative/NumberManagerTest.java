package com.salemcreative;

import java.util.Random;

import junit.framework.TestCase;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class NumberManagerTest {
	
	private static final boolean DEBUG_MODE = false;
	private static final int NR_OF_ITERATIONS = 100;

	@Test
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

	@Test
	public void testRandomIntExclusive() {
		int lowerBound;
		int upperBound;
		
		for (int i = 0; i < NR_OF_ITERATIONS; i++) {
			lowerBound = new Random().nextInt(7);
			upperBound = lowerBound + new Random().nextInt(10) + 1;
			
			int randomIntExclusive = NumberManager.getRandomIntExclusiveUpperBound(lowerBound, upperBound);
			log("randomIntExclusive = " +randomIntExclusive + " given range ["+lowerBound+","+upperBound+"]");
			
			assertTrue(randomIntExclusive >=  lowerBound);
			assertTrue(randomIntExclusive < upperBound);
		}
	}

	@Test
	public void testPercentageInt() {
		int nominator = 5;
		int denominator = 10;
		Double expected = 50.0;
		Double actual = NumberManager.calculatePercentage(nominator, denominator);
		assertEquals(expected, actual);
	}

	@Test
	public void testPercentageDouble() {
		double nominator = 5;
		double denominator = 10;
		Double expected = 50.0;
		Double actual = NumberManager.calculatePercentage(nominator, denominator);
		assertEquals(expected, actual);
	}

	@Test
	public void testPercentageIsInfinite() {
		double nominator = 5;
		double denominator = 0;
		double percentage = NumberManager.calculatePercentage(nominator, denominator);
		assertTrue(Double.isInfinite(percentage));
	}

	@Test
	public void testPercentageIsZero() {
		double nominator = 0;
		double denominator = 10;
		Double expected = 0.0;
		Double actual = NumberManager.calculatePercentage(nominator, denominator);
		assertEquals(expected, actual);
	}
	
	private void log(String message) {
		if (DEBUG_MODE) {
			System.out.println(message);
		}
	}
}

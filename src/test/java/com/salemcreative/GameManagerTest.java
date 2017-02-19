package com.salemcreative;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import junit.framework.TestCase;

public class GameManagerTest extends TestCase {
	
	private static final double MAX_PERCENTAGE_THRESHOLD_GAMES_WON_WITH_DOOR_KEPT = 40;
	private static final double MIN_PERCENTAGE_THRESHOLD_GAMES_WON_WITH_DOOR_SWITCHED = 60;
	private static final String	CHOICE_1	= "1";
	private static final String	CHOICE_2	= "2";
	private static final String	CHOICE_NO	= "n";
	private static final String	NL	= System.getProperty("line.separator");
	private static final String	NL2	= "\r";
	
	ByteArrayInputStream in;
	
	public void testSimulateGamesAlwaysKeepDoor() {
		GameManager gameManager = new GameManager();
		GameSessionStatistics gameSessionStats = gameManager.simulateGamesAlwaysKeepDoor();
		gameSessionStats.reportGameSessionStatistics();
		
		double percentageWonWithDoorKept = gameSessionStats.getPercentageWonWithDoorKept();
		assertTrue(percentageWonWithDoorKept <= MAX_PERCENTAGE_THRESHOLD_GAMES_WON_WITH_DOOR_KEPT);
	}
	
	public void testSimulateGamesAlwaysSwitchDoor() {
		GameManager gameManager = new GameManager();
		GameSessionStatistics gameSessionStats = gameManager.simulateGamesAlwaysSwitchDoor();
		gameSessionStats.reportGameSessionStatistics();
		double percentageWonWithDoorSwitched = gameSessionStats.getPercentageWonWithDoorSwitched();
		assertTrue(percentageWonWithDoorSwitched >= MIN_PERCENTAGE_THRESHOLD_GAMES_WON_WITH_DOOR_SWITCHED);
	}
	
	public void testPlay() throws IOException {
		setUserInput(CHOICE_1 + NL + CHOICE_2 + NL + CHOICE_NO + NL + CHOICE_2);
		
		GameManager gameManager = new GameManager();
		gameManager.play();
		readUserInput(CHOICE_2);
		
	}
	
	private void setUserInput(String input) {
		in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
	}
	
	private void readUserInput(String input) throws IOException {
		String data = input + NL;
		InputStream stdin = System.in;
		try {
			System.setIn(new ByteArrayInputStream(data.getBytes()));
			Scanner scanner = new Scanner(System.in);
			System.out.println(scanner.nextLine());
		} finally {
			//System.setIn(stdin);
		}
	}
}

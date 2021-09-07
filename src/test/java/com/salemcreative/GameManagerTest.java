package com.salemcreative;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class GameManagerTest {
	
	private static final double MAX_PERCENTAGE_THRESHOLD_GAMES_WON_WITH_DOOR_KEPT = 40;
	private static final double MIN_PERCENTAGE_THRESHOLD_GAMES_WON_WITH_DOOR_SWITCHED = 60;
	private static final String	CHOICE_1	= "1";
	private static final String	CHOICE_2	= "2";
	private static final String CHOICE_NO = "n";
	private static final String CHOICE_QUIT = "6";
	private static final String	NL	= System.getProperty("line.separator");

	@Test
	public void testSimulateGamesAlwaysKeepDoor() {
		GameManager gameManager = new GameManager();
		GameSessionStatistics gameSessionStats = gameManager.simulateGamesAlwaysKeepDoor();
		gameSessionStats.reportGameSessionStatistics();
		
		double percentageWonWithDoorKept = gameSessionStats.getPercentageWonWithDoorKept();
		assertTrue(percentageWonWithDoorKept <= MAX_PERCENTAGE_THRESHOLD_GAMES_WON_WITH_DOOR_KEPT);
	}

	@Test
	public void testSimulateGamesAlwaysSwitchDoor() {
		GameManager gameManager = new GameManager();
		GameSessionStatistics gameSessionStats = gameManager.simulateGamesAlwaysSwitchDoor();
		gameSessionStats.reportGameSessionStatistics();
		double percentageWonWithDoorSwitched = gameSessionStats.getPercentageWonWithDoorSwitched();
		assertTrue(percentageWonWithDoorSwitched >= MIN_PERCENTAGE_THRESHOLD_GAMES_WON_WITH_DOOR_SWITCHED);
	}

	/**
	 * Simulate an interactive game with the given set of choices.
	 * Choice 1: Select door 1 (1).
	 * Choice 2: Switch door (2).
	 * Choice 3: Play again (n)
	 * Choice 4: Quit (6)
	 */
	@Test
	public void testInteractivePlay() {
		final String userInput = CHOICE_1 + NL + CHOICE_2 + NL + CHOICE_NO + NL + CHOICE_QUIT + NL;
		final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(userInput.getBytes());

		GameManager gameManager = new GameManager(byteArrayInputStream);
		gameManager.play();
	}

	/**
	 * Set user input on System.in. Issue on multiple reads of System.in.
	 * Better to provide the InputStream to the GameManager.
	 * Ref. https://stackoverflow.com/questions/6415728/junit-testing-with-simulated-user-input
	 */
	@Deprecated
	private void setUserInput(String input) {
		final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(input.getBytes());
		System.setIn(byteArrayInputStream);
	}

	@Deprecated
	private void readUserInput(String input) {
		final String inputWithNewLine = input + NL;
		final InputStream systemInBackup = System.in;

		try {
			System.setIn(new ByteArrayInputStream(inputWithNewLine.getBytes()));
			Scanner scanner = new Scanner(System.in);
			System.out.println("Read user input: " + scanner.nextLine());
		} finally {
			System.setIn(systemInBackup);
		}
	}
}

package com.salemcreative;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

/**
 * The GameManager handles the logic of playing games of Monty Hall.
 * This includes games played by a user and games simulated by the computer.
 * 
 * @author Tobias
 *
 */
public class GameManager {
	
	private static final String NL = System.getProperty("line.separator");
	private static final int NR_OF_DOORS = 3;
	private static final int NR_OF_SIMULATIONS = 1000;
	
	private enum GameCommand {
		KEEP_DOOR("1"),
		SWITCH_DOOR("2");
		
		private String commandKey;
		
		private GameCommand(String input) {
			commandKey = input;
		}
		
		public static GameCommand fromString(String input) {
			if (input != null) {
				for (GameCommand c : GameCommand.values()) {
					if (input.equalsIgnoreCase(c.commandKey)) {
						return c;
					}
				}
			}
			throw new IllegalArgumentException("Given input: " +input+ " is not a valid GameCommand.");
		}
	}
	
	private GameCommand givenGameCommand;
	
	private GameSessionStatistics gameSessionStatistics = new GameSessionStatistics();
	
	private Set<Integer> doorSet;
	private Integer winningDoor = null;
	private Integer currentDoorChoice = null;
	private Integer openedDoor = null;
	private boolean doorSwitched = false;
	
	public GameSessionStatistics simulateGamesAlwaysKeepDoor() {
		System.out.println("=== [Simulating "+NR_OF_SIMULATIONS+ " Monty Hall Games by always keeping door] === "+NL);
		
		for (int i = 0; i < NR_OF_SIMULATIONS; i++) {
			simulateGameKeepDoor();
		}
		return gameSessionStatistics;
		//reportGameSessionResult();
	}
	
	public GameSessionStatistics simulateGamesAlwaysSwitchDoor() {
		System.out.println("=== [Simulating "+NR_OF_SIMULATIONS+ " Monty Hall Games by always switching door] === "+NL);
		
		for (int i = 0; i < NR_OF_SIMULATIONS; i++) {
			simulateGameSwitchDoor();
		}
		return gameSessionStatistics;
		//reportGameSessionResult();
	}
	
	private void simulateGameKeepDoor() {
		final boolean isInteractiveGame = false;
		
		setupGame();
		currentDoorChoice = NumberManager.getRandomIntExlusiveUpperBound(1, NR_OF_DOORS);
		openedDoor = openRandomNonPrizeDoor();
		keepDoor(isInteractiveGame);
		calculateGameResult();
		//reportGameResult(isInteractiveGame);
	}
	
	private void simulateGameSwitchDoor() {
		final boolean isInteractiveGame = false;
		
		setupGame();
		currentDoorChoice = NumberManager.getRandomIntExlusiveUpperBound(1, NR_OF_DOORS);
		openedDoor = openRandomNonPrizeDoor();
		switchDoor(isInteractiveGame);
		calculateGameResult();
		//reportGameResult(isInteractiveGame);
	}
	
	private void welcomeMessage() {
		System.out.println();
		System.out.println("Welcome to The Monty Hall Game!");
		System.out.println("===============================");
		System.out.println("You have " +NR_OF_DOORS+ " doors before you. One of them contains the grand prize!");
		System.out.println("The other doors each contain a goat.");
	}
	
	private void readInitialDoorSelection() {
		
		while (!isValidDoorSelection(currentDoorChoice)) {
			System.out.print("Please make your choice of door (1-"+NR_OF_DOORS+")> ");
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			try {
				currentDoorChoice = Integer.parseInt(reader.readLine());
			} catch(NumberFormatException e) {
				System.out.println("Please choose a number between 1 and " + NR_OF_DOORS);
				continue;
			} catch (IOException e) {
				System.err.println("Could not process input. Please try again." + e.getMessage());
				continue;
			} finally {
				try {
					reader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	private void readGameCommandSelection() {
		while (!isValidGameCommand(givenGameCommand)) {
			
			System.out.print("What is your choice (1 or 2)> ");
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			try {
				givenGameCommand = GameCommand.fromString(reader.readLine());
			} catch(IllegalArgumentException e) {
				System.out.println(e.getMessage());
				continue;
			} catch (IOException e) {
				System.err.println("Could not process input. Please try again. Excepion: " +e.getMessage());
				continue;
			} finally {
				try {
					reader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	public void play() {
		final boolean isInteractiveGame = true;
		
		setupGame();
		welcomeMessage();
		reportGameBoard();
		
		readInitialDoorSelection();
		System.out.println("Thank you. You have chosen door nr " +currentDoorChoice);
		reportGameBoard();
		
		System.out.println("The game host will now reveal one of the other doors.");
		openedDoor = openRandomNonPrizeDoor();
		
		System.out.println("Door nr " +openedDoor+ " was opened and contains a goat."+NL);
		reportGameBoard();
		
		System.out.println("Now to the big question: do you wish to KEEP your door choice, or SWITCH door?");
		System.out.println("1) Keep your current door choice.");
		System.out.println("2) Switch your current door choice." +NL);
		
		readGameCommandSelection();
		
		switch (givenGameCommand) {
			case KEEP_DOOR:
				keepDoor(isInteractiveGame);
				break;
			case SWITCH_DOOR:
				switchDoor(isInteractiveGame);
				break;
			default:
				break;
		}
		calculateGameResult();
		reportGameResult(isInteractiveGame);
		
		if (playAgain()) {
			System.out.println("Ok great, let's play again! :) " +NL);
			play();
		} else {
			reportGameSessionStatistics();
		}
	}
	
	private boolean playAgain() {
		System.out.print("Do you wish to play again? (y or n)> ");
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		try {
			String playAgainAnswer = reader.readLine();
			if (playAgainAnswer.equalsIgnoreCase("y")) {
				return true;
			}
		} catch (IOException e) {
			System.err.println("Could not process input. Please try again. Excepion: " +e.getMessage());
		}
		return false;
	}
	
	private Integer openRandomNonPrizeDoor() {
		// NOTE: from the set of all doors, select a random non-price door which is not already selected by the user.
		// Then select one remaining - always non-winning - door at random.
		
		Integer randomDoor = null;
		while (!isValidDoorToOpen(randomDoor)) {
			randomDoor = NumberManager.getRandomIntInclusiveUpperBound(1, doorSet.size());
		}
		
		return randomDoor;
	}
	
	private void setupGame() {
		doorSet = new HashSet<>();
		for (int i = 1; i <= NR_OF_DOORS; i++) {
			doorSet.add(i);
		}
		
		winningDoor = NumberManager.getRandomIntInclusiveUpperBound(1, 3);
		givenGameCommand = null;
		currentDoorChoice = null;
		openedDoor = null;
		doorSwitched = false;
	}
	
	private boolean isValidDoorSelection(Integer doorSelection) {
		return doorSelection != null && doorSelection >= 1 && doorSelection <= NR_OF_DOORS;
	}
	
	private boolean isValidDoorToOpen(Integer doorToOpen) {
		return doorToOpen != null && !doorToOpen.equals(currentDoorChoice) && !doorToOpen.equals(winningDoor);
	}
	
	private boolean isValidGameCommand(GameCommand command) {
		return command != null;
	}
	
	private void switchDoor(boolean isInteractiveGame) {
		doorSwitched = true;
		gameSessionStatistics.gamePlayedWithDoorSwitched();
		
		for (Integer door : doorSet) {
			if (!door.equals(currentDoorChoice) && !door.equals(openedDoor)) {
				currentDoorChoice = door;
				break;
			}
		}
		
		if (isInteractiveGame) {
			System.out.println("Switched doors. Your new door choice is " +currentDoorChoice);
		}
		
	}
	
	private void keepDoor(boolean isInteractiveGame) {
		doorSwitched = false;
		gameSessionStatistics.gamePlayedWithDoorKept();
		
		if (isInteractiveGame) {
			System.out.println("Ok, keeping your door choice of " +currentDoorChoice);
		}
	}
	
	private void reportGameBoard() {
		System.out.println("The game now has the following status: "+NL);
		
		for (int i = 1; i <= NR_OF_DOORS; i++) {
			System.out.print("Door " +i+ " ");
			if (currentDoorChoice != null && i == currentDoorChoice) {
				System.out.println("[Closed] <--- Chosen by you.");
			} else if (openedDoor != null && i == openedDoor) {
				System.out.println("<Opened> <--- Contains a goat.");
			} else {
				System.out.println("[Closed]");
			}
		}
		System.out.println();
	}
	
	private boolean isAWinningGame() {
		return currentDoorChoice.equals(winningDoor);
	}
	
	private void calculateGameResult() {
		
		if (isAWinningGame()) {
			
			if (doorSwitched) {
				gameSessionStatistics.gameWonWithDoorSwitched();
			} else {
				gameSessionStatistics.gameWonWithDoorKept();
			}
		}
	}
	
	private void reportGameResult(boolean isInteractiveGame) {
		System.out.println();
		System.out.println("Get ready for the game results!");
		System.out.println(NL+"*************** [DRUMROLL] ***************"+NL);
		System.out.println("Your door choice is: " +currentDoorChoice);
		System.out.println("The winning door is: " +winningDoor);
		System.out.println(NL+"******************************************");
		
		String tactics = doorSwitched ? "You switched door and " : "You kept the same door and ";
		
		if (isAWinningGame()) {
			System.out.println("Congratulations! "+tactics+ "have won this game! =)" +NL);
		} else {
			System.out.println("Sorry. " +tactics+ "have lost this game");
			System.out.println("Better (statistical?) luck next time!"+NL);
		}
	}
	
	private void reportGameSessionStatistics() {
		gameSessionStatistics.reportGameSessionStatistics();
	}
	
}

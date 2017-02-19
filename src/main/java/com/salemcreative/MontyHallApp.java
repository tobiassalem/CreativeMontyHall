package com.salemcreative;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Simple application to illustrate the Monty Hall problem.
 * 
 * @author Tobias
 *
 */
public class MontyHallApp {
	
	private static final String APP_NAME = "The Monty Hall Application";
	private static final String NL = System.getProperty("line.separator");
	
	private static AppCommand givenCommand;
	
	private enum AppCommand {
		PLAY_GAME("1"),
		SIMULATE_GAMES_ALWAYS_KEEP("2"),
		SIMULATE_GAMES_ALWAYS_SWITCH("3"),
		MOTIVATE_GAME_CHOICE("4"),
		MOTIVATE_GAME_CHOICE_EXTENTED("5"),
		QUIT("6");
		
		private String commandKey;
		
		private AppCommand(String input) {
			commandKey = input;
		}
		
		public static AppCommand fromString(String input) {
			if (input != null) {
				for (AppCommand c : AppCommand.values()) {
					if (input.equalsIgnoreCase(c.commandKey)) {
						return c;
					}
				}
			}
			throw new IllegalArgumentException("Given input: " +input+ " is not a valid AppComand.");
		}
	}
	
	private static final String GAME_DESC =
		"The Monty Hall Problem gets its name from the TV game show, 'Let's Make A Deal', hosted by Monty Hall. " +NL+
		"The scenario is such: you are given the opportunity to select one closed door of three, behind one of which " +NL+
		"there is a prize. The other two doors hide 'goats' (or some other such 'non-prize')." +NL+
		"Once you have made your selection, Monty Hall will open one of the remaining doors, "+NL+
		"revealing that it does not contain the prize." +NL+NL+
		"He then asks you if you would like to switch your selection to the other unopened door, " +NL+
		"or stay with your original choice. Here is the problem: " +NL+
		"Does it matter if you switch? Let's play the game to find out! " + NL;
	
	private static final String SCENARIO1 =
		"Scenario 1: " +NL+
		"============" +NL+
		"You happen to choose the door with the prize - a 1/3 probability. " +NL+
		"The game host then opens one of the doors with a goat (of which there are two in this scenario). " +NL+
		"You switch door - to the door with the other goat, and loose. " +NL+
		"Remember: the probability of this scenario is only 1/3 since " +NL+
		"when you choose one of the THREE closed doors the chance of selecting the prize is 1/3 " +NL+
		"Let's illustrate this Sceario: " +NL+
		"Door 1 [GOAT] 	<--- closed " +NL+
		"Door 2 [PRIZE] <--- chosen by you, chance 1/3 " +NL+
		"Door 3 [GOAT] 	<--- closed " +NL+NL+
		"After the game host opens one of the doors with a goat behind it: " +NL+
		"Door 1 [GOAT] 	<--- opened by the host. " +NL+
		"Door 2 [PRIZE] <--- first chosen by you. " +NL+
		"Door 3 [GOAT] 	<--- closed - switch results in a loss." +NL+NL;
	
	private static final String SCENARIO2 =
		"Scenario 2: " +NL+
		"============" +NL+
		"You happen to choose a door with a goat - a 2/3 probability. " +NL+
		"The game host then opens one of the other doors with a goat." +NL+
		"You switch door - to the door with the prize, and win. " +NL+
		"Remember: the probability of this type of win is 2/3 since " +NL+
		"when you choose one of the THREE closed doors the chance of selecting a Goat is 2/3 " +NL+
		"It does not matter which goat you select since the host will always open the door with the other goat. " +NL+NL+
		"Let's illustrate this Sceario: " +NL+
		"Door 1 [GOAT] 	<--- chosen by you " +NL+
		"Door 2 [PRIZE] <--- closed " +NL+
		"Door 3 [GOAT] 	<--- closed " +NL+NL+
		"After the game host opens one of the doors with a goat behind it: " +NL+
		"Door 1 [GOAT] 	<--- first chosen by you " +NL+
		"Door 2 [PRIZE] <--- closed - the host never opens the door with the prize - switch results in a WIN. " +NL+
		"Door 3 [GOAT] 	<--- opened - the host always opens a door with a goat. "+NL+NL;
	
	private static final String SCENARIO3 =
		"Scenario 3: " +NL+
		"============" +NL+
		"You happen to choose a door with a goat - a 2/3 probability. " +NL+
		"The game host then opens one of the other doors with a goat. " +NL+
		"You switch door - to the door with the prize, and win. " +NL+
		"Remember: the probability of this type of win is 2/3 since " +NL+
		"when you choose one of the THREE closed doors the chance of selecting a Goat is 2/3 " +NL+
		"It does not matter which goat you select since the host will always open the door with the OTHER goat. " +NL+NL+
		"Let's illustrate this Sceario: " +NL+
		"Door 1 [GOAT] 	<--- closed " +NL+
		"Door 2 [PRIZE] <--- closed " +NL+
		"Door 3 [GOAT] 	<--- chosen by you " +NL+NL+
		"After the game host opens one of the doors with a goat behind it: " +NL+
		"Door 1 [GOAT] 	<--- opened - the host always opens a door with a goat. " +NL+
		"Door 2 [PRIZE] <--- closed - the host never opens the door with the prize - switch results in a WIN. " +NL+
		"Door 3 [GOAT] 	<--- first chosen by you"+NL+NL;
	
	private static final String GAME_CHOICE_MOTIVATION =
		"In your initial choice of door you clearly have 1/3 (or 33,33...%) chance of selecting " +NL+
		"the door with the price, and thus 2/3 (or 66,66...%) chance of selecting a door with a goat. " +NL+
		"The game host KNOWS which door has the prize and NEVER will open the door with the prize "+NL+
		"- he will always open a goat door and never the prize door." +NL+
		"This means that unless you chose the door with the prize -which we already know is a 1/3 chance- the remaining closed door " +NL+
		"actually has the grand prize. This with a probability of 2/3. "+NL+
		"Thus by SWITCHING door you have a 2/3 probability of winning the prize! " +NL+NL+
		"You may think to yourself 'well I don't know if that's true or not, so lets go through each scenario step by step "+NL+
		"so you can find out for yourself that it is true. " +NL+
		SCENARIO1 + NL + SCENARIO2 + NL + SCENARIO3 + NL +
		"In summary: two of the three possible scenarios results in a WIN if you choose to SWITCH choice after the game hosts opens " +
		"one of the remaining non-prize doors. "+NL+
		"In other words, if you want a 2/3 or 66,6666...% chance of winning the Monty Hall Game  " +NL+
		"- ALWAYS switch door when the option is given. =) ";
	
	private static final String EXTENDED_GAME_CHOICE_MOTIVATION =
		"Consider the scenario where there are 1000 doors, one with a prize." +NL+
		"You still select ONE of them, with a probality of 1/1000 to select the door with the prize." +NL+
		"The game host then opens 998 of the doors revailing goats behind all of them." +NL+
		"If you choose to keep your door, what are the odds when you had 1000 doors to chose from, "+NL+
		"that you managed to pick the door with the prize? You're right, only 1 in 1000." +NL+NL+
		"Now that Monty Hall has opened 998 of the other doors for you, revealing they have no prize, " +NL+
		"the odds for the prize behind the remaining closed door that you did not pick are pretty good. " +NL+
		"In fact they are 999/1000, since the only way for that door NOT to contain the prize " +NL+
		"is if you by chance originally picked the door with the prize. "+NL+
		"And we already know those odds are 1/1000." +NL+NL+
		"It does not really matter that we have 1000 doors in this scenario - the principle is the same." +NL+NL+
		"Remember the following: " +NL+
		" * the game hosts ALWAYS opens doors with goats/non-prizes." +NL+
		" * the game hosts NEVER opens the door with the prize, that door is always kept closed." +NL+
		" * except for your already chosen door, exactly one other door is kept closed." +NL+
		" * the process must be seen as whole, when you FIRST chose your door what is the probability THEN for that door to: " +NL+
		"   a) contain the one single prize? It is: 1/x (where x is the number of doors, e.g. 1/3)" + NL+
		"   b) contain a goat? (x-1)/x, where X is the number of doors, (e.g. 2/3)." +NL+
		" * Since the game is structured the way it is, the odds for switching to the winning door is also (x-1)/x ";
	;
	
	private static final String MAIN_MENU =
		"Please make your choice (by pressing the corresponding number): " +NL+
		"================================================================" +NL+
		"1) Play a game of Monty Hall " +NL+
		"2) Simulate lots of Monty Hall games where the door is always KEPT " +NL+
		"3) Simulate lots of Monty Hall games where the door is always SWITCHED " +NL+
		"4) Motivate the best game choice " +NL+
		"5) Extended game choice motivation"+NL+
		"6) Quit the application "+NL+NL;
	
	private static final String PROMPT = "> ";
	
	private static final String FINAL_WORDS = "Ok quitting "+APP_NAME+ "." +NL+
		"Have a nice day, and remember: if a thing is humanly possible, consider it to be within your reach!" +NL;
	
	public static void main(String[] args) {
		System.out.println();
		System.out.println("Welcome to "+APP_NAME+"!");
		System.out.println("========================================\n");
		System.out.println(GAME_DESC);
		
		while (givenCommand != AppCommand.QUIT) {
			System.out.println();
			System.out.println(MAIN_MENU);
			System.out.print(PROMPT);
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			try {
				givenCommand = AppCommand.fromString(reader.readLine());
			} catch(IllegalArgumentException e) {
				System.out.println(e.getMessage() + " Please review the menu and try again.");
				continue;
			} catch (IOException e) {
				System.err.println("Could not process input. Please try again.");
				continue;
			}
			
			switch (givenCommand) {
				case PLAY_GAME:
					playMontyHall();
					break;
				case SIMULATE_GAMES_ALWAYS_KEEP:
					simulateMontyHallGamesAlwaysKeepDoor();
					break;
				case SIMULATE_GAMES_ALWAYS_SWITCH:
					simulateMontyHallGamesAlwaysSwitchDoor();
					break;
				case MOTIVATE_GAME_CHOICE:
					motivateGameChoice();
					break;
				case MOTIVATE_GAME_CHOICE_EXTENTED:
					motivateGameChoiceExtended();
					break;
				case QUIT:
					quitApplication();
					break;
				default:
					break;
			}
		}
		
	}
	
	public static void playMontyHall() {
		GameManager gameManager = new GameManager();
		gameManager.play();
	}
	
	public static void simulateMontyHallGamesAlwaysKeepDoor() {
		GameManager gameManager = new GameManager();
		GameSessionStatistics gameSessionStats = gameManager.simulateGamesAlwaysKeepDoor();
		gameSessionStats.reportGameSessionStatistics();
		
	}
	
	public static void simulateMontyHallGamesAlwaysSwitchDoor() {
		GameManager gameManager = new GameManager();
		GameSessionStatistics gameSessionStats = gameManager.simulateGamesAlwaysSwitchDoor();
		gameSessionStats.reportGameSessionStatistics();
	}
	
	public static void motivateGameChoice() {
		System.out.println(GAME_CHOICE_MOTIVATION);
	}
	
	public static void motivateGameChoiceExtended() {
		System.out.println(EXTENDED_GAME_CHOICE_MOTIVATION);
	}
	
	private static void quitApplication() {
		System.out.println(FINAL_WORDS);
		System.exit(0);
	}
	
}

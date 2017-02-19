package com.salemcreative;

import java.text.DecimalFormat;

public class GameSessionStatistics {
	
	private static final String NL = System.getProperty("line.separator");
	
	private int nrOfGamesWithDoorKept = 0;
	private int nrOfGamesWithDoorSwitched = 0;
	
	private int nrOfGamesWonWithDoorKept = 0;
	private int nrOfGamesWonWithDoorSwitched = 0;
	
	public void gamePlayedWithDoorKept() {
		nrOfGamesWithDoorKept++;
	}
	
	public void gamePlayedWithDoorSwitched() {
		nrOfGamesWithDoorSwitched++;
	}
	
	public void gameWonWithDoorKept() {
		nrOfGamesWonWithDoorKept++;
	}
	
	public void gameWonWithDoorSwitched() {
		nrOfGamesWonWithDoorSwitched++;
	}
	
	public double getPercentageWonWithDoorKept() {
		return NumberManager.calculatPercentage(nrOfGamesWonWithDoorKept,nrOfGamesWithDoorKept);
	}
	
	public double getPercentageWonWithDoorSwitched() {
		return NumberManager.calculatPercentage(nrOfGamesWonWithDoorSwitched,nrOfGamesWithDoorSwitched);
	}
	
	public void reportGameSessionStatistics() {
		
		int totalNrOfGames = nrOfGamesWithDoorKept + nrOfGamesWithDoorSwitched;
		if (totalNrOfGames == 0) {
			System.out.println("No game session statistics to report");
			return;
		}
		
		double percentageWonWithDoorKept = getPercentageWonWithDoorKept();
		double percentageWonWithDoorSwitched = getPercentageWonWithDoorSwitched();
		DecimalFormat df = new DecimalFormat("0.00");
		
		System.out.println();
		System.out.println("The statistics for this Game Session is the following:");
		System.out.println("=======================================================");
		System.out.println("Total number of games played: " +totalNrOfGames+ NL);
		
		System.out.println("Games played with the door choice KEPT: " +nrOfGamesWithDoorKept);
		System.out.println("Percentage of games won when keeping door: " + df.format(percentageWonWithDoorKept) + " %");
		System.out.println();
		
		System.out.println("Games played with the door choice SWITCHED: " +nrOfGamesWithDoorSwitched);
		System.out.println("Percentage of games won when switching door: " +df.format(percentageWonWithDoorSwitched) + " %");
		System.out.println();
		
		System.out.println("Now, do you think it is better to keep or to switch door?");
		System.out.println("Remember that the statistical significance is more clear the more games you play.");
	}
	
}

package com.bowling;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

public class BowlingAlley {

	int numberOfPlayers = 0;
	int numberOfLanes = 0;
	BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	GameContext gameContext = null;

	public BowlingAlley() throws IOException {

		// Bowling alley, initialize lanes and game.
		System.out.println("Game initializing ...");
		System.out.println("Enter number of lanes");

		numberOfLanes = Integer.parseInt(in.readLine());
		gameContext = GameContext.init(numberOfLanes);

		while (true) {

			System.out.println("1. Start new game. \n2. Enter game score.\nEnter choice : ");
			int choice = Integer.parseInt(in.readLine());

			switch (choice) {
			case 1:
				startNewGame();
				break;
			case 2:
				addScore();
				break;
			default:
				System.out.println("Invalid command.");
				break;
			}
		}
	}

	private void startNewGame() throws IOException {
		System.out.println("Enter players names ( space sepated )");
		String players = in.readLine();
		int allocatedLane = gameContext.startGame(players.split(" "));
		if (allocatedLane == -1) {
			System.err.println("Please wait !!!");
			return;
		}
		System.out.println("Allocated lane = " + allocatedLane);
		System.out.println(
				"Next Player on Lane: " + allocatedLane + " is : " + gameContext.getGame(allocatedLane).whoIsNext());
	}

	private void addScore() throws IOException {
		System.out.println("Enter lane");
		int lane = Integer.parseInt(in.readLine());
		System.out.println("Enter score in format : <strike>/<5 spare>/<5 5>");

		for (int i = 0; i < gameContext.getGame(lane).getNumberOfPlayers(); i++) {
			System.out.println("Enter score for " + gameContext.getGame(lane).whoIsNext());
			String input = in.readLine();
			gameContext.getGame(lane).updateScore(input);
		}

		displayScore(lane);
	}

	private void displayScore(int lane) {

		Game game = gameContext.getGame(lane);
		String format = "|%1$-10s|%2$-12s|%3$-7s|%4$-12s|%5$-10s\n";
		for (Map.Entry<String, List<Frame>> entry : game.getGameData().entrySet()) {
			System.out.println("Player: " + entry.getKey());
			System.out.format(format, "Frames", "First", "Second", "FrameScore", "Total");
			int count = 1;
			for (Frame frame : entry.getValue()) {

				String temp = (frame.getFirst() + frame.getSecond() == 10) ? "/" : Integer.toString(frame.getSecond());

				System.out.format(format, "Frame-" + count++, (frame.getFirst() == 10) ? "X" : frame.getFirst(),
						(temp.equals("-1")) ? "" : temp, (frame.getFrameScore() == -1) ? "" : frame.getFrameScore(),
						(frame.getTotalScore() == -1) ? "" : frame.getTotalScore());
			}
			System.out.println("");
		}

	}

	public static void main(String[] args) throws IOException {
		new BowlingAlley();
	}
}

package com.bowling;

public class GameContext {

	private static GameContext gameContext = null;
	private Game[] lanes = null;

	private GameContext(int numberOfLanes) {
		lanes = new Game[numberOfLanes];
	}

	public static GameContext init(int numberOfLanes) {
		if (gameContext == null) {
			gameContext = new GameContext(numberOfLanes);
		}
		return gameContext;
	}

	public static GameContext getContext() {
		return gameContext;
	}

	public int startGame(String[] players) {
		int lane = getFreeLane();
		if (lane == -1) {
			System.err.println("No lane available. Wait !!!");
			return -1;
		}

		lanes[lane] = new Game(players);
		return lane;
	}

	public Game getGame(int lane) {
		return lanes[lane];
	}

	private int getFreeLane() {
		for (int i = 0; i < lanes.length; i++) {
			if (lanes[i] == null)
				return i;
		}
		return -1;
	}

}

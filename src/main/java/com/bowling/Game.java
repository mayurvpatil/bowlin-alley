package com.bowling;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Game {

	private Map<String, List<Frame>> gameData = null;
	private String[] players = null;
	private int currentPlayer = 0;
	private int frame = 0;

	public Game(String[] players) {

		gameData = new LinkedHashMap<String, List<Frame>>(players.length);

		for (String player : players) {
			List<Frame> tmp = new ArrayList<Frame>();
			for (int i = 0; i < 10; i++) {
				tmp.add(new Frame());
			}
			gameData.put(player, tmp);
		}

		this.players = players;
	}

	public String whoIsNext() {
		return players[currentPlayer];
	}

	public int getNumberOfPlayers() {
		return players.length;
	}

	public void updateScore(String score) {

		String[] tok = score.split(" ");
		Frame currentFrame = gameData.get(players[currentPlayer]).get(frame);

		int first = -1;
		int second = -1;

		if (tok[0].strip().equalsIgnoreCase("strike")) {
			first = 10;
		} else if (tok.length >= 1 && tok[1].strip().equalsIgnoreCase("spare")) {
			first = Integer.parseInt(tok[0]);
			second = 10 - first;
		} else {
			first = Integer.parseInt(tok[0]);
			second = Integer.parseInt(tok[1]);
			currentFrame.setFrameScore(first + second);
		}

		currentFrame.setFirst(first);
		currentFrame.setSecond(second);

		updatePreviousFrames(currentFrame, first, second);

		if (currentPlayer == players.length - 1)
			frame++;
		currentPlayer = ++currentPlayer % players.length;

	}

	private void updatePreviousFrames(Frame curreFrame, int currentFirst, int currentSecond) {

		Frame lastFrame = null;
		Frame secondLastFrame = null;
		// Update spare - previous frame.

		if ((frame - 1) >= 0) {
			lastFrame = gameData.get(players[currentPlayer]).get(frame - 1);
			// Check if last frame strike and next frame is open frame.
			if (lastFrame.getFirst() == 10) {
				if (currentFirst != -1 && currentSecond != -1) {
					lastFrame.setFrameScore(currentFirst + currentSecond);
				}
				// last frame is spare then add current first throw score.
			} else if (lastFrame.getSecond() == 10 || (lastFrame.getFirst() + lastFrame.getSecond()) == 10) {
				lastFrame.setFrameScore(10 + currentFirst);
				// Total score needs to be updated
			}

			// Update spike - second last frame.
			if (frame >= 2) {
				secondLastFrame = gameData.get(players[currentPlayer]).get(frame - 2);
				if (secondLastFrame.getFirst() == 10) {

					int frameCount = 10;
					if (lastFrame.getFirst() == 10)
						frameCount += (10 + currentFirst);
					else if (lastFrame.getSecond() == 10 || (lastFrame.getFirst() + lastFrame.getSecond() == 10))
						frameCount += 10;

					// Third condition handled in spare udpade.

					secondLastFrame.setFrameScore(frameCount);

				}

			}

			/*
			 * int totalScore = 0; if((frame >= 3)) { totalScore =
			 * gameData.get(players[currentPlayer]).get(frame - 3).getTotalScore(); } else {
			 * totalScore = 0; }
			 * 
			 * if(secondLastFrame != null) secondLastFrame.setTotalScore(totalScore +
			 * secondLastFrame.getFrameScore());
			 * 
			 * if(lastFrame.getFrameScore() != -1) {
			 * lastFrame.setTotalScore(secondLastFrame.getTotalScore() +
			 * lastFrame.getFrameScore()); }
			 */
		}

	}

	public Map<String, List<Frame>> getGameData() {
		return gameData;
	}

}

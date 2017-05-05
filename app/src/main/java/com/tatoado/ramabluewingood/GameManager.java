package com.tatoado.ramabluewingood;

/**
 * Created by juandavid on 4/05/17.
 */

@SuppressWarnings("WeakerAccess")
class GameManager {

	private static GameManager instance = new GameManager();
	private final Player[] player = new Player[2];
	public boolean firstPlayerTurn = true;

	private GameManager() {
		player[0] = new Player();
		player[1] = new Player();
	}

	public static GameManager getInstance() {
		return instance;
	}

	public void resetGame() {
		player[0].resetPoints();
		player[1].resetPoints();
	}

	public int getPlayerPoints(int id) {
		return id == 1 || id == 2 ? player[id - 1].getPoints() : -1;
	}

	public void addPlayerPoint(boolean point) {
		if (point)
			player[firstPlayerTurn ? 0 : 1].addPoint();
		firstPlayerTurn = !firstPlayerTurn;
	}

	private class Player {
		private int points;

		Player() {
		}

		int getPoints() {
			return points;
		}

		void addPoint() {
			this.points++;
		}

		void resetPoints() {
			this.points = 0;
		}
	}
}

package com.tatoado.ramabluewingood;

/**
 * Created by juandavid on 4/05/17.
 */
class GameManager {

	private static GameManager instance = new GameManager();//patron de diseño singleton
	private final Player[] player = new Player[2]; //jugadores de la aplicación
	boolean firstPlayerTurn = true; //determina de quién es el turno en cada momento

	/**
	 * se crean los jugadores
	 */
	private GameManager() {
		player[0] = new Player();
		player[1] = new Player();
	}

	static GameManager getInstance() {
		return instance;
	}

	/**
	 * elimina los puntos de todos los jugadores
	 */
	void resetGame() {
		player[0].resetPoints();
		player[1].resetPoints();
	}

	/**
	 * devuelve el puntaje del jugador seleccionado
	 *
	 * @param id jugador de quien se quiere saber el puntaje
	 * @return puntaje del jugador seleccionado
	 */
	int getPlayerPoints(int id) {
		return id == 1 || id == 2 ? player[id - 1].getPoints() : -1;
	}

	/**
	 * agrega punto al jugador actual
	 *
	 * @param point determina si hay punto o no
	 */
	void addPlayerPoint(boolean point) {
		if (point)
			player[firstPlayerTurn ? 0 : 1].addPoint();
		firstPlayerTurn = !firstPlayerTurn;
	}

	/**
	 * agrega punto al jugador seleccionado
	 *
	 * @param id    identificador del jugador a quien se desea agregar punto
	 * @param point determina si hay punto o no
	 */
	void addPlayerPoint(int id, boolean point) {
		if (point)
			player[id - 1].addPoint();
	}

	/**
	 * alterna el turno entre los jugadores
	 */
	void changePlayer() {
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

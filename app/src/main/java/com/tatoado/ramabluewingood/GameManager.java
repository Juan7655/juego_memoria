package com.tatoado.ramabluewingood;

/**
 * Created by juandavid on 4/05/17.
 */
class GameManager {

	private static GameManager instance = new GameManager();//patron de diseño singleton
	private final Player player = new Player(); //jugadores de la aplicación
	boolean firstPlayerTurn = true; //determina de quién es el turno en cada momento

	/**
	 * se crean los jugadores
	 */
	private GameManager() {
	}

	static GameManager getInstance() {
		return instance;
	}

	/**
	 * elimina los puntos de todos los jugadores
	 */
	void resetGame() {
		player.resetPoints();
	}

	/**
	 * devuelve el puntaje del jugador seleccionado
	 *
	 * @return puntaje del jugador seleccionado
	 */
	int getPlayerPoints() {
		return player.getPoints();
	}

	/**
	 * agrega punto al jugador actual
	 *
	 * @param point determina si hay punto o no
	 */
	void addPlayerPoint(boolean point) {
		if (point)
			player.addPoint();
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
			player.addPoint();
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

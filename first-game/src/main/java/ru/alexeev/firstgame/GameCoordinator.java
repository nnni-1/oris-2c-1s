package ru.alexeev.firstgame;

import java.util.ArrayList;
import java.util.List;

public class GameCoordinator {

    private final Player player1;
    private final Player player2;
    private Player currentPlayer;
    private List<PlayerController> players = new ArrayList<>();

    public GameCoordinator(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        this.currentPlayer = player1; // Изначально ходит первый игрок
    }

    public boolean isCurrentPlayer(Player player) {
        return currentPlayer == player;
    }

    public void nextTurn() {
        currentPlayer = (currentPlayer == player1) ? player2 : player1;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void addPlayer(PlayerController playerController) {
        players.add(playerController);
    }

    public void notifyPlayers() {
        for (PlayerController player : players) {
            player.updateTurn();
        }
    }

    public void endGame(Player winner) {
        System.out.println("Победитель: " + winner.nameProperty().get());
        System.exit(0); // Завершение игры
    }
}

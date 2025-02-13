package ru.alexeev.firstgame;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;


public class PlayerController {

    @FXML
    private Label playerNameLabel;

    @FXML
    private ProgressBar hpBar;

    @FXML
    private Label hpLabel;

    @FXML
    private Button attackButton;

    private Player player;
    private Player opponent;
    private GameCoordinator gameCoordinator;


    public void initializePlayer(Player player, Player opponent, GameCoordinator gameCoordinator) {
        this.player = player;
        this.opponent = opponent;
        this.gameCoordinator = gameCoordinator;

        playerNameLabel.textProperty().bind(player.nameProperty());
        hpBar.progressProperty().bind(player.hpProperty().divide(100.0));
        hpLabel.textProperty().bind(player.hpProperty().asString("HP: %d"));

        gameCoordinator.addPlayer(this);

    }

    @FXML
    private void attack() {
        if (gameCoordinator.isCurrentPlayer(player)) {
            int damage = player.attack();
            opponent.takeDamage(damage);
            gameCoordinator.nextTurn();
            if (!opponent.isAlive()) {
                attackButton.setDisable(true);
                gameCoordinator.endGame(player);
            } else {
                gameCoordinator.notifyPlayers();
            }
        }
    }

    public void updateTurn() {
        attackButton.setDisable(!gameCoordinator.isCurrentPlayer(player));
    }
}


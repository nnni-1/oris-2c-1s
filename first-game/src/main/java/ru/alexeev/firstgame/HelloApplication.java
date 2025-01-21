package ru.alexeev.firstgame;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HelloApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Player player1 = new Player("nikita", 100, 15);
        Player player2 = new Player("arsen", 100, 15);
        GameCoordinator gameCoordinator = new GameCoordinator(player1, player2);

        FXMLLoader loader1 = new FXMLLoader(getClass().getResource("player_window.fxml"));
        Scene scene1 = new Scene(loader1.load());
        PlayerController controller1 = loader1.getController();
        controller1.initializePlayer(player1, player2, gameCoordinator);

        Stage stage1 = new Stage();
        stage1.setTitle("Игрок 1");
        stage1.setScene(scene1);
        stage1.show();

        FXMLLoader loader2 = new FXMLLoader(getClass().getResource("player_window.fxml"));
        Scene scene2 = new Scene(loader2.load());
        PlayerController controller2 = loader2.getController();
        controller2.initializePlayer(player2, player1, gameCoordinator);

        Stage stage2 = new Stage();
        stage2.setTitle("Игрок 2");
        stage2.setScene(scene2);
        stage2.show();

        controller1.updateTurn();
        controller2.updateTurn();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

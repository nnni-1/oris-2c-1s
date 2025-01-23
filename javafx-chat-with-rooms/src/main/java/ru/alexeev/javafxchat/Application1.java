package ru.alexeev.javafxchat;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class Application1 extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        ApplicationUtils.setStage(stage);
        ApplicationUtils.showWindow("first-page.fxml", "Подключение к лобби");
    }

    public static void main(String[] args) {
        launch();
    }
}
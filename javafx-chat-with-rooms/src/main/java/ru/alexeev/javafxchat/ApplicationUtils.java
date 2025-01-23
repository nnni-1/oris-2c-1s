package ru.alexeev.javafxchat;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ApplicationUtils {

    private static Stage stage;

    public static void setStage(Stage stage) {
        ApplicationUtils.stage = stage;
    }

    public static void showWindow(String fxmlPath, String title) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application1.class.getResource(fxmlPath));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);

        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }
}

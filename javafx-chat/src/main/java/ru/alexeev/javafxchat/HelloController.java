package ru.alexeev.javafxchat;

import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class HelloController {
    @FXML
    public ScrollPane scrollPane;
    @FXML
    private TextField inputField;
    @FXML
    private VBox messageBox;

    @FXML
    private void sendMessage() {
        String message = inputField.getText().trim();
        if (!message.isEmpty()) {
            Text messageText = new Text("сообщение от пользователя " + message);
            messageBox.getChildren().add(messageText);

            inputField.clear();
        }
    }
}
package ru.alexeev.javafxchat;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import ru.alexeev.javafxchat.model.Code;
import ru.alexeev.javafxchat.model.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Map;

public class RoomController {

    private PrintWriter writer;
    private BufferedReader reader;
    private ObjectMapper mapper;

    @FXML
    private TextField roomIdTextField;

    @FXML
    private Button connectButton;

    @FXML
    private AnchorPane contentPane;

    @FXML
    private Label serverMessageLabel;

    @FXML
    public void initialize() {
        mapper = new ObjectMapper();
        connectButton.setOnAction(event -> {
            try {
                handleConnectButtonAction();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        try {
            Socket socket = SocketSource.getSocket();
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void handleConnectButtonAction() throws IOException {
        String roomId = roomIdTextField.getText().trim();
        if (!roomId.isEmpty()) {

            System.out.println("Попытка подключения к комнате с ID: " + roomId);

            sendMessage(roomId);
        } else {
            System.out.println("Пожалуйста, введите ID комнаты.");
        }
    }

    private void loadPage(String page){
        try{
            AnchorPane nextPage = FXMLLoader.load(getClass().getResource(page));
            contentPane.getChildren().clear();
            contentPane.getChildren().add(nextPage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendMessage(String roomId) {
        String message = roomIdTextField.getText().trim();
        Message request = new Message(Code.JOIN_LOBBY, Map.of("roomId", roomId));
        if (!message.isEmpty() && message.matches("^\\d+$")) {
            try {
                writer.println(mapper.writeValueAsString(request));
                roomIdTextField.clear();
                ApplicationUtils.showWindow("hello-view.fxml", "чатик");
            } catch (IOException e){
                throw new RuntimeException("Cannot send request to server or serialize message", e);
            }
        } else {
            serverMessageLabel.setText("введите корректный числовой ID комнаты");
        }
    }
}

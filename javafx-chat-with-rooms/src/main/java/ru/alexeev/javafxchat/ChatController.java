package ru.alexeev.javafxchat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import ru.alexeev.javafxchat.model.Code;
import ru.alexeev.javafxchat.model.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Map;

public class ChatController {
    //private static final String SERVER_HOST = "192.168.106.47";

    private PrintWriter writer;
    private ObjectMapper mapper = new ObjectMapper();


    @FXML
    public ScrollPane scrollPane;

    @FXML
    private TextArea inputField;

    @FXML
    private VBox messageBox;

    @FXML
    private ListView<Text> messageList;

    @FXML
    public void initialize() {
        connectToServer();
    }

    private int roomId;

    private void connectToServer(){
        try {
            Socket socket = SocketSource.getSocket();
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);

            //поток отвечает за чтение буффера сервера, печатает нам смски в окно
            new Thread(() -> {
                try {
                    String message;
                    while ((message = in.readLine()) != null) {
                        String finalMessage = message;
                        Platform.runLater(() -> addMessage(finalMessage));
                    }
                } catch (IOException e) {
                    System.err.println("Ошибка чтения сообщений: " + e.getMessage());
                }
            }).start();

        } catch (IOException e) {
            System.err.println("Ошибка клиента: " + e.getMessage());
        }
    }

    @FXML
    private void sendMessage() {
        String message = inputField.getText().trim();
        Message request = new Message(Code.SEND_MESSAGE, Map.of("message", message));
        if (!message.isEmpty()) {
            try {
                writer.println(mapper.writeValueAsString(request));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            inputField.clear();
        }
    }

    private void addMessage(String message) {
        Text text = new Text(message);
//        messageBox.getChildren().add(text);
        messageList.getItems().add(text);
    }

    @FXML
    public void onKeyPressed(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            sendMessage();
        }
    }

}


package ru.alexeev.javafxchat;

import com.fasterxml.jackson.databind.ObjectMapper;
import jdk.jfr.Frequency;
import ru.alexeev.javafxchat.model.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private final int userId;
    private ObjectMapper mapper;
    private int currentRoomId;

    public ClientHandler(Socket socket, int userId) {
        this.socket = socket;
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            System.out.println("Ошибка в регистрации клиента");
        }
        this.userId = userId;
        mapper = new ObjectMapper();
    }
    //поток отвечает за отправку написанных пользователем сообщений на сервер
    @Override
    public void run() {
        while (true) {
            try {
                out.println("Добро пожаловать в приложение! Ваш ID: " + userId);

                String message;
                while ((message = in.readLine()) != null) {
                    //здесь происходит обработка всего сообщения в зависимости от кода сообщения
                    System.out.println(message);
                    Message request = mapper.readValue(message, Message.class);

                    switch (request.getCode()){
                        case JOIN_LOBBY -> {
                            currentRoomId = Integer
                                    .parseInt(request.
                                    getParameters()
                                    .get("roomId"));
                            ChatServer.connectClient(this, currentRoomId);
                            ChatServer.broadcastMessage("Пользователь " + userId + " подключился", currentRoomId);
                        }
                        case SEND_MESSAGE -> ChatServer
                                .broadcastMessage("Пользователь с ID " + userId + ": " + request
                                .getParameters()
                                .get("message"), currentRoomId);
                    }
                }
            } catch (IOException e) {
                //System.err.println("Ошибка в обработке клиента: " + e.getMessage());
                throw new RuntimeException(e);
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    System.err.println("Ошибка при закрытии соединения: " + e.getMessage());
                }
                ChatServer.removeClient(this);
            }
        }
    }

    void printMessage(String message) {
        out.println(message);
    }

    public int getUserId(){
        return userId;
    }
}

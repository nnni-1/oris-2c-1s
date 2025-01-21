package ru.alexeev.javafxchat;

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

    public ClientHandler(Socket socket, int userId) {
        this.socket = socket;
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            System.out.println("Ошибка в регистрации клиента");
        }
        this.userId = userId;
    }
    //поток отвечает за отправку написанных пользователем сообщений на сервер
    @Override
    public void run() {
        while (true) {
            try {
                out.println("Добро пожаловать в чат! Ваш ID: " + userId);
                ChatServer.broadcastMessage("Пользователь " + userId + " подключился к чату", this);
                String message;
                while ((message = in.readLine()) != null) {
                    System.out.println(message);
                    ChatServer.broadcastMessage("Пользователь " + userId + " :" + message, this);
                }
            } catch (IOException e) {
                System.err.println("Ошибка в обработке клиента: " + e.getMessage());
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
}

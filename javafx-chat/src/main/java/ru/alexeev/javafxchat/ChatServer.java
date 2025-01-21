package ru.alexeev.javafxchat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ChatServer {
    private static final int PORT = 12346;
    private static final List<ClientHandler> CLIENTS = new ArrayList<>();
    private static final AtomicInteger USER_ID = new AtomicInteger(1);

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Сервер запущен на порту: " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Новый клиент подключился: " + clientSocket.getInetAddress());

                int id = USER_ID.getAndIncrement();
                ClientHandler clientHandler = new ClientHandler(clientSocket, id);
                CLIENTS.add(clientHandler);
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            System.err.println("Ошибка сервера: " + e.getMessage());
        }
    }

    static void broadcastMessage(String message, ClientHandler sender) {
        synchronized (CLIENTS) {
            for (ClientHandler client : CLIENTS) {
                client.printMessage(message);
            }
        }
    }

    static void removeClient(ClientHandler clientHandler) {
        synchronized (CLIENTS) {
            CLIENTS.remove(clientHandler);
        }
    }

}
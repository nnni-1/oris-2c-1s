package ru.alexeev.javafxchat;

import ru.alexeev.javafxchat.model.Lobby;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class ChatServer {
    private static final int PORT = 12346;
    private static final List<ClientHandler> CLIENTS = new ArrayList<>();
    private static final AtomicInteger USER_ID = new AtomicInteger(1);
    private static final Map<Integer, Lobby> lobbies = new HashMap<>();

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

    static void broadcastMessage(String message, int roomId) {
        synchronized (lobbies.get(roomId)) {
            for (ClientHandler clientHandler : lobbies.get(roomId).getPlayers().values()) {
                clientHandler.printMessage(message);
            }
        }
    }

    static void removeClient(ClientHandler clientHandler) {
        synchronized (CLIENTS) {
            CLIENTS.remove(clientHandler);
        }
    }

    static void createLobby(int roomId){
        //TODO WARNING ХАРДКОД
        synchronized (lobbies) {
            Lobby lobby = new Lobby();
            lobby.setId(roomId);
            lobbies.put(roomId, lobby);
        }
    }

    static void connectClient(ClientHandler clientHandler, int roomId) {
        synchronized (lobbies) {
            if (!lobbies.containsKey(roomId)) {
                createLobby(roomId);
            }
            lobbies.get(roomId).getPlayers().put(clientHandler.getUserId(), clientHandler);
        }
    }

}
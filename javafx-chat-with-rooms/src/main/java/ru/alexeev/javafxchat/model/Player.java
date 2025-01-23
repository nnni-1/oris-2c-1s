package ru.alexeev.javafxchat.model;

import java.net.Socket;

public class Player {
    private final int id;
    private final String name;
    private final Socket socket;
    public Player(int id, String name, Socket socket) {
        this.id = id;
        this.name = name;
        this.socket = socket;
    }

    public int getId() {
        return id;
    }

    public Socket getSocket() {
        return socket;
    }

    public String getName() {
        return name;
    }
}

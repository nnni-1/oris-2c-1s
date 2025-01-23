package ru.alexeev.javafxchat.model;

import ru.alexeev.javafxchat.ClientHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Lobby {
    private int id;
    private Map<Integer, ClientHandler> players;

    public Lobby(){
        players = new HashMap<>();
        id = new Random().nextInt(90000) + 10000;
    }

    public void addPlayer(ClientHandler clientHandler){
        players.put(clientHandler.getUserId(), clientHandler);
    }

    public ClientHandler getPlayer(int id){
        return players.get(id);
    }

    public int getId() {
        return id;
    }

    public Map<Integer, ClientHandler> getPlayers() {
        return players;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void nextTurn(){
        //TODO реализовать логику смены очереди хода
    }

    public void broadcast(Message request){
        //TODO реализовать отправку изменения состояние лобби на сервер
    }

    public Player getCurrentPlayer(){
        return null;
        //TODO реализовать получение следующего по ходу игрока
    }

}

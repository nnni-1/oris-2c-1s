package ru.alexeev.javafxchat.model;

import java.util.Arrays;

public enum Code {
    DEAL_CARDS,
    MOVE,
    JOIN_LOBBY,
    SELECT_LOBBY,
    CREATE_LOBBY,
    LOBBY_CREATED,
    PLAYER_JOINED,
    MOVE_SUCCESS,
    ERROR,
    SEND_MESSAGE;

    public static int codeToInt(String code) {
        return valueOf(code.toUpperCase()).ordinal();
    }

    public static Code intToCode(int code) {
        return Arrays.stream(Code.values())
                .filter(c -> c.ordinal() == code)
                .findAny()
                .orElse(null);
    }
}

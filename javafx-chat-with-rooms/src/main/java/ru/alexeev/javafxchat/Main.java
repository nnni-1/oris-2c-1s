package ru.alexeev.javafxchat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.alexeev.javafxchat.model.Code;
import ru.alexeev.javafxchat.model.Message;

import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Message message = new Message(Code.SEND_MESSAGE, Map.of("message", "hi"));
        ObjectMapper mapper = new ObjectMapper();
        try {
            System.out.println(mapper.writeValueAsString(message));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}

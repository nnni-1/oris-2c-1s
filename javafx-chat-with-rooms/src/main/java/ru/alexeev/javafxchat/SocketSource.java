package ru.alexeev.javafxchat;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Properties;

public class SocketSource {
    private static Properties properties;
    private static Socket socket = null;
    private final static String PORT_KEY = "PORT";
    private final static String HOST_KEY = "HOST";

    public static Socket getSocket(){
        if (socket == null || !socket.isConnected() || socket.isClosed()){
            loadProperties();
            try {
                socket = new Socket(properties.getProperty(HOST_KEY), Integer.parseInt(properties.getProperty(PORT_KEY)));
            } catch (IOException e) {
                throw new RuntimeException("Socket can not be opened", e);
            }
        }
        return socket;
    }

    private static void loadProperties() {
        properties = new Properties();
        try (InputStream stream = SocketSource.class.getClassLoader().getResourceAsStream("application.properties")) {
            properties.load(stream);
        } catch (IOException e) {
            throw new RuntimeException("Could not read application.properties file", e);
        }
    }

    public void closeSocket(){
        if (socket != null && !socket.isClosed() && socket.isConnected()){
            try {
                socket.close();
            } catch (IOException e) {
                throw new RuntimeException("Could not close the socket", e);
            }
        }
    }
}

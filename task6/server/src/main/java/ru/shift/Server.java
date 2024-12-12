package ru.shift;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.shift.message.implementation.JsonMessage;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static final Logger logger = LoggerFactory.getLogger(Server.class);

    private final int port;
    private final JsonMessage messageHandler;
    private final ClientConnectionListener listener;

    public Server(int port, JsonMessage messageHandler, ClientConnectionListener listener) {
        this.port = port;
        this.messageHandler = messageHandler;
        this.listener = listener;
    }

    void waitClient() {
        logger.info("wait client");
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                Socket socket = serverSocket.accept();
                socket.setSoTimeout(0);
                ChatChannel channel = new ChatChannel(socket, messageHandler, messageHandler);
                new Thread(() -> {
                    listener.handleClient(channel);
                }).start();
            }
        } catch (IOException e) {
            logger.error("Error in wait client", e);
        }
    }
}
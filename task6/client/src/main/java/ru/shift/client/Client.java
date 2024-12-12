package ru.shift.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.shift.ChatChannel;
import ru.shift.Main;
import ru.shift.message.implementation.*;
import ru.shift.message.interfaces.Message;

import java.io.IOException;
import java.net.Socket;
import java.time.Instant;
import java.util.concurrent.CompletableFuture;

public class Client {
    private static final Logger logger = LoggerFactory.getLogger(Client.class);

    String serverHost;
    int serverPort;
    public ChatChannel channel;
    private final ClientListener listener;
    private final JsonMessage messageHandler;

    public Client(String serverHost, int sererPort, ClientListener listener) {
        this.serverHost = serverHost;
        this.serverPort = sererPort;
        this.messageHandler = new JsonMessage();
        this.listener = listener;
    }

    public void serverConnection() throws IOException {
        Socket socket = new Socket(serverHost, serverPort);
        socket.setSoTimeout(0);
        channel = new ChatChannel(socket, messageHandler, messageHandler);

        CompletableFuture.runAsync(() -> channel.readLoop(this::handleMessage));
    }

    public void onTextEntered(String username, String text) throws IOException {
        channel.sendRequest(new PostRequest(username, Instant.now(), text));
    }

    public void login(String username) throws IOException {
        channel.sendRequest(new LoginRequest(username));
    }

    public void handleMessage(ChatChannel channel, Message message) {
        String messageType = message.getClass().getSimpleName();
        switch (messageType) {
            case "LoginResponse" -> {
                var loginResponse = (LoginResponse) message;
                if (loginResponse.isSuccess()) {
                    listener.closeDialogWindow();
                    listener.openChat();
                } else {
                   logger.info("Login error");
                   listener.showErrorDialog("Error login user. This login already exists.");
                }
            }
            case "PostNotification" -> listener.updateChat((PostNotification) message);
            case "UsersListNotification" -> listener.updateUsers((UsersListNotification) message);
            default -> logger.info("Unknown message: {}", messageType);
        }
    }
}

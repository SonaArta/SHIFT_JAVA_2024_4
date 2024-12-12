package ru.shift;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.shift.message.implementation.LoginRequest;
import ru.shift.message.implementation.PostNotification;
import ru.shift.message.implementation.PostRequest;
import ru.shift.message.implementation.UsersListNotification;
import ru.shift.message.interfaces.Message;

import java.io.IOException;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

public class ServerController implements ClientConnectionListener {
    private static final Logger logger = LoggerFactory.getLogger(ServerController.class);

    private final Map<String, ChatClient> clients = new ConcurrentHashMap<>();

    public ServerController() {
    }

    @Override
    public void handleClient(ChatChannel channel) {
        var client = new ChatClient(channel);
        CompletableFuture.runAsync(() -> {
            channel.readLoop((chatChannel, message) -> {
                try {
                    messageReceived(client, chatChannel, message);
                } catch (IOException e) {
                    logger.error("Error processing client", e);
                }
            });

            clients.remove(client.getUsername());
            notifyAllClients(
                    new PostNotification("Server", Instant.now(), client.getUsername() + " logout")
            );
            notifyAllClientsUsersList();
        });
    }

    void messageReceived(ChatClient client, ChatChannel channel, Message message) throws IOException {
        String messageType = message.getClass().getSimpleName();
        switch (messageType) {
            case "LoginRequest" -> handleLoginRequest(client, channel, (LoginRequest) message);
            case "PostRequest" -> handlePostRequest(client, channel, (PostRequest) message);
            default -> logger.info("Unknown message: {}", messageType);
        }
    }

    private void handleLoginRequest(ChatClient client, ChatChannel channel, LoginRequest request) throws IOException {
        String username = request.getUsername();

        if (clients.keySet().stream().anyMatch(currentClientName -> currentClientName.equals(username))) {
            channel.sendResponse(request.error("Unique"));
        } else {
            client.setUsername(username);
            clients.put(username, client);
            channel.sendResponse(request.success());

            notifyAllClients(new PostNotification("Server", Instant.now(), username + " entered in chat"));
            notifyAllClientsUsersList();
        }
    }

    private void handlePostRequest(ChatClient client, ChatChannel channel, PostRequest request) throws IOException {
        if (client.getUsername() == null) {
            channel.sendResponse(request.error("Login first"));
        } else {
            notifyAllClients(new PostNotification(request.getUsername(), request.getTimestamp(), request.getContent()));
        }
    }

    private void notifyAllClientsUsersList() {
        notifyAllClients(new UsersListNotification(clients.keySet()));
    }

    private void notifyAllClients(Message message) {
        clients.values().forEach(client -> {
            try {
                client.getChatChannel().send(message);
            } catch (IOException e) {
                logger.error("Error notifying clients", e);
            }
        });
    }
}

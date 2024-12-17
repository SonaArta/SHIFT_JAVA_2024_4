package ru.shift;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.shift.client.Client;
import ru.shift.client.ClientData;
import ru.shift.client.ClientListener;
import ru.shift.message.implementation.PostNotification;
import ru.shift.message.implementation.UsersListNotification;
import ru.shift.view.ClientView;
import ru.shift.view.EnterNameWindow;
import ru.shift.view.InfoWindow;

import java.io.IOException;
import java.util.Set;

public class Controller implements ClientListener, ClientData {
    private static final Logger logger = LoggerFactory.getLogger(Controller.class);

    ClientView clientView;
    Client client;
    EnterNameWindow enterNameWindow;
    volatile String username;
    volatile Set<String> chatUsers;

    public Controller(String host, int port) {
        this.enterNameWindow = new EnterNameWindow();
        this.client = new Client(host, port, this);
        try {
            client.serverConnection();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        enterNameWindow.addButtonListener(e -> {
            try {
                handleNameSubmit();
            } catch (IOException er) {
                logger.error("Name submit error", er);
            }
        });
        enterNameWindow.open();
    }

    void handleNameSubmit() throws IOException {
        username = enterNameWindow.getEnterName();
        client.login(username);
    }

    @Override
    public void showErrorDialog(String message) {
        new InfoWindow().showErrorWindow(message);
    }

    @Override
    public void closeDialogWindow() {
        enterNameWindow.close();
    }

    @Override
    public void openChat() {
        clientView = new ClientView(this);
        clientView.addButtonListener(e -> {
            try {
                String textMessage = clientView.getTextMessage();
                client.onTextEntered(username, textMessage);
                clientView.clearText();
            } catch (IOException er) {
                logger.error("Open chat error", er);
            }
        });
    }

    @Override
    public void updateChat(PostNotification message) {
        clientView.showMessage(message);
    }

    @Override
    public void updateUsers(UsersListNotification message) {
        chatUsers = message.getUsernames();
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public Set<String> getChatUsers() {
        return chatUsers;
    }
}

package ru.shift.client;

import ru.shift.message.implementation.PostNotification;
import ru.shift.message.implementation.UsersListNotification;

public interface ClientListener {
    void showErrorDialog(String message);
    void updateChat(PostNotification message);
    void updateUsers(UsersListNotification message);
    void closeDialogWindow();
    void openChat();
}

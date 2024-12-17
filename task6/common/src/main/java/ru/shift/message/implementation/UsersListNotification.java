package ru.shift.message.implementation;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.shift.message.MessageType;
import ru.shift.message.interfaces.Notification;

import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class UsersListNotification extends AbstractMessage implements Notification {
    private Set<String> usernames;

    public UsersListNotification(Set<String> usernames) {
        super(MessageType.USERS_LIST);
        this.usernames = usernames;
    }
}

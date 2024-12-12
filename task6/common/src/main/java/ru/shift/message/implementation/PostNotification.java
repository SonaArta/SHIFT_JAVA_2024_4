package ru.shift.message.implementation;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.shift.message.MessageType;
import ru.shift.message.interfaces.Notification;

import java.time.Instant;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class PostNotification extends AbstractMessage implements Notification {
    private String username;
    private Instant timestamp;
    private String content;

    public PostNotification(String username, Instant timestamp, String content) {
        super(MessageType.POST_NOTIFICATION);
        this.username = username;
        this.timestamp = timestamp;
        this.content = content;
    }
}

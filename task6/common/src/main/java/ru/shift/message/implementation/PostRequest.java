package ru.shift.message.implementation;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.shift.message.MessageType;

import java.time.Instant;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public final class PostRequest extends AbstractRequest {
    private String username;
    private Instant timestamp;
    private String content;

    public PostRequest(String nick, Instant timestamp, String body) {
        super(MessageType.POST_REQ, generateId());
        this.username = nick;
        this.timestamp = timestamp;
        this.content = body;
    }
}

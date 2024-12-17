package ru.shift.message.implementation;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.shift.message.MessageType;
import ru.shift.message.interfaces.Message;

import java.util.UUID;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "type")
@JsonSubTypes(value = {
        @JsonSubTypes.Type(name = "LOGIN_REQ", value = LoginRequest.class),
        @JsonSubTypes.Type(name = "LOGIN_RESP", value = LoginResponse.class),
        @JsonSubTypes.Type(name = "POST_REQ", value = PostRequest.class),
        @JsonSubTypes.Type(name = "POST_NOTIFICATION", value = PostNotification.class),
        @JsonSubTypes.Type(name = "USERS_LIST", value = UsersListNotification.class)
})
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
public class AbstractMessage implements Message {
    protected MessageType type;

    public AbstractMessage(MessageType type) {
        this.type = type;
    }

    public static String generateId() {
        return UUID.randomUUID().toString();
    }
}

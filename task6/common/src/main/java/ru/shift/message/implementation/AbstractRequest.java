package ru.shift.message.implementation;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.shift.message.MessageType;
import ru.shift.message.interfaces.Request;
import ru.shift.message.interfaces.Response;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
public class AbstractRequest extends AbstractMessage implements Request {
    String id;
    Boolean error;
    String reason;

    protected AbstractRequest(MessageType type, String id) {
        super(type);
        this.id = id;
    }

    @Override
    public Response success() {
        return null;
    }

    @Override
    public Response error(String reason) {
        return null;
    }
}

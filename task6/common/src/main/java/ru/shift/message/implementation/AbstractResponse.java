package ru.shift.message.implementation;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.shift.message.MessageType;
import ru.shift.message.interfaces.Response;

@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
public class AbstractResponse extends AbstractMessage implements Response {
    String id;
    Boolean error;
    String reason;

    protected AbstractResponse(MessageType type, String id) {
        super(type);
        this.id = id;
    }

    public AbstractResponse(MessageType type, String id, String reason) {
        super(type);
        this.id = id;
        this.error = true;
        this.reason = reason;
    }

    @Override
    public boolean isSuccess() {
        return error == null || !error;
    }

    @Override
    public boolean isError() {
        return error != null && error;
    }
}

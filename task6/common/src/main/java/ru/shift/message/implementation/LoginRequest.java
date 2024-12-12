package ru.shift.message.implementation;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.shift.message.MessageType;
import ru.shift.message.interfaces.Response;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class LoginRequest extends AbstractRequest {
    private String username;

    public LoginRequest(String username) {
        super(MessageType.LOGIN_REQ, AbstractMessage.generateId());
        this.username = username;
    }

    @Override
    public Response success() {
        return new LoginResponse(id);
    }

    @Override
    public Response error(String errorMessage) {
        return new LoginResponse(id, errorMessage);
    }
}

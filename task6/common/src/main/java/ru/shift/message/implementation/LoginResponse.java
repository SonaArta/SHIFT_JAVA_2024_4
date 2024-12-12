package ru.shift.message.implementation;

import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.shift.message.MessageType;

@NoArgsConstructor
@Getter
public class LoginResponse extends AbstractResponse {

    public LoginResponse(String id, String reason) {
        super(MessageType.LOGIN_RESP, id, reason);
    }

    public LoginResponse(String id) {
        super(MessageType.LOGIN_RESP, id);
    }
}

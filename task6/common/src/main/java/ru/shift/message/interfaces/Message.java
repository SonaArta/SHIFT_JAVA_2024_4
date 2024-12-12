package ru.shift.message.interfaces;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ru.shift.message.MessageType;

public interface Message {
    MessageType getType();

    @JsonIgnore
    default boolean isRequest() {
        return false;
    }

    @JsonIgnore
    default boolean isResponse() {
        return false;
    }
}

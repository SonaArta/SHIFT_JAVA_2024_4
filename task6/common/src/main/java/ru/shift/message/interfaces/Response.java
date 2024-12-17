package ru.shift.message.interfaces;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface Response extends Message {
    @JsonIgnore
    boolean isSuccess();
    boolean isError();
    String getReason();
}

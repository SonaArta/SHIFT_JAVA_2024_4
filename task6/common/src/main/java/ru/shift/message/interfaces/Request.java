package ru.shift.message.interfaces;

public interface Request extends Message{
    Response success();
    Response error(String reason);

}

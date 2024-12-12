package ru.shift.message.interfaces;

public interface MessageSerializer<T> {
    T serialize(Message message);
}

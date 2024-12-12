package ru.shift.message.interfaces;

import java.net.ProtocolException;

public interface MessageDeserializer <T>{
    Message deserialize(T message) throws ProtocolException;
}

package ru.shift.message.implementation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.shift.message.interfaces.Message;
import ru.shift.message.interfaces.MessageDeserializer;
import ru.shift.message.interfaces.MessageSerializer;

import java.net.ProtocolException;

public class JsonMessage implements MessageSerializer<String>, MessageDeserializer<String> {
    private static final Logger logger = LoggerFactory.getLogger(JsonMessage.class);

    static ObjectMapper mapper = JsonMapper.builder()
            .addModule(new JavaTimeModule())
            .build();

    @Override
    public Message deserialize(String data) throws ProtocolException {
        try {
            if (data == null || data.isBlank()) {

                throw new RuntimeException("Empty message");
            }
            return mapper.readValue(data, AbstractMessage.class);
        } catch (JsonProcessingException e) {
            logger.error("Deserialization failed for: {}.", data, e);
            throw new ProtocolException("Deserialization failed for: " + data);
        }
    }

    @Override
    public String serialize(Message message) {
        try {
            return mapper.writeValueAsString(message);
        } catch (JsonProcessingException e) {
            logger.error("{} serialization failed.", message.getType(), e);
            throw new RuntimeException("serialization failed", e);
        }
    }
}


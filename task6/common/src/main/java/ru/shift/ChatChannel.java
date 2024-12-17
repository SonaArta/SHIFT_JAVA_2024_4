package ru.shift;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.shift.message.interfaces.*;

import java.io.*;
import java.net.Socket;
import java.util.function.BiConsumer;

public class ChatChannel implements AutoCloseable {
    private static final Logger logger = LoggerFactory.getLogger(ChatChannel.class);

    private final Socket sock;
    private final MessageSerializer<String> serializer;
    private final MessageDeserializer<String> deserializer;
    private final BufferedReader reader;
    private final BufferedWriter writer;


    public ChatChannel(Socket sock, MessageSerializer<String> serializer, MessageDeserializer<String> deserializer) throws IOException {
        this.sock = sock;
        this.serializer = serializer;
        this.deserializer = deserializer;
        reader = new BufferedReader(new InputStreamReader(sock.getInputStream()));
        writer = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));
    }

    @Override
    public void close() throws Exception {
        writer.close();
        reader.close();
        sock.close();
    }

    public void send(Message message) throws IOException {
        var messageJson = serializer.serialize(message);
        writer.write(messageJson + "\n");
        writer.flush();
    }

    public void sendResponse(Response response) throws IOException {
        var message = serializer.serialize(response);
        writer.write(message + "\n");
        writer.flush();
    }

    public void sendRequest(Request request) throws IOException {
        String string = serializer.serialize(request);
        writer.write(string + "\n");
        writer.flush();
    }

    public void readLoop(BiConsumer<ChatChannel, Message> messageConsumer) {
        logger.info("Start wait messages");
        while (true) {
            try {
                String line = reader.readLine();
                if (line == null || line.isBlank()) {
                    logger.info("Connection closed by server, empty line");
                    break;
                }
                Message message = deserializer.deserialize(line);
                if (message == null) {
                    logger.info("Null message");
                    continue;
                }
                handleMessage(message, messageConsumer);
            } catch (IOException e) {
                logger.error("Error reading from channel", e);
                break;
            } catch (Exception e) {
                logger.error("Unexpected error", e);
                break;
            }
        }

        logger.info("Stop wait messages");
    }

    private void handleMessage(Message message, BiConsumer<ChatChannel, Message> messageConsumer) {
        messageConsumer.accept(this, message);
    }
}

package ru.shift;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.shift.load.ApplicationProperties;
import ru.shift.load.ParsePropertiesException;
import ru.shift.message.implementation.JsonMessage;

import java.io.IOException;

import static ru.shift.load.PropertiesLoader.loadProperties;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] ar) {
        ApplicationProperties applicationProperties = getPropertiesProgram();

        JsonMessage messageHandler = new JsonMessage();
        ServerController serverController = new ServerController();
        Server server = new Server(applicationProperties.port(), messageHandler, serverController);
        server.waitClient();
    }

    private static ApplicationProperties getPropertiesProgram() {
        try {
            return loadProperties();
        } catch (ParsePropertiesException | IOException e) {
            logger.error("Error while reading properties", e);
            logger.info("The program started with default values");
            return ApplicationProperties.builder()
                    .port(Integer.parseInt("1234"))
                    .host("localhost")
                    .build();
        }
    }
}

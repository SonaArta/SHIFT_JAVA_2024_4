package ru.shift.utils;

import ru.shift.utils.exception.ParsePropertiesException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesLoader {
    private PropertiesLoader() {

    }

    public static PropertiesProgram loadProperties() throws IOException, ParsePropertiesException {
        ClassLoader classLoader = PropertiesLoader.class.getClassLoader();
        InputStream name = classLoader.getResourceAsStream("config.properties");
        Properties properties = new Properties();
        try (InputStream inputStream = name) {
            properties.load(inputStream);
        } catch (IOException e) {
            throw new IOException("Error opening configuration file");
        }

        try {
            int storageSize = Integer.parseInt(properties.getProperty("storageSize"));
            int producerCount = Integer.parseInt(properties.getProperty("producerCount"));
            int consumerCount = Integer.parseInt(properties.getProperty("consumerCount"));
            int producerTime = Integer.parseInt(properties.getProperty("producerTime"));
            int consumerTime = Integer.parseInt(properties.getProperty("consumerTime"));
            String nameProducer = properties.getProperty("nameProducer");
            String nameConsumer = properties.getProperty("nameConsumer");

            return new PropertiesProgram(storageSize,
                    producerCount, consumerCount,
                    producerTime, consumerTime,
                    nameProducer, nameConsumer);
        } catch (NumberFormatException e) {
            throw new ParsePropertiesException("Error format configuration");
        }
    }
}
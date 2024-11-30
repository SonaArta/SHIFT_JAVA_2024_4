package ru.shift.utils;

import ru.shift.utils.exception.ParsePropertiesException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesLoader {
    private final static String PROPERTY_FILE_NAME = "config.properties";
    private final static String STORAGE_SIZE = "storageSize";
    private final static String PRODUCER_COUNT = "producerCount";
    private final static String CONSUMER_COUNT = "consumerCount";
    private final static String PRODUCER_TIME = "producerTime";
    private final static String CONSUMER_TIME = "consumerTime";
    private final static String PRODUCER_NAME = "producerName";
    private final static String CONSUMER_NAME = "consumerName";

    private PropertiesLoader() {
    }

    public static ApplicationProperties loadProperties() throws IOException, ParsePropertiesException {
        ClassLoader classLoader = PropertiesLoader.class.getClassLoader();
        InputStream name = classLoader.getResourceAsStream(PROPERTY_FILE_NAME);
        Properties properties = new Properties();
        try (InputStream inputStream = name) {
            properties.load(inputStream);
        } catch (IOException e) {
            throw new IOException("Error opening configuration file");
        }

        try {
            return ApplicationProperties.builder()
                    .storageSize(Integer.parseInt(properties.getProperty(STORAGE_SIZE)))
                    .producerCount(Integer.parseInt(properties.getProperty(PRODUCER_COUNT)))
                    .consumerCount(Integer.parseInt(properties.getProperty(CONSUMER_COUNT)))
                    .producerTime(Integer.parseInt(properties.getProperty(PRODUCER_TIME)))
                    .consumerTime(Integer.parseInt(properties.getProperty(CONSUMER_TIME)))
                    .producerName(properties.getProperty(PRODUCER_NAME))
                    .consumerName(properties.getProperty(CONSUMER_NAME))
                    .build();
        } catch (NumberFormatException e) {
            throw new ParsePropertiesException("Error format configuration");
        }
    }
}
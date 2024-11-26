package ru.shift.utils;

import ru.shift.utils.exception.ParsePropertiesException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesLoader {
    private PropertiesLoader() {
    }

    public static ApplicationProperties loadProperties() throws IOException, ParsePropertiesException {
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
            String producerName = properties.getProperty("producerName");
            String consumerName = properties.getProperty("consumerName");

            return ApplicationProperties.builder()
                    .storageSize(storageSize)
                    .producerCount(producerCount)
                    .consumerCount(consumerCount)
                    .producerTime(producerTime)
                    .consumerTime(consumerTime)
                    .producerName(producerName)
                    .consumerName(consumerName)
                    .build();
        } catch (NumberFormatException e) {
            throw new ParsePropertiesException("Error format configuration");
        }
    }
}
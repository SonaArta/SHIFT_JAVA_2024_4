package ru.shift.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesProgram {
    private static final Logger logger = LoggerFactory.getLogger(PropertiesProgram.class);

    public static int producerCount;
    public static int consumerCount;
    public static int producerTime;
    public static int consumerTime;
    public static int storageSize;
    public static int threadCount;

    private PropertiesProgram() {

    }

    public static void loadProperties() {
        ClassLoader classLoader = PropertiesProgram.class.getClassLoader();
        InputStream name = classLoader.getResourceAsStream("config.properties");
        Properties properties = new Properties();
        try (InputStream inputStream = name) {
            properties.load(inputStream);
        } catch (IOException e) {
            logger.error("Error opening configuration file", e);
        }

        producerCount = Integer.parseInt(properties.getProperty("producerCount"));
        consumerCount = Integer.parseInt(properties.getProperty("consumerCount"));
        producerTime = Integer.parseInt(properties.getProperty("producerTime"));
        consumerTime = Integer.parseInt(properties.getProperty("consumerTime"));
        storageSize = Integer.parseInt(properties.getProperty("storageSize"));
        threadCount = producerCount + consumerCount;
    }
}
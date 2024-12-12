package ru.shift.load;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesLoader {
    private final static String PROPERTY_FILE_NAME = "config.properties";
    private final static String PORT = "port";
    private final static String HOST = "host";

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
                    .port(Integer.parseInt(properties.getProperty(PORT)))
                    .host(properties.getProperty(HOST))
                    .build();
        } catch (NumberFormatException e) {
            throw new ParsePropertiesException("Error format configuration");
        }
    }
}

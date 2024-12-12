package ru.shift;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.shift.load.ApplicationProperties;
import ru.shift.load.ParsePropertiesException;
import ru.shift.view.EnterHostAndPortWindow;
import ru.shift.view.InfoWindow;

import java.io.IOException;

import static ru.shift.load.PropertiesLoader.loadProperties;

public class MainTwo {
    private static final Logger logger = LoggerFactory.getLogger(ru.shift.Main.class);
    private static String host;
    private static int port;

    public static void main(String[] args) {
        initHostAndPort();

        Controller controller = new Controller(host, port);
    }

    private static void setHostAndPort(ApplicationProperties applicationProperties, EnterHostAndPortWindow enterAddressServer) {
        String hostFromEnter = enterAddressServer.getHost();
        int portFromEnter = Integer.parseInt(enterAddressServer.getPort());

        String hostFromApplicationProperties = applicationProperties.host();
        int portFromApplicationProperties = applicationProperties.port();

        if (!hostFromEnter.equals(hostFromApplicationProperties) && !(portFromEnter == portFromApplicationProperties)) {
            host = hostFromApplicationProperties;
            port = portFromApplicationProperties;

            new InfoWindow().showInfoWindow("Program start with default properties.");
            logger.info("Program start with default properties.");
        } else {
            host = hostFromEnter;
            port = portFromEnter;
        }

        enterAddressServer.close();
    }

    private static void initHostAndPort() {
        ApplicationProperties applicationProperties = getPropertiesProgram();

        EnterHostAndPortWindow enterNameWindow = new EnterHostAndPortWindow();
        enterNameWindow.addButtonListener(e -> setHostAndPort(applicationProperties, enterNameWindow));
        enterNameWindow.open();
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

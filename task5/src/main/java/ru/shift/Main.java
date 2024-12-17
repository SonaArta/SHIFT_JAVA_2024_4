package ru.shift;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.shift.consumer.Consumer;
import ru.shift.producer.Producer;
import ru.shift.storage.Storage;
import ru.shift.utils.ApplicationProperties;
import ru.shift.utils.exception.ParsePropertiesException;

import java.io.IOException;

import static ru.shift.utils.PropertiesLoader.loadProperties;
import static ru.shift.utils.constant.DefaultValues.*;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        ApplicationProperties applicationProperties = getPropertiesProgram();

        Storage storage = new Storage(applicationProperties.storageSize());
        for (int i = 0; i < applicationProperties.producerCount(); i++) {
            new Thread(new Producer(storage, applicationProperties.producerTime()), applicationProperties.producerName() + i).start();
        }
        for (int i = 0; i < applicationProperties.consumerCount(); i++) {
            new Thread(new Consumer(storage, applicationProperties.consumerTime()), applicationProperties.consumerName() + i).start();
        }
    }

    private static ApplicationProperties getPropertiesProgram() {
        try {
            return loadProperties();
        } catch (ParsePropertiesException | IOException e) {
            logger.error("Error while reading properties", e);
            logger.info("The program started with default values");
            return ApplicationProperties.builder()
                    .storageSize(STORAGE_SIZE)
                    .producerCount(PRODUCER_COUNT)
                    .consumerCount(CONSUMER_COUNT)
                    .producerTime(PRODUCER_TIME)
                    .consumerTime(CONSUMER_TIME)
                    .producerName(PRODUCER_NAME)
                    .consumerName(CONSUMER_NAME)
                    .build();
        }
    }
}
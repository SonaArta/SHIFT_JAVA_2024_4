package ru.shift;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.shift.consumer.Consumer;
import ru.shift.producer.Producer;
import ru.shift.storage.Storage;
import ru.shift.utils.PropertiesProgram;
import ru.shift.utils.exception.ParsePropertiesException;

import java.io.IOException;

import static ru.shift.utils.PropertiesLoader.loadProperties;
import static ru.shift.utils.constant.DefaultValues.*;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        PropertiesProgram propertiesProgramm;
        try {
            propertiesProgramm = loadProperties();
        } catch (ParsePropertiesException | IOException e) {
            logger.error(String.valueOf(e));
            propertiesProgramm = new PropertiesProgram(storageSize,
                    producerCount, consumerCount,
                    producerTime, consumerTime,
                    nameProducer, nameConsumer);
            logger.info("The program started with default values");
        }

        Storage storage = new Storage(propertiesProgramm.storageSize());
        for (int i = 0; i < propertiesProgramm.producerCount(); i++) {
            new Thread(new Producer(storage, propertiesProgramm.producerTime()), propertiesProgramm.nameProducer() + i).start();
        }
        for (int i = 0; i < propertiesProgramm.consumerCount(); i++) {
            new Thread(new Consumer(storage, propertiesProgramm.consumerTime()), propertiesProgramm.nameConsumer() + i).start();
        }
    }
}
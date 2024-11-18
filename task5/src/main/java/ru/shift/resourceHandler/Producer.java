package ru.shift.resourceHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.shift.resource.IdCounter;
import ru.shift.resource.Resource;
import ru.shift.storage.Storage;
import ru.shift.utils.PropertiesProgram;

import static ru.shift.utils.PropertiesProgram.threadCount;

public class Producer implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(Producer.class);
    private final int producerTime;
    private final Storage storage;

    public Producer(Storage storage) {
        this.producerTime = PropertiesProgram.producerTime;
        this.storage = storage;
    }

    @Override
    public void run() {
        logger.info("\"{}\" with ID = {} is start\n", Thread.currentThread().getName(), Thread.currentThread().getId() % threadCount);

        while (true) {
            int resourceId = IdCounter.getCountId();
            Resource newResource = new Resource(resourceId);
            logger.info("\"{}\" create resource with ID = {}", Thread.currentThread().getName(), resourceId);

            long differentTime = storage.put(newResource);
            try {
                Thread.sleep(producerTime - differentTime);
            } catch (InterruptedException e) {
                logger.error("The flow in the sleep state was interrupted.", e);
            }
        }

    }
}

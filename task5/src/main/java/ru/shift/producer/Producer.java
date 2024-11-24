package ru.shift.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.shift.resource.Resource;
import ru.shift.storage.Storage;


public class Producer implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(Producer.class);
    private final int id;
    private final int producerTime;
    private final Storage storage;

    public Producer(Storage storage, int producerTime) {
        this.id = IdGenerator.generateId();
        this.producerTime = producerTime;
        this.storage = storage;
    }

    @Override
    public void run() {
        logger.info("\"{}\" with ID = {} is start\n", Thread.currentThread().getName(), id);

        while (true) {
            Resource newResource = new Resource();
            logger.info("\"{}\" create resource with ID = {}", Thread.currentThread().getName(), newResource.getId());

            storage.put(newResource, id);
            logger.info("A resource with ID = {} was added. There are {} resources in stock.\n",
                    newResource.getId(), storage.getCountResourceInStorage());
            try {
                Thread.sleep(producerTime);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                logger.error("The flow in the sleep state was interrupted.", e);
            }
        }

    }
}

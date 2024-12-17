package ru.shift.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.shift.resource.Resource;
import ru.shift.storage.Storage;

public class Consumer implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(Consumer.class);
    private final int id;
    private final int consumerTime;
    private final Storage storage;

    public Consumer(Storage storage, int consumerTime) {
        this.id = IdGenerator.generateId();
        this.consumerTime = consumerTime;
        this.storage = storage;
    }


    @Override
    public void run() {
        logger.info("\"{}\" with ID = {} is start\n",
                Thread.currentThread().getName(), id);

        while (!Thread.currentThread().isInterrupted()) {
            Resource takenResource = storage.take(id);
            logger.info("A resource with ID = {} was taken away. There are {} resources in stock.\n",
                    takenResource.getId(), storage.getCountResourceInStorage());
            try {
                Thread.sleep(consumerTime);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                logger.error("The flow in the sleep state was interrupted.", e);
            }
        }

    }
}

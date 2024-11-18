package ru.shift.resourceHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.shift.storage.Storage;
import ru.shift.utils.PropertiesProgram;

import static ru.shift.utils.PropertiesProgram.threadCount;

public class Consumer implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(Consumer.class);
    private final int consumerTime;
    private final Storage storage;

    public Consumer(Storage storage) {
        this.consumerTime = PropertiesProgram.consumerTime;
        this.storage = storage;
    }


    @Override
    public void run() {
        logger.info("\"{}\" with ID = {} is start\n",
                Thread.currentThread().getName(), Thread.currentThread().getId() % threadCount);

        while (true) {
            long differentTime = storage.take();
            try {
                Thread.sleep(consumerTime - differentTime);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                logger.error("The flow in the sleep state was interrupted.", e);
            }
        }

    }
}

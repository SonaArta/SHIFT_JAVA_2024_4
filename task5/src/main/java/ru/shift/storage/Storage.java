package ru.shift.storage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.shift.resource.Resource;

import java.util.LinkedList;
import java.util.Queue;

public class Storage {
    private static final Logger logger = LoggerFactory.getLogger(Storage.class);

    private final int storageSize;
    private final Queue<Resource> resourceQueue;

    public Storage(int storageSize) {
        this.storageSize = storageSize;
        this.resourceQueue = new LinkedList<>();
    }

    public int getCountResourceInStorage() {
        return resourceQueue.size();
    }

    public synchronized void put(Resource resource, int idProducer) {
        while (resourceQueue.size() >= storageSize) {
            try {
                logger.info("\"{}\" with ID = {} has entered waiting mode.",
                        Thread.currentThread().getName(), idProducer);
                wait();
                logger.info("\"{}\" with ID = {} exited waiting mode.",
                        Thread.currentThread().getName(), idProducer);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                logger.error("The flow in the sleep state was interrupted.", e);
            }
        }
        resourceQueue.add(resource);
        logger.info("A resource with ID = {} was added. There are {} resources in stock.\n",
                resource.getId(), resourceQueue.size());
        notifyAll();
    }

    public synchronized Resource take(int idConsumer) {
        while (resourceQueue.isEmpty()) {
            try {
                logger.info("\"{}\" with ID = {} has entered waiting mode.",
                        Thread.currentThread().getName(), idConsumer);
                wait();
                logger.info("\"{}\" with ID = {} exited waiting mode.",
                        Thread.currentThread().getName(), idConsumer);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                logger.error("The flow in the sleep state was interrupted.", e);
            }
        }
        Resource takenResource = resourceQueue.poll();
        notifyAll();

        return takenResource;
    }
}

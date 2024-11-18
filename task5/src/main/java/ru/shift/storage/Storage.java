package ru.shift.storage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.shift.resource.Resource;
import ru.shift.utils.PropertiesProgram;

import java.util.LinkedList;
import java.util.Queue;

import static ru.shift.utils.PropertiesProgram.threadCount;

public class Storage {
    private static final Logger logger = LoggerFactory.getLogger(Storage.class);

    private final int storageSize;
    private final Queue<Resource> resourceQueue;

    public Storage() {
        this.storageSize = PropertiesProgram.storageSize;
        this.resourceQueue = new LinkedList<>();
    }

    synchronized public long put(Resource resource) {
        while (resourceQueue.size() >= storageSize) {
            try {
                logger.info("\"{}\" with producerID  = {} has entered waiting mode.", Thread.currentThread().getName(), Thread.currentThread().getId() % threadCount);
                wait();
                logger.info("\"{}\" with producerID = {} exited waiting  mode.", Thread.currentThread().getName(), Thread.currentThread().getId() % threadCount);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        long startTime = System.currentTimeMillis();
        resourceQueue.add(resource);
        logger.info("A resource with ID = {} was added. There are {} resources in stock.\n",
                resource.getId(), resourceQueue.size());
        notifyAll();

        return System.currentTimeMillis() - startTime;
    }

    synchronized public long take() {
        while (resourceQueue.isEmpty()) {
            try {
                logger.info("\"{}\" with consumerID = {}  has entered waiting mode.", Thread.currentThread().getName(), Thread.currentThread().getId() % threadCount);
                wait();
                logger.info("\"{}\" with consumerID = {} exited waiting  mode.", Thread.currentThread().getName(), Thread.currentThread().getId() % threadCount);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        long startTime = System.currentTimeMillis();
        Resource takenResource = resourceQueue.poll();
        logger.info("A resource with ID = {} was taken away. There are {} resources in stock.\n",
                takenResource.getId(), resourceQueue.size());
        notifyAll();

        return System.currentTimeMillis() - startTime;
    }
}

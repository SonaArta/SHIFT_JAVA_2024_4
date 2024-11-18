package ru.shift;

import ru.shift.resourceHandler.Consumer;
import ru.shift.resourceHandler.Producer;
import ru.shift.storage.Storage;
import ru.shift.utils.PropertiesProgram;

import static ru.shift.utils.PropertiesProgram.loadProperties;

public class Main {
    public static void main(String[] args) {
        loadProperties();
        Storage storage = new Storage();
        for (int i = 0; i < PropertiesProgram.producerCount; i++) {
            new Thread(new Producer(storage), "PRODUCER-" + i).start();
        }
        for (int i = 0; i < PropertiesProgram.consumerCount; i++) {
            new Thread(new Consumer(storage), "CONSUMER-" + i).start();
        }
    }
}
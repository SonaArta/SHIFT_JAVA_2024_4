package ru.shift.consumer;

class IdGenerator {
    private static int id = 0;

    private IdGenerator() {
    }

    static synchronized int generateId() {
        return id++;
    }
}


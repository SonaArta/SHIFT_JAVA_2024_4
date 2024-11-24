package ru.shift.producer;

class IdGenerator {
    private static int id = 0;

    private IdGenerator() {
    }

    public static synchronized int generateId() {
        return id++;
    }
}


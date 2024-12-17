package ru.shift.resource;

class IdGenerator {
    private static int id = 1;

    private IdGenerator() {
    }

    static synchronized int generateId() {
        return id++;
    }
}


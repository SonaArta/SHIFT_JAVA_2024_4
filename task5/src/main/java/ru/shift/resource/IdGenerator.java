package ru.shift.resource;

class IdGenerator {
    private static int id = 1;

    private IdGenerator() {
    }

    public static synchronized int generateId() {
        return id++;
    }
}


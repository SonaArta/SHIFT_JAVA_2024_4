package ru.shift.resource;

public class IdCounter {
    private static int id = 1;

    private IdCounter() {

    }

    public synchronized static int getCountId() {
        return id++;
    }
}

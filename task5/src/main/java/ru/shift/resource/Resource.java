package ru.shift.resource;

import static ru.shift.resource.IdGenerator.generateId;

public class Resource {
    private final int id;

    public Resource() {
        this.id = generateId();
    }

    public int getId() {
        return id;
    }
}

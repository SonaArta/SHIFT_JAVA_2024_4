package ru.shift.controller;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

public class Notifiable<L> {
    List<L> listeners = new CopyOnWriteArrayList<>();

    public void add(L listener) {
        this.listeners.add(listener);
    }

    public void add(List<L> listeners) {
        this.listeners.addAll(listeners);
    }

    public void notify(Consumer<L> notifier) {
        for (L listener : this.listeners) {
            notifier.accept(listener);
        }
    }
}

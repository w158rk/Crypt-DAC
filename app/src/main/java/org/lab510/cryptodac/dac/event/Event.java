package org.lab510.cryptodac.dac.event;

public class Event {
    private String name;

    public Event(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

}

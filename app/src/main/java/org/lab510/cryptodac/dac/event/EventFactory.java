package org.lab510.cryptodac.dac.event;

public class EventFactory{
    public static <EventType extends Enum> Event getEvent(EventType type) {
        return new Event(type.name());
    }
}

package org.lab510.cryptodac.dac.event;

public class EventFactory{
    public static <EventType extends Enum> Event getEvent(EventType type) {
        String prefix = PublicKeyEventFactory.class.getSimpleName();
        return new Event(prefix.substring(0, prefix.length()-7) + ":" + type.name());
    }
}

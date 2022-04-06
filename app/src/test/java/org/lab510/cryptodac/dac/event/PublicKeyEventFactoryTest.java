package org.lab510.cryptodac.dac.event;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class PublicKeyEventFactoryTest {
    @Test
    public void testPublicKeyEventFactory() {
        for(PublicKeyEventFactory.EventType type : PublicKeyEventFactory.EventType.values()) {
            assertEquals(type.name(), PublicKeyEventFactory.getEvent(type).toString());
        }
    }
}

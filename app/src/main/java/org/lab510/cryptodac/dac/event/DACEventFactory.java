package org.lab510.cryptodac.dac.event;

public class DACEventFactory extends EventFactory {
    public enum EventType {
        ASSIGN_UR,
        REVOKE_UR,
        ASSIGN_PA,
        REVOKE_PA
    }
}

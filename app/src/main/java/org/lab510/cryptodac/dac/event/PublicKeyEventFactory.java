package org.lab510.cryptodac.dac.event;

public class PublicKeyEventFactory extends EventFactory {
    public enum EventType {
        SYS_INI,
        KEY_GEN,
        ENC,
        DEC,
        SYM_KEY_GEN,
        SYM_ENC,
        SYM_DEC
    }
}

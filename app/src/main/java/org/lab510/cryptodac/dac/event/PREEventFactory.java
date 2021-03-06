package org.lab510.cryptodac.dac.event;

public class PREEventFactory extends EventFactory {
    public enum EventType {
        SYS_INI,
        KEY_GEN,
        REKEN_GEN,
        ENC_1,
        ENC_2,
        DEC_1,
        DEC_2,
        REENC,
        SYM_KEY_GEN,
        SYM_ENC,
        SYM_DEC
    }
}
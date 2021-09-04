package org.lab510.cryptodac.time;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class DayTimerTest {
    @Test
    void testIncrement() {
        DayTimer timer = new DayTimer();
        for(int i=0; i<100; i++) {
            assertEquals(i, timer.getToday());
            timer.increment();
        }
    }
}

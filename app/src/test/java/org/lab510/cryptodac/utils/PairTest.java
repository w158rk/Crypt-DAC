package org.lab510.cryptodac.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class PairTest {
    @Test
    void testPair() {
        Pair<Integer, Integer> pair = new Pair<Integer,Integer>(1, 2);
        assertEquals(pair.getFirst(), 1);
        assertEquals(pair.getSecond(), 2);
    }
}

package org.lab510.cryptodac.error;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ErrorTest {

    @Test
    void testMessage() {
        String message = "Something goes wrong";
        String expected = "org.lab510.cryptodac.error.Error: " + message;
        try {
            throw new Error(message);
        } catch (Error e) {
            assertEquals(expected, e.toString());
        }
    }

}

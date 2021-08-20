package org.lab510.cryptodac.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;

import org.junit.jupiter.api.Test;

public class ConfigParserTest {
    @Test
    void configParseFailTest() {
        String filename = "123.properties";
        try {
            Configuration configuration = new ConfigParser().parse(filename);
        } catch (NullPointerException e) {

        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void configParseSuccessTest() {
        String filename = "test.properties";
        Configuration configuration = null;


        try {
            configuration = new ConfigParser().parse(filename);
        } catch (NullPointerException e) {
            e.printStackTrace();
            fail();
        } catch (Exception e) {
            fail();
        }

        assertNotNull(configuration);

        assertEquals(79, configuration.getIntegerValue("numUser"));
        assertEquals(231, configuration.getIntegerValue("numPerm"));
        assertEquals(20, configuration.getIntegerValue("numRole"));
        assertEquals(75, configuration.getIntegerValue("numUR"));
        assertEquals(629, configuration.getIntegerValue("numPA"));

        assertEquals(0.1, configuration.getDoubleValue("mu"));
        assertEquals(0.7, configuration.getDoubleValue("muAMin"));
        assertEquals(1, configuration.getDoubleValue("muAMax"));
        assertEquals(0.3, configuration.getDoubleValue("muUMin"));
        assertEquals(0.7, configuration.getDoubleValue("muUMax"));


    }
}

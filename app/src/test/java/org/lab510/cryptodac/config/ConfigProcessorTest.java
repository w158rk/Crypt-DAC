package org.lab510.cryptodac.config;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

public class ConfigProcessorTest {
    @Test
    void processTest() {
        String filename = "test.properties";
        Configuration conf = null;

        try {
            conf = new ConfigParser().parse(filename);
        } catch (NullPointerException e) {
            e.printStackTrace();
            fail();
        } catch (Exception e) {
            fail();
        }

        assertNotNull(conf);

        new ConfigProcessor().process(conf);
        assertTrue(conf.getDoubleValue("muA")>=conf.getDoubleValue("muAMin"));
        assertTrue(conf.getDoubleValue("muA")<=conf.getDoubleValue("muAMax"));
        assertTrue(conf.getDoubleValue("muU")>=conf.getDoubleValue("muUMin"));
        assertTrue(conf.getDoubleValue("muU")<=conf.getDoubleValue("muUMax"));
    }
}

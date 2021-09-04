package org.lab510.cryptodac.dac;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.lab510.cryptodac.config.ConfigParser;
import org.lab510.cryptodac.config.ConfigProcessor;
import org.lab510.cryptodac.config.Configuration;

public class IdentityBasedDACTest {
    private static IdentityBasedDAC dac = null;
    private static Configuration config = null;

    @BeforeEach
    public void setup() {
        ConfigParser parser = new ConfigParser();
        try {
            config = parser.parse("test.properties");
            new ConfigProcessor().process(config);
        }
        catch(IOException e) {
            fail();
        }
        dac = new IdentityBasedDAC(config);
    }

    @Test
    public void testAddUser() {
        dac.initializeWorkload();
        dac.addUser();
        assertEquals(config.getIntegerValue("numUser")+1, dac.getWorkload().getUsers().size());
        assertEquals(1, dac.getTxs().size());
    }

    @Test
    public void testRun() {
        dac.run();
        assertEquals(config.getIntegerValue("numUR")+30*dac.getRateAssignUser(), dac.getWorkload().getUrs().size());
        assertEquals(4*30*dac.getRateAssignUser(), dac.getTxs().size());
    }
}

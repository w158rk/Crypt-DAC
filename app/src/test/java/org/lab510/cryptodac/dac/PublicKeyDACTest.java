package org.lab510.cryptodac.dac;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.lab510.cryptodac.config.ConfigParser;
import org.lab510.cryptodac.config.ConfigProcessor;
import org.lab510.cryptodac.config.Configuration;

public class PublicKeyDACTest {
    private static PublicKeyDAC dac = null;
    private static Configuration config = null;

    @BeforeEach
    public void setup() {
        ConfigParser parser = new ConfigParser();
        try {
            config = parser.parse("test.properties");
            new ConfigProcessor().process(config);
        } catch (Exception e) {
            fail();
        }
        dac = new PublicKeyDAC(config);
        dac.initializeWorkload();
    }

    @Test
    public void testAddUser() {
        dac.addUser();
        assertEquals(config.getIntegerValue("numUser") + 1, dac.getWorkload().getUsers().size());
        assertEquals(1, dac.txs.size());
    }

    @Test
    public void testAssignUser() {
        dac.assignUr();
        assertEquals(config.getIntegerValue("numUser"), dac.getWorkload().getUsers().size());
        assertEquals(config.getIntegerValue("numRole"), dac.getWorkload().getRoles().size());
        assertEquals(config.getIntegerValue("numUR") + 1, dac.getWorkload().getUrs().size());
        assertEquals(2, dac.txs.size());
    }

    // @Test
    // public void testRevokeUser() {
    //     dac.revokeUser();
    // }

    // @Test
    // public void testRun() {
    //     assertEquals(config.getIntegerValue("numUR"), dac.getWorkload().getUrs().size());
    //     dac.run();
    //     assertEquals(config.getIntegerValue("numUR") + 30 * dac.rateAssignUser,
    //             dac.getWorkload().getUrs().size());
    //     assertEquals(4 * 30 * dac.rateAssignUser, dac.txs.size());
    // }
}

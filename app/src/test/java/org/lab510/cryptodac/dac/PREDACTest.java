package org.lab510.cryptodac.dac;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.lab510.cryptodac.config.ConfigParser;
import org.lab510.cryptodac.config.ConfigProcessor;
import org.lab510.cryptodac.config.Configuration;

public class PREDACTest {
    private static PREDAC dac = null;
    private static Configuration config = null;

    @BeforeEach
    public void setup() {
        ConfigParser parser = new ConfigParser();
        try {
            config = parser.parse("test.properties");
            new ConfigProcessor().process(config);
        } catch (IOException e) {
            fail();
        }
        dac = new PREDAC(config);
        dac.initializeWorkload();
    }

    @Test
    public void testAddUser() {
        dac.addUser();
        assertEquals(config.getIntegerValue("numUser") + 1, dac.getWorkload().getUsers().size());
        assertEquals(1, dac.txs.size());
    }

    @Test
    public void testAddPerm() {
        dac.addPerm();
        assertEquals(config.getIntegerValue("numPerm") + 1, dac.getWorkload().getPerms().size());
        assertEquals(3, dac.txs.size());
    }

    @Test
    public void testAddRole() {
        dac.addRole();
        assertEquals(config.getIntegerValue("numRole") + 1, dac.getWorkload().getRoles().size());
        assertEquals(3, dac.txs.size());
    }

    @Test
    public void testRemovePerm() {
        dac.removePerm();
        assertEquals(config.getIntegerValue("numPerm") - 1, dac.getWorkload().getPerms().size());
        assertEquals(0, dac.txs.size());
    }

    @Test
    public void testRemoveRole() {
        dac.removeRole();
        assertEquals(config.getIntegerValue("numRole") - 1, dac.getWorkload().getRoles().size());
        System.out.println("# of transactions generated in testRemoveRole : " + dac.txs.size());
    }

    @Test
    public void testRevokePerm() {
        dac.revokePa();
        assertEquals(config.getIntegerValue("numPA") - 1, dac.getWorkload().getPas().size());
        assertEquals(5, dac.txs.size());
    }

    @Test
    public void testRevokeUr() {
        var ur = dac.revokeUr();
        assertEquals(config.getIntegerValue("numUR") - 1, dac.getWorkload().getUrs().size());

        var pasSize = dac.getWorkload().getPas(ur.getRole()).size();
        assertEquals(5*pasSize + 3, dac.txs.size());
        System.out.println("# of transactions generated in testRevokeRole : " + dac.txs.size());
    }

    @Test
    /**
     * 管理员控制云端增加绑定数据⟨u,r⟩
     */
    public void testAssignUser() {
        dac.assignRole();
        assertEquals(config.getIntegerValue("numUser"), dac.getWorkload().getUsers().size());
        assertEquals(config.getIntegerValue("numRole"), dac.getWorkload().getRoles().size());
        assertEquals(config.getIntegerValue("numUR") + 1, dac.getWorkload().getUrs().size());
    }
}

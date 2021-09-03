package org.lab510.cryptodac.workload;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.lab510.cryptodac.config.Configuration;
import org.lab510.cryptodac.config.ConfigParser;

import java.util.List;

public class WorkloadInitializerTest {

    @Test
    void workloadInitTest() {
        Configuration conf = null;

        try {
            conf = new ConfigParser().parse("test.properties");
        }catch(Exception e) {
            fail();
        }

        Workload workload = new Workload();
        assertNotNull(workload);
        WorkloadInitializer initializer = new WorkloadInitializer(workload, conf);
        initializer.initialize();
        List<Integer> params = workload.params();
        assertEquals(79, params.get(0));
        assertEquals(231, params.get(1));
        assertEquals(20, params.get(2));
        assertEquals(75, params.get(3));
        assertEquals(629, params.get(4));

        assertEquals(3, workload.getUserMax().numRoles());
        assertEquals(0, workload.getUserMin().numRoles());
        assertEquals(30, workload.getRoleMaxU().numUsers());
        assertEquals(1, workload.getRoleMinU().numUsers());

        assertEquals(209, workload.getRoleMaxP().numPerms());
        assertEquals(1, workload.getRoleMinP().numPerms());
        assertEquals(10, workload.getPermMax().numRoles());
        assertEquals(1, workload.getPermMin().numRoles());

        for(User user: workload.getUsers()) {
            assertTrue(user.numRoles()<=3);
            assertTrue(user.numRoles()>=0);
        }

        for(Role role: workload.getRoles()) {
            assertTrue(role.numUsers()>=1);
            assertTrue(role.numUsers()<=30);
            assertTrue(role.numPerms()>=1);
            assertTrue(role.numPerms()<=209);
        }

        for(Perm perm: workload.getPerms()) {
            assertTrue(perm.numRoles()>=1);
            assertTrue(perm.numRoles()<=10);
        }

        assertNotEquals(workload.getPermMax(), workload.getPermMin());
        assertNotEquals(workload.getRoleMinP(), workload.getRoleMaxP());
        assertNotEquals(workload.getRoleMinU(), workload.getRoleMaxU());
        assertNotEquals(workload.getPermMax(), workload.getPermMin());


    }

    private void workloadInitTest(String filename) {
        Configuration conf = null;

        try {
            conf = new ConfigParser().parse(filename);
        }catch(Exception e) {
            fail();
        }
        assertNotNull(conf);
        Workload workload = new Workload();
        assertNotNull(workload);
        WorkloadInitializer initializer = new WorkloadInitializer(workload, conf);
        initializer.initialize();

        String [] paramNames = {
            "numUser",
            "numPerm",
            "numRole",
            "numUR",
            "numPA",
        };

        List<Integer> params = workload.params();
        for(int i=0; i<5; i++) {
            assertEquals(conf.getIntegerValue(paramNames[i]), params.get(i));
        }

        int maxRolesPerUser = conf.getIntegerValue("maxRolesPerUser");
        int minRolesPerUser = conf.getIntegerValue("minRolesPerUser");
        int maxUsersPerRole = conf.getIntegerValue("maxUsersPerRole");
        int minUsersPerRole = conf.getIntegerValue("minUsersPerRole");

        assertEquals(maxRolesPerUser, workload.getUserMax().numRoles());
        assertEquals(minRolesPerUser, workload.getUserMin().numRoles());
        assertEquals(maxUsersPerRole, workload.getRoleMaxU().numUsers());
        assertEquals(minUsersPerRole, workload.getRoleMinU().numUsers());

        int maxPermsPerRole = conf.getIntegerValue("maxPermsPerRole");
        int minPermsPerRole = conf.getIntegerValue("minPermsPerRole");
        int maxRolesPerPerm = conf.getIntegerValue("maxRolesPerPerm");
        int minRolesPerPerm = conf.getIntegerValue("minRolesPerPerm");
        assertEquals(maxRolesPerPerm, workload.getPermMax().numRoles());
        assertEquals(minRolesPerPerm, workload.getPermMin().numRoles());
        assertEquals(maxPermsPerRole, workload.getRoleMaxP().numPerms());
        assertEquals(minPermsPerRole, workload.getRoleMinP().numPerms());


        for(User user: workload.getUsers()) {
            assertTrue(user.numRoles()<=maxRolesPerUser);
            assertTrue(user.numRoles()>=minRolesPerUser);
        }

        for(Role role: workload.getRoles()) {
            assertTrue(role.numUsers()>=minUsersPerRole);
            assertTrue(role.numUsers()<=maxUsersPerRole);
            assertTrue(role.numPerms()>=minPermsPerRole);
            assertTrue(role.numPerms()<=maxPermsPerRole);
        }

        for(Perm perm: workload.getPerms()) {
            assertTrue(perm.numRoles()>=minRolesPerPerm);
            assertTrue(perm.numRoles()<=maxRolesPerPerm);
        }

        assertNotEquals(workload.getPermMax(), workload.getPermMin());
        assertNotEquals(workload.getRoleMinP(), workload.getRoleMaxP());
        assertNotEquals(workload.getRoleMinU(), workload.getRoleMaxU());
        assertNotEquals(workload.getPermMax(), workload.getPermMin());
    }

    @Test
    void workloadInitTestMore() {
        String [] files = {
            "domino.properties",
            "emea.properties",
            "firewall1.properties",
            "firewall2.properties",
            "healthcare.properties",
            "university.properties"
        };

        for (String filename : files) {
            workloadInitTest(filename);
        }
    }

}

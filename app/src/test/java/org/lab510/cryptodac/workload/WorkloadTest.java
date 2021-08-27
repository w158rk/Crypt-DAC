package org.lab510.cryptodac.workload;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.lab510.cryptodac.config.ConfigParser;
import org.lab510.cryptodac.config.Configuration;

public class WorkloadTest {
    @Test
    void workloadUserTest() {
        Workload workload = new Workload();

        for(int i=0; i<10; i++) {
            workload.addUser(new User());
            workload.addRole(new Role());
            workload.addPerm(new Perm());
        }

        assertNotNull(workload);
        boolean res = true;

        for(int i=0; i<4; i++) {
            res = workload.assign_user();
            assertTrue(res);
        }

        System.out.println();
        for(int i=0; i<4; i++) {
            res = workload.revoke_user();
            assertTrue(res);
        }
        res = workload.revoke_user();
        assertFalse(res);

        res = workload.assign_user();
        assertTrue(res);
        res = workload.assign_user();
        assertTrue(res);
        res = workload.revoke_user();
        assertTrue(res);
        res = workload.assign_user();
        assertTrue(res);

    }

    @Test
    void workloadPermTest() {
        Workload workload = new Workload();

        for(int i=0; i<10; i++) {
            workload.addUser(new User());
            workload.addRole(new Role());
            workload.addPerm(new Perm());
        }

        assertNotNull(workload);
        boolean res = true;

        for(int i=0; i<4; i++) {
            res = workload.assign_perm();
            assertTrue(res);
        }

        System.out.println();
        for(int i=0; i<4; i++) {
            res = workload.revoke_perm();
            assertTrue(res);
        }
        res = workload.revoke_perm();
        assertFalse(res);

        res = workload.assign_perm();
        assertTrue(res);
        res = workload.assign_perm();
        assertTrue(res);
        res = workload.revoke_perm();
        assertTrue(res);
        res = workload.assign_perm();
        assertTrue(res);

    }

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
        workload.initialize(conf);
        List<Integer> params = workload.params();
        // assertEquals(79-2, params.get(0));
        // assertEquals(231-2, params.get(1));
        // assertTrue(16<=params.get(2));
        // assertTrue(18>=params.get(2));
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
}

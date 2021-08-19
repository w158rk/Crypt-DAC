package org.lab510.cryptodac.workload;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

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


}

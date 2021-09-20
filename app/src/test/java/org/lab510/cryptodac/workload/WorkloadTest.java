package org.lab510.cryptodac.workload;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.lab510.cryptodac.error.Error;

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
            workload.assign_user();
        }

        for(int i=0; i<4; i++) {
            workload.revoke_user();
        }

        boolean errorCatched = false;
        try {
            workload.revoke_user();
        }
        catch(Error e) {
            errorCatched = true;
        }
        if(!errorCatched) fail();

        workload.assign_user();
        workload.assign_user();
        workload.revoke_user();
        workload.assign_user();

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

        boolean errorCatched = false;
        try {
            workload.revoke_perm();
        }
        catch(Error e) {
            errorCatched = true;
        }
        if(!errorCatched) fail();

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

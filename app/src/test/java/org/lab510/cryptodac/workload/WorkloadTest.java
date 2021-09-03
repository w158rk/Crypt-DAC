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
            res = workload.assign_user();
            assertTrue(res);
        }

        for(int i=0; i<4; i++) {
            res = workload.revoke_user();
            assertTrue(res);
        }

        boolean errorCatched = false;
        try {
            res = workload.revoke_user();
        }
        catch(Error e) {
            errorCatched = true;
        }
        if(!errorCatched) fail();

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

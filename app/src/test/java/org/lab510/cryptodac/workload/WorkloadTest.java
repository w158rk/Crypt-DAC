package org.lab510.cryptodac.workload;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.lab510.cryptodac.error.Error;

public class WorkloadTest {
    private Workload workload;

    @BeforeEach
    void initWorkloadTest() {
        workload = new Workload();

        for (int i = 0; i < 10; i++) {
            workload.addUser(new User());
            workload.addRole(new Role());
            workload.addPerm(new Perm());
        }

    }

    @Test
    void testGetPas() {
        int expected = 4;
        int actual = 0;

        for (int i = 0; i < expected; i++) {
            workload.assignPa();
        }

        for (var role : workload.getRoles()) {
            actual += workload.getPas(role).size();
        }

        assertEquals(expected, actual);
    }

    @Test
    void workloadUserTest() {

        for (int i = 0; i < 4; i++) {
            workload.assignUr();
        }

        for (int i = 0; i < 4; i++) {
            workload.revokeUr();
        }

        boolean errorCatched = false;
        try {
            workload.revokeUr();
        } catch (Error e) {
            errorCatched = true;
        }
        if (!errorCatched)
            fail();

        workload.assignUr();
        workload.assignUr();
        workload.revokeUr();
        workload.assignUr();

        for (int i=0; i < 10; i++) {
            workload.removeUser();
            assertEquals(9-i, workload.getUsers().size());
        }

        assertEquals(0, workload.getUrs().size());
    }


    @Test
    void workloadPermTest() {
        boolean res = true;

        for (int i = 0; i < 4; i++) {
            res = workload.assignPa();
            assertTrue(res);
        }

        for (int i = 0; i < 4; i++) {
            assertNotNull(workload.revokePa());
        }

        boolean errorCatched = false;
        try {
            workload.revokePa();
        } catch (Error e) {
            errorCatched = true;
        }
        if (!errorCatched)
            fail();

        res = workload.assignPa();
        assertTrue(res);
        res = workload.assignPa();
        assertTrue(res);
        assertNotNull(workload.revokePa());
        assertTrue(res);
        res = workload.assignPa();
        assertTrue(res);

    }


}

package org.lab510.cryptodac.workload;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class RoleTest {
    @Test
    void roleContainUserTest() {
        User user1 = new User();
        User user2 = new User();
        Role role  = new Role();

        assertFalse(role.contains(user1));
        assertFalse(role.contains(user2));

        UR ur1 = new UR();
        ur1.setUser(user1);
        ur1.setRole(role);
        ur1.add();
        assertEquals(1, role.numUsers());
        assertTrue(role.contains(user1));
        assertFalse(role.contains(user2));

        UR ur2 = new UR();
        ur2.setUser(user2);
        ur2.setRole(role);
        ur2.add();
        assertEquals(2, role.numUsers());
        assertTrue(role.contains(user1));
        assertTrue(role.contains(user2));
    }
}

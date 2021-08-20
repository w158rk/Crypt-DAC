package org.lab510.cryptodac.workload;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class UserTest {
    @Test
    void userContainTest() {
        IUser user = new User();
        IRole role1  = new Role();
        IRole role2 = new Role();

        assertFalse(user.contains(role1));
        assertFalse(user.contains(role2));

        IUR ur1 = new UR();
        ur1.setUser(user);
        ur1.setRole(role1);
        ur1.add();
        assertEquals(1, user.numRoles());
        assertTrue(user.contains(role1));
        assertFalse(user.contains(role2));

        IUR ur2 = new UR();
        ur2.setUser(user);
        ur2.setRole(role2);
        ur2.add();
        assertEquals(2, user.numRoles());
        assertTrue(user.contains(role1));
        assertTrue(user.contains(role2));
    }
}

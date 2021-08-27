package org.lab510.cryptodac.workload;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class URTest {
    @Test
    void testEquals() {
        User user = new User();
        Role role = new Role();
        UR ur1 = new UR();
        ur1.setRole(role);
        ur1.setUser(user);
        UR ur2 = new UR();
        ur2.setRole(role);
        ur2.setUser(user);

        assertTrue(ur1.equals(ur2));
    }
}

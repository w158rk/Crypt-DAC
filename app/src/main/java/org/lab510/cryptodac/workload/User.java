package org.lab510.cryptodac.workload;

import java.util.HashSet;
import java.util.Set;

/**
 * User
 *
 * @version 0.0.1
 */
public class User {
    private Set<UR> urs = null;

    User() {
        urs = new HashSet<UR>();
    }

    public Set<UR> getUrs() {
        return urs;
    }

    public boolean add(UR ur) {
        return urs.add(ur);
    }

    public boolean remove(UR ur) {
        return urs.remove(ur);
    }

    public int numRoles() {
        if(urs==null) return 0;
        return urs.size();
    }

    public boolean contains(Role role) {
        if(urs==null) return false;
        UR ur = new UR();
        ur.setUser(this);
        ur.setRole(role);
        return urs.contains(ur);
    }

}

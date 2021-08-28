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

    /**
     * default constructor
     */
    User() {
        urs = new HashSet<UR>();
    }

    /**
     * link a UR relation
     * @param ur UR object
     * @return true if success
     */
    public boolean add(UR ur) {
        return urs.add(ur);
    }

    /**
     * unlink a UR relation
     * @param ur UR object
     * @return true if success
     */
    public boolean remove(UR ur) {
        return urs.remove(ur);
    }

    /**
     * get the number of roles linked to this user
     * @return the number of roles linked to this user
     */
    public int numRoles() {
        if(urs==null) return 0;
        return urs.size();
    }

    /**
     * tell whether the role is linked to this user
     * @param role Role object
     * @return true if the role is linked to this user
     */
    public boolean contains(Role role) {
        if(urs==null) return false;
        UR ur = new UR();
        ur.setUser(this);
        ur.setRole(role);
        return urs.contains(ur);
    }

}

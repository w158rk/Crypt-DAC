package org.lab510.cryptodac.workload;

import java.util.HashSet;
import java.util.Set;

/**
 * Permission
 *
 * @version 0.0.1
 */
public class Perm {

    private Set<PA> pas = null;

    /**
     * default constructor
     */
    Perm() {
        pas = new HashSet<PA>();
    }

    /**
     * link a PA object to this object
     * @param pa PA object to be linked
     * @return true if success
     */
    public boolean add(PA pa) {
        return pas.add(pa);
    }

    /**
     * remove the link to a PA object
     * @param pa PA object to be remoed
     * @return true if success
     */
    public boolean remove(PA pa) {
        return pas.remove(pa);
    }

    /**
     * get the number of roles linked to this permission
     * @return the number of roles linked to this permission
     */
    public int numRoles() {
        if(pas==null) return 0;
        return pas.size();
    }

    /**
     * tell the answer to whether the role is linked to this permission
     * @param role Role object
     * @return true if the role is linked
     */
    public boolean contains(Role role) {
        if(pas==null) return false;
        PA pa = new PA(this, role);
        return pas.contains(pa);
    }

}

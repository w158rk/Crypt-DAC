package org.lab510.cryptodac.workload;

import java.util.HashSet;
import java.util.Set;

/**
 * Role
 *
 * @version 0.0.1
 */
public class Role {
    private Set<UR> urs = null;
    private Set<PA> pas = null;

    /**
     * default constructor
     */
    Role() {
        urs = new HashSet<UR>();
        pas = new HashSet<PA>();
    }

    /**
     * link a UR object
     * @param ur UR object
     * @return true if success
     */
    public boolean add(UR ur) {
        return urs.add(ur);
    }

    /**
     * unlink a UR object
     * @param ur UR object
     * @return true if success
     */
    public boolean remove(UR ur) {
        return urs.remove(ur);
    }

    /**
     * link a PA object
     * @param pa PA object
     * @return true if success
     */
    public boolean add(PA pa) {
        return pas.add(pa);
    }

    /**
     * unlink a PA object
     * @param pa PA object
     * @return true if success
     */
    public boolean remove(PA pa) {
        return pas.remove(pa);
    }

    /**
     * get the number of users linked to this role
     * @return the number of users linked to this role
     */
    public int numUsers() {
        if(urs==null) return 0;
        return urs.size();
    }

    /**
     * get the number of permissions linked to this role
     * @return the number of permissions linked to this role
     */
    public int numPerms() {
        return pas.size();
    }

    /**
     * tell whether the user is linked to this role
     * @param user User object
     * @return true if the user is linked
     */
    public boolean contains(User user) {
        if(urs==null) return false;
        UR ur = new UR();
        ur.setRole(this);
        ur.setUser(user);
        return urs.contains(ur);
    }

    /**
     * tell whether the permission is linked to this role
     * @param perm Permission object
     * @return true if the permissioni is linked
     */
    public boolean contains(Perm perm) {
        if(pas==null) return false;
        PA pa = new PA(perm, this);
        return pas.contains(pa);
    }
}

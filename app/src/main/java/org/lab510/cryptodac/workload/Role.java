package org.lab510.cryptodac.workload;

import java.util.HashSet;
import java.util.Set;

public class Role {
    private Set<UR> urs = null;
    private Set<PA> pas = null;

    Role() {
        urs = new HashSet<UR>();
        pas = new HashSet<PA>();
    }

    public boolean add(UR ur) {
        return urs.add(ur);
    }

    public boolean remove(UR ur) {
        return urs.remove(ur);
    }

    public boolean add(PA pa) {
        return pas.add(pa);
    }

    public boolean remove(PA pa) {
        return pas.remove(pa);
    }

    public int numUsers() {
        if(urs==null) return 0;
        return urs.size();
    }

    public int numPerms() {
        // TODO Auto-generated method stub
        return 0;
    }

    public boolean contains(User user) {
        if(urs==null) return false;
        UR ur = new UR();
        ur.setRole(this);
        ur.setUser(user);
        return urs.contains(ur);
    }


    public boolean contains(Perm perm) {
        // TODO Auto-generated method stub
        return false;
    }
}

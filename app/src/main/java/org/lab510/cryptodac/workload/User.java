package org.lab510.cryptodac.workload;

import java.util.HashSet;
import java.util.Set;

public class User implements IUser {
    private Set<IUR> urs = null;

    User() {
        urs = new HashSet<IUR>();
    }


    @Override
    public boolean add(IUR ur) {
        return urs.add(ur);
    }

    @Override
    public boolean remove(IUR ur) {
        return urs.remove(ur);
    }


    @Override
    public int numRoles() {
        if(urs==null) return 0;
        return urs.size();
    }


    @Override
    public boolean contains(IRole role) {
        if(urs==null) return false;
        IUR ur = new UR();
        ur.setUser(this);
        ur.setRole(role);
        return urs.contains(ur);
    }

}

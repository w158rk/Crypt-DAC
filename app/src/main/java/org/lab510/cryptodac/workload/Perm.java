package org.lab510.cryptodac.workload;

import java.util.HashSet;
import java.util.Set;

public class Perm {

    private Set<PA> pas = null;

    Perm() {
        pas = new HashSet<PA>();
    }

    public boolean add(PA pa) {
        return pas.add(pa);
    }

    public boolean remove(PA pa) {
        return pas.remove(pa);
    }

    public int numRoles() {
        if(pas==null) return 0;
        return pas.size();
    }


    public boolean contains(Role role) {
        if(pas==null) return false;
        PA pa = new PA(this, role);
        return pas.contains(pa);
    }

}

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

}

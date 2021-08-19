package org.lab510.cryptodac.workload;

import java.util.HashSet;
import java.util.Set;

public class Role implements IRole {
    private Set<IUR> urs = null;
    private Set<IPA> pas = null;

    Role() {
        urs = new HashSet<IUR>();
        pas = new HashSet<IPA>();
    }

    @Override
    public boolean add(IUR ur) {
        return urs.add(ur);
    }
    @Override
    public boolean visit(IPA pa) {
        return pas.add(pa);
    }

    @Override
    public boolean remove(IUR ur) {
        return urs.remove(ur);
    }
}

package org.lab510.cryptodac.workload;

import java.util.HashSet;
import java.util.Set;

public class Perm implements IPerm {

    private Set<IPA> pas = null;

    Perm() {
        pas = new HashSet<IPA>();
    }

    @Override
    public boolean visit(IPA pa) {
        return pas.add(pa);
    }

}

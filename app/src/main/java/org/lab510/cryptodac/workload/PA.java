package org.lab510.cryptodac.workload;

public class PA implements IPA{

    @Override
    public boolean accept(IPerm perm) {
        return perm.visit(this);
    }

    @Override
    public boolean accept(IRole role) {
        return role.visit(this);
    }

}

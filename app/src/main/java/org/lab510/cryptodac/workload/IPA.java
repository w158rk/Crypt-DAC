package org.lab510.cryptodac.workload;

public interface IPA {
    public boolean accept(IPerm perm);
    public boolean accept(IRole role);
}

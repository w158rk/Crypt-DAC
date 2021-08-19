package org.lab510.cryptodac.workload;

public interface IRole {
    public boolean add(IUR ur);
    public boolean remove(IUR ur);
    public boolean visit(IPA pa);
}

package org.lab510.cryptodac.workload;

public interface IUser {
    public boolean add(IUR ur);
    public boolean remove(IUR ur);
    public int numRoles();
    public boolean contains(IRole role);
}

package org.lab510.cryptodac.workload;

public interface IRole {
    public boolean add(IUR ur);
    public boolean remove(IUR ur);
    public boolean add(IPA pa);
    public boolean remove(IPA pa);
    public int numUsers();
    public int numPerms();
    public boolean contains(IUser user);
    public boolean contains(IPerm perm);
}

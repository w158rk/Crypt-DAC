package org.lab510.cryptodac.workload;

public interface IUR {
    public boolean add();
    public boolean remove();
    public IUser getUser();
    public IRole getRole();
    public void setUser(IUser user);
    public void setRole(IRole role);
}

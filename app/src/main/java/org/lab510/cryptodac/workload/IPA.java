package org.lab510.cryptodac.workload;

public interface IPA {
    public boolean add();
    public boolean remove();
    public boolean equals(IPA pa);
    public IRole getRole();
    public IPerm getPerm();
    public void setRole(IRole role);
    public void setPerm(IPerm perm);
}

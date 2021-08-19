package org.lab510.cryptodac.workload;

public class PA implements IPA{

    IPerm perm = null;
    IRole role = null;

    @Override
    public boolean add() {
        return (perm!=null)
            && (role!=null)
            && perm.add(this)
            && role.add(this);
    }

    @Override
    public boolean remove() {
        return (perm!=null)
        && (role!=null)
        && perm.remove(this)
        && role.remove(this);
    }

    @Override
    public boolean equals(IPA pa) {
        return (perm==pa.getPerm()) && (role==pa.getRole());
    }

    @Override
    public IRole getRole() {
        return role;
    }

    @Override
    public IPerm getPerm() {
        return perm;
    }

    @Override
    public void setRole(IRole role) {
        this.role = role;
    }

    @Override
    public void setPerm(IPerm perm) {
        this.perm = perm;
    }

}

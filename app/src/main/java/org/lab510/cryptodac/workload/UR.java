package org.lab510.cryptodac.workload;

public class UR implements IUR{

    private IUser user = null;
    private IRole role = null;

    public UR() {

    }

    public UR(IUser user, IRole role) {
        this.user = user;
        this.role = role;
    }

    @Override
    public boolean equals(Object ur) {
        if(this.user==((UR)ur).getUser() && this.role==((UR)ur).getRole()) return true;
        else return false;
    }

    @Override
    public int hashCode() {
        return user.hashCode() + role.hashCode();
    }

    @Override
    public IUser getUser() {
        return user;
    }

    @Override
    public IRole getRole() {
        return role;
    }

    @Override
    public boolean add() {
        if(user==null || role==null) return false;
        return user.add(this) && role.add(this);
    }

    @Override
    public boolean remove() {
        if(user==null || role==null) return false;
        return user.remove(this) && role.remove(this);
    }

    @Override
    public void setUser(IUser user) {
        this.user = user;
    }

    @Override
    public void setRole(IRole role) {
        this.role = role;
    }

}

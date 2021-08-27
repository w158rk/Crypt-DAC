package org.lab510.cryptodac.workload;

public class PA {

    Perm perm = null;
    Role role = null;

    public boolean add() {
        return (perm!=null)
            && (role!=null)
            && perm.add(this)
            && role.add(this);
    }

    public boolean remove() {
        return (perm!=null)
        && (role!=null)
        && perm.remove(this)
        && role.remove(this);
    }

    public boolean equals(PA pa) {
        return (perm==pa.getPerm()) && (role==pa.getRole());
    }

    public Role getRole() {
        return role;
    }

    public Perm getPerm() {
        return perm;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setPerm(Perm perm) {
        this.perm = perm;
    }

    public PA(Perm perm, Role role) {
        this.perm = perm;
        this.role = role;
    }

    public PA() {

    }

}

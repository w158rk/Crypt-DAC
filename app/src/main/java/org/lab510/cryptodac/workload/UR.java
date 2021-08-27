package org.lab510.cryptodac.workload;

public class UR {

    private User user = null;
    private Role role = null;

    public UR() {

    }

    public UR(User user, Role role) {
        this.user = user;
        this.role = role;
    }

    public boolean equals(Object ur) {
        if(this.user==((UR)ur).getUser() && this.role==((UR)ur).getRole()) return true;
        else return false;
    }

    public int hashCode() {
        return user.hashCode() + role.hashCode();
    }

    public User getUser() {
        return user;
    }

    public Role getRole() {
        return role;
    }

    public boolean add() {
        if(user==null || role==null) return false;
        return user.add(this) && role.add(this);
    }

    public boolean remove() {
        if(user==null || role==null) return false;
        return user.remove(this) && role.remove(this);
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setRole(Role role) {
        this.role = role;
    }

}

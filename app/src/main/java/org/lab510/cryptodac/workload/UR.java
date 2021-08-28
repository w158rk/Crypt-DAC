package org.lab510.cryptodac.workload;

/**
 * User-Role Relation
 *
 * @version 0.0.1
 */
public class UR {

    private User user = null;
    private Role role = null;

    /**
     * default constructor
     */
    public UR() {

    }

    /**
     * constructor with user and role
     * @param user User object
     * @param role Role object
     */
    public UR(User user, Role role) {
        this.user = user;
        this.role = role;
    }

    /**
     * overrided equals
     * @param ur UR object
     * @return true if the users and roles of the two UR objects are the same
     */
    @Override
    public boolean equals(Object ur) {
        if(this.user==((UR)ur).getUser() && this.role==((UR)ur).getRole()) return true;
        else return false;
    }

    /**
     * overrided hashcode for now
     * @return the combination of user.hashcode and role.hashcode
     */
    @Override
    public int hashCode() {
        return user.hashCode() + role.hashCode();
    }

    /**
     * getter
     * @return User object
     */
    public User getUser() {
        return user;
    }

    /**
     * getter
     * @return Role object
     */
    public Role getRole() {
        return role;
    }

    /**
     * update the user and role with this UR relation
     * @return true if updating successfully
     */
    public boolean add() {
        if(user==null || role==null) return false;
        return user.add(this) && role.add(this);
    }

    /**
     * remove this UR relation from the user and role
     * @return true if success
     */
    public boolean remove() {
        if(user==null || role==null) return false;
        return user.remove(this) && role.remove(this);
    }

    /**
     * setter
     * @param user User object
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * setter
     * @param role Role object
     */
    public void setRole(Role role) {
        this.role = role;
    }

}

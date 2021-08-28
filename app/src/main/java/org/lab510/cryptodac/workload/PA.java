package org.lab510.cryptodac.workload;

/**
 * Permission Assignment
 *
 * @version 0.0.1
 */
public class PA {

    Perm perm = null;
    Role role = null;

    /**
     * update the corresponding Perm and Role objects to add this relation
     * @return true if updating successfully
     */
    public boolean add() {
        return (perm!=null)
            && (role!=null)
            && perm.add(this)
            && role.add(this);
    }

    /**
     * update the corresponding Perm and Role objects to remove this relation
     * @return true if updating successfully
     */
    public boolean remove() {
        return (perm!=null)
        && (role!=null)
        && perm.remove(this)
        && role.remove(this);
    }

    /**
     * override equals
     * @param pa PA object
     * @return true if the perms and roles of the two PA objects are the same
     */
    @Override
    public boolean equals(Object pa) {
        return (perm==((PA)pa).getPerm()) && (role==((PA)pa).getRole());
    }

    /**
     * getter
     * @return Role object
     */
    public Role getRole() {
        return role;
    }

    /**
     * getter
     * @return Perm object
     */
    public Perm getPerm() {
        return perm;
    }

    /**
     * setter
     * @param role Role object
     */
    public void setRole(Role role) {
        this.role = role;
    }

    /**
     * setter
     * @param perm Perm object
     */
    public void setPerm(Perm perm) {
        this.perm = perm;
    }
    /**
     * constructor
     * @param perm Perm object
     * @param role Role object
     */
    public PA(Perm perm, Role role) {
        this.perm = perm;
        this.role = role;
    }

    /**
     * default constructor
     */
    public PA() {

    }

}

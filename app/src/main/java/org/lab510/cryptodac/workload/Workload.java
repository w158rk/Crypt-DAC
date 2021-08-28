package org.lab510.cryptodac.workload;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.lab510.cryptodac.config.Configuration;
import org.lab510.cryptodac.utils.RandomPicker;

/**
 * Workload, representing a DAC system
 *
 * @version 0.0.1
 */
public class Workload {

    private Set<User> users = null;
    private Set<Perm> perms = null;
    private Set<Role> roles = null;
    private Set<PA> pas = null;
    private Set<UR> urs = null;
    User userMax = null, userMin = null;
    Role roleMaxU = null, roleMinU = null;
    Role roleMaxP = null, roleMinP = null;
    Perm permMax = null, permMin = null;

    /**
     * getter
     * @return
     */
    public User getUserMax() {
        return userMax;
    }

    /**
     * getter
     * @return
     */
    public User getUserMin() {
        return userMin;
    }

    /**
     * getter
     * @return
     */
    public Role getRoleMaxP() {
        return roleMaxP;
    }

    /**
     * getter
     * @return
     */
    public Role getRoleMinP() {
        return roleMinP;
    }

    /**
     * getter
     * @return
     */
    public Role getRoleMaxU() {
        return roleMaxU;
    }

    /**
     * getter
     * @return
     */
    public Role getRoleMinU() {
        return roleMinU;
    }

    /**
     * getter
     * @return
     */
    public Perm getPermMax() {
        return permMax;
    }

    /**
     * getter
     * @return
     */
    public Perm getPermMin() {
        return permMin;
    }

    /**
     * setter
     * @param userMax User object
     */
    public void setUserMax(User userMax) {
        this.userMax = userMax;
    }

    /**
     * setter
     * @param userMin User object
     */
    public void setUserMin(User userMin) {
        this.userMin = userMin;
    }

    /**
     * setter
     * @param roleMaxP Role object
     */
    public void setRoleMaxP(Role roleMaxP) {
        this.roleMaxP = roleMaxP;
    }

    /**
     * setter
     * @param roleMinP Role object
     */
    public void setRoleMinP(Role roleMinP) {
        this.roleMinP = roleMinP;
    }

    /**
     * setter
     * @param roleMaxU Role object
     */
    public void setRoleMaxU(Role roleMaxU) {
        this.roleMaxU = roleMaxU;
    }


    /**
     * setter
     * @param roleMinU Role object
     */
    public void setRoleMinU(Role roleMinU) {
        this.roleMinU = roleMinU;
    }

    /**
     * setter
     * @param permMax Perm object
     */
    public void setPermMax(Perm permMax) {
        this.permMax = permMax;
    }

    /**
     * setter
     * @param permMin Perm object
     */
    public void setPermMin(Perm permMin) {
        this.permMin = permMin;
    }

    /**
     * default constructor
     */
    public Workload() {
        users = new HashSet<User>();
        perms = new HashSet<Perm>();
        roles = new HashSet<Role>();
        pas = new HashSet<PA>();
        urs = new HashSet<UR>();
    }

    /**
     * getter
     * @return
     */
    public Set<User> getUsers() {
        return users;
    }

    /**
     * getter
     * @return
     */
    public Set<Perm> getPerms() {
        return perms;
    }

    /**
     * getter
     * @return
     */
    public Set<Role> getRoles() {
        return roles;
    }

    /**
     * getter
     * @return
     */
    public Set<UR> getUrs() {
        return urs;
    }

    /**
     * getter
     * @return
     */
    public Set<PA> getPas() {
        return pas;
    }

    /**
     * add a user into the workload
     * @param user User object
     * @return true if success
     */
    public boolean addUser(User user) {
        return users.add(user);
    }

    /**
     * add a role into the workload
     * @param role Role object
     * @return true if success
     */
    public boolean addRole(Role role) {
        return roles.add(role);
    }

    /**
     * add a permission into the workload
     * @param perm Perm object
     * @return true if success
     */
    public boolean addPerm(Perm perm) {
        return perms.add(perm);
    }

    /**
     * assign a random user to a random role
     * @return true if success
     */
    public boolean assign_user() {
        UR ur = new UR();
        boolean res = false;
        RandomPicker<User> userPicker = new RandomPicker<User>(users);
        RandomPicker<Role> rolePicker = new RandomPicker<Role>(roles);

        while(!res) {
            ur.setUser(userPicker.getRandomElement());
            ur.setRole(rolePicker.getRandomElement());
            res = ur.add();
        }

        res = res && urs.add(ur);
        return res;
    }

    /**
     * assign a given user to a given role
     * @param user User object, in the workload
     * @param role Role object, in the workload
     * @return true if success
     */
    public boolean assign_user(User user, Role role) {
        UR ur = new UR();
        ur.setUser(user);
        ur.setRole(role);
        boolean res = ur.add();
        res = res && urs.add(ur);
        return res;
    }

    /**
     * assign a random permission to a random role
     * @return true if success
     */
    public boolean assign_perm() {
        PA pa = new PA();
        boolean res = false;
        RandomPicker<Perm> permPicker = new RandomPicker<Perm>(perms);
        RandomPicker<Role> rolePicker = new RandomPicker<Role>(roles);

        while(!res) {
            pa.setPerm(permPicker.getRandomElement());
            pa.setRole(rolePicker.getRandomElement());
            res = pa.add();
        }

        pas.add(pa);
        return res;
    }

    /**
     * assign a given permission to a given role
     * @param perm Perm object, in the workload
     * @param role Role object, in the workload
     * @return true if success
     */
    public boolean assign_perm(Perm perm, Role role) {
        PA pa = new PA();
        pa.setPerm(perm);
        pa.setRole(role);
        boolean res = pa.add();
        res = res && pas.add(pa);
        return res;
    }

    /**
     * revoke a random user from a random role
     * @return true if success
     */
    public boolean revoke_user() {
        UR ur = new RandomPicker<UR>(urs).getRandomElement();
        if(ur==null) return false;
        return urs.remove(ur) && ur.remove();
    }

    /**
     * revoke a random permission from a random role
     * @return true if success
     */
    public boolean revoke_perm() {
        PA pa = new RandomPicker<PA>(pas).getRandomElement();
        return (pa!=null) && pas.remove(pa) && pa.remove();
    }

    /**
     * print the set sizes of the workload, including<br>
     * - users <br>
     * - roles <br>
     * - perms <br>
     * - urs <br>
     * - pas <br>
     */
    public void print_sizes() {
        System.out.println("size of users: " + users.size());
        System.out.println("size of roles: " + roles.size());
        System.out.println("size of perms: " + perms.size());
        System.out.println("size of urs: " + urs.size());
        System.out.println("size of pas: " + pas.size());
    }


    /**
     * initialize the workload with configuration
     * @param conf given configuration
     * @return true if success
     */
    public boolean initialize(Configuration conf) {
        return new WorkloadInitializer().initialize(this, conf);
    }

    /**
     * return the list containing the set sizes of the workload, including <br>
     * - users <br>
     * - roles <br>
     * - perms <br>
     * - urs <br>
     * - pas <br>
     * @return an integer list
     */
    public List<Integer> params() {
        List<Integer> ret = new ArrayList<Integer>();
        ret.add(Integer.valueOf(users.size()));
        ret.add(Integer.valueOf(perms.size()));
        ret.add(Integer.valueOf(roles.size()));
        ret.add(Integer.valueOf(urs.size()));
        ret.add(Integer.valueOf(pas.size()));
        return ret;
    }


}

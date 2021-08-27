package org.lab510.cryptodac.workload;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.lab510.cryptodac.config.Configuration;
import org.lab510.cryptodac.utils.RandomPicker;

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

    public User getUserMax() {
        return userMax;
    }

    public User getUserMin() {
        return userMin;
    }

    public Role getRoleMaxP() {
        return roleMaxP;
    }

    public Role getRoleMinP() {
        return roleMinP;
    }

    public Role getRoleMaxU() {
        return roleMaxU;
    }

    public Role getRoleMinU() {
        return roleMinU;
    }

    public Perm getPermMax() {
        return permMax;
    }

    public Perm getPermMin() {
        return permMin;
    }

    public void setUserMax(User userMax) {
        this.userMax = userMax;
    }

    public void setUserMin(User userMin) {
        this.userMin = userMin;
    }

    public void setRoleMaxP(Role roleMaxP) {
        this.roleMaxP = roleMaxP;
    }

    public void setRoleMinP(Role roleMinP) {
        this.roleMinP = roleMinP;
    }

    public void setRoleMaxU(Role roleMaxU) {
        this.roleMaxU = roleMaxU;
    }


    public void setRoleMinU(Role roleMinU) {
        this.roleMinU = roleMinU;
    }

    public void setPermMax(Perm permMax) {
        this.permMax = permMax;
    }

    public void setPermMin(Perm permMin) {
        this.permMin = permMin;
    }

    public Workload() {
        users = new HashSet<User>();
        perms = new HashSet<Perm>();
        roles = new HashSet<Role>();
        pas = new HashSet<PA>();
        urs = new HashSet<UR>();
    }

    public Set<User> getUsers() {
        return users;
    }

    public Set<Perm> getPerms() {
        return perms;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public Set<UR> getUrs() {
        return urs;
    }

    public Set<PA> getPas() {
        return pas;
    }

    public boolean addUser(User user) {
        return users.add(user);
    }

    public boolean addRole(Role role) {
        return roles.add(role);
    }

    public boolean addPerm(Perm perm) {
        return perms.add(perm);
    }

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

    public boolean assign_user(User user, Role role) {
        UR ur = new UR();
        ur.setUser(user);
        ur.setRole(role);
        boolean res = ur.add();
        res = res && urs.add(ur);
        return res;
    }


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

    public boolean assign_perm(Perm perm, Role role) {
        PA pa = new PA();
        pa.setPerm(perm);
        pa.setRole(role);
        boolean res = pa.add();
        res = res && pas.add(pa);
        return res;
    }

    public boolean revoke_user() {
        UR ur = new RandomPicker<UR>(urs).getRandomElement();
        if(ur==null) return false;
        return urs.remove(ur) && ur.remove();
    }

    public boolean revoke_perm() {
        PA pa = new RandomPicker<PA>(pas).getRandomElement();
        return (pa!=null) && pas.remove(pa) && pa.remove();
    }

    public void print_sizes() {
        System.out.println("size of users: " + users.size());
        System.out.println("size of roles: " + roles.size());
        System.out.println("size of perms: " + perms.size());
        System.out.println("size of urs: " + urs.size());
        System.out.println("size of pas: " + pas.size());
    }



    public boolean initialize(Configuration conf) {
        return new WorkloadInitializer().initialize(this, conf);
    }

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

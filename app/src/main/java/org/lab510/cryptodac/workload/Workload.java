package org.lab510.cryptodac.workload;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.lab510.cryptodac.error.Error;
import org.lab510.cryptodac.utils.RandomPicker;
import org.lab510.cryptodac.utils.SetFilter;

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
    private boolean initialized = false;

    public boolean isInitialized() {
        return initialized;
    }

    public void setInitialized() {
        initialized = true;
    }

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

    public boolean addUser() {
        return users.add(new User());
    }

    public boolean addRole() {
        return roles.add(new Role());
    }

    public boolean addPerm() {
        return perms.add(new Perm());
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

    public User getRandomUser() {
        RandomPicker<User> picker = new RandomPicker<User>(users);
        return picker.getRandomElement();
    }

    public Role getRandomRole() {
        RandomPicker<Role> picker = new RandomPicker<Role>(roles);
        return picker.getRandomElement();
    }

    public Perm getRandomPerm() {
        RandomPicker<Perm> picker = new RandomPicker<Perm>(perms);
        return picker.getRandomElement();
    }

    public UR getRandomUr() {
        RandomPicker<UR> picker = new RandomPicker<UR>(urs);
        return picker.getRandomElement();
    }

    public PA getRandomPa() {
        RandomPicker<PA> picker = new RandomPicker<PA>(pas);
        return picker.getRandomElement();
    }

    public boolean removeUser() {
        return removeUser(getRandomUser());
    }

    public Role removeRole() {
        return removeRole(getRandomRole());
    }

    public boolean removePerm() {
        return removePerm(getRandomPerm());
    }

    public boolean removeUser(User user) {
        // TODO
        var tUrs = getUrs(user);
        for (var ur : tUrs) {
            urs.remove(ur);
        }
        return users.remove(user);
    }

    public Role removeRole(Role role) {
        var tPas = getPas(role);
        var tUrs = getUrs(role);
        assert tPas.size() == 0;
        assert tUrs.size() == 0;

        if (!roles.remove(role)) {
            throw new Error("cannot remove the role");
        }

        return role;
    }

    public boolean removePerm(Perm perm) {
        // TODO
        // remove all the PAs linked to this permission
        var tPas = getPas(perm);
        for (var pa : tPas) {
            pas.remove(pa);
        }
        return perms.remove(perm);
    }

    public Set<PA> getPas(Role role) {
        return SetFilter.filter(pas, (PA pa) -> pa.getRole() == role);
    }

    public Set<PA> getPas(Perm perm) {
        return SetFilter.filter(pas, (PA pa) -> pa.getPerm() == perm);
    }

    public Set<UR> getUrs(Role role) {
        return SetFilter.filter(urs, (UR ur) -> ur.getRole() == role);
    }

    public Set<UR> getUrs(User user) {
        return SetFilter.filter(urs, (UR ur) -> ur.getUser() == user);
    }

    public void assignUr() {
        boolean res = false;

        while (!res) {
            res = assignUr(getRandomUser(), getRandomRole());
        }

    }

    public boolean assignUr(User user, Role role) {
        UR ur = new UR();
        ur.setUser(user);
        ur.setRole(role);
        boolean res = ur.add();
        res = res && urs.add(ur);
        return res;
    }

    public boolean assignPa() {
        boolean res = false;

        while (!res) {
            res = assignPerm(getRandomPerm(), getRandomRole());
        }

        return res;
    }

    public boolean assignPerm(Perm perm, Role role) {
        PA pa = new PA();
        pa.setPerm(perm);
        pa.setRole(role);
        boolean res = pa.add();
        res = res && pas.add(pa);
        return res;
    }

    public UR revokeUr() {
        return revokeUr(getRandomUr());
    }

    public UR revokeUr(UR ur) {
        if (ur==null || !urs.remove(ur) || !ur.remove()) {
            throw new Error("cannot revoke the role");
        }
        return ur;
    }

    public PA revokePa() {
        return revokePa(getRandomPa());
    }

    public PA revokePa(PA pa) {
        if ((pa == null) || !pas.remove(pa) || !pa.remove())
            throw new Error("cannnot revoke permission");
        return pa;
    }

    public void printSizes() {
        System.out.println("size of users: " + users.size());
        System.out.println("size of roles: " + roles.size());
        System.out.println("size of perms: " + perms.size());
        System.out.println("size of urs: " + urs.size());
        System.out.println("size of pas: " + pas.size());
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

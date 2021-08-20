package org.lab510.cryptodac.workload;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.lab510.cryptodac.config.Configuration;
import org.lab510.cryptodac.utils.RandomPicker;

public class Workload implements IWorkload{

    private Set<IUser> users = null;
    private Set<IPerm> perms = null;
    private Set<IRole> roles = null;
    private Set<IPA> pas = null;
    private Set<IUR> urs = null;
    IUser userMax = null, userMin = null;
    IRole roleMaxU = null, roleMinU = null;
    IRole roleMaxP = null, roleMinP = null;
    IPerm permMax = null, permMin = null;

    public IUser getUserMax() {
        return userMax;
    }

    public IUser getUserMin() {
        return userMin;
    }

    public IRole getRoleMaxP() {
        return roleMaxP;
    }

    public IRole getRoleMinP() {
        return roleMinP;
    }

    public IRole getRoleMaxU() {
        return roleMaxU;
    }

    public IRole getRoleMinU() {
        return roleMinU;
    }

    public IPerm getPermMax() {
        return permMax;
    }

    public IPerm getPermMin() {
        return permMin;
    }

    public void setUserMax(IUser userMax) {
        this.userMax = userMax;
    }

    public void setUserMin(IUser userMin) {
        this.userMin = userMin;
    }

    public void setRoleMaxP(IRole roleMaxP) {
        this.roleMaxP = roleMaxP;
    }

    public void setRoleMinP(IRole roleMinP) {
        this.roleMinP = roleMinP;
    }

    public void setRoleMaxU(IRole roleMaxU) {
        this.roleMaxU = roleMaxU;
    }


    public void setRoleMinU(IRole roleMinU) {
        this.roleMinU = roleMinU;
    }

    public void setPermMax(IPerm permMax) {
        this.permMax = permMax;
    }

    public void setPermMin(IPerm permMin) {
        this.permMin = permMin;
    }

    public Workload() {
        users = new HashSet<IUser>();
        perms = new HashSet<IPerm>();
        roles = new HashSet<IRole>();
        pas = new HashSet<IPA>();
        urs = new HashSet<IUR>();
    }

    public Set<IUser> getUsers() {
        return users;
    }

    public Set<IPerm> getPerms() {
        return perms;
    }

    public Set<IRole> getRoles() {
        return roles;
    }

    public Set<IUR> getUrs() {
        return urs;
    }

    public Set<IPA> getPas() {
        return pas;
    }

    public boolean addUser(IUser user) {
        return users.add(user);
    }

    public boolean addRole(IRole role) {
        return roles.add(role);
    }

    public boolean addPerm(IPerm perm) {
        return perms.add(perm);
    }

    @Override
    public boolean assign_user() {
        IUR ur = new UR();
        boolean res = false;
        RandomPicker<IUser> userPicker = new RandomPicker<IUser>(users);
        RandomPicker<IRole> rolePicker = new RandomPicker<IRole>(roles);

        while(!res) {
            ur.setUser(userPicker.getRandomElement());
            ur.setRole(rolePicker.getRandomElement());
            res = ur.add();
        }

        res = res && urs.add(ur);
        return res;
    }

    @Override
    public boolean assign_user(IUser user, IRole role) {
        IUR ur = new UR();
        ur.setUser(user);
        ur.setRole(role);
        boolean res = ur.add();
        res = res && urs.add(ur);
        return res;
    }


    @Override
    public boolean assign_perm() {
        IPA pa = new PA();
        boolean res = false;
        RandomPicker<IPerm> permPicker = new RandomPicker<IPerm>(perms);
        RandomPicker<IRole> rolePicker = new RandomPicker<IRole>(roles);

        while(!res) {
            pa.setPerm(permPicker.getRandomElement());
            pa.setRole(rolePicker.getRandomElement());
            res = pa.add();
        }

        pas.add(pa);
        return res;
    }

    @Override
    public boolean revoke_user() {
        IUR ur = new RandomPicker<IUR>(urs).getRandomElement();
        if(ur==null) return false;
        return urs.remove(ur) && ur.remove();
    }

    @Override
    public boolean revoke_perm() {
        IPA pa = new RandomPicker<IPA>(pas).getRandomElement();
        return (pa!=null) && pas.remove(pa) && pa.remove();
    }

    public void print_sizes() {
        System.out.println("size of users: " + users.size());
        System.out.println("size of roles: " + roles.size());
        System.out.println("size of perms: " + perms.size());
        System.out.println("size of urs: " + urs.size());
        System.out.println("size of pas: " + pas.size());
    }



    @Override
    public boolean initialize(Configuration conf) {
        return new WorkloadInitializer().initialize(this, conf);
    }

    @Override
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

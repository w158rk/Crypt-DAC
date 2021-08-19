package org.lab510.cryptodac.workload;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

class RandomPicker<E> {
    private Set<E> set;

    public RandomPicker(Set<E> set) {
        this.set = set;
    }

    private int getRandomInteger(int bound) {
        return new Random().nextInt(bound);
    }

    public E getRandomElement() {
        if(set==null || set.isEmpty()) {
            return null;
        }

        int i = getRandomInteger(set.size());
        for(E e : set) {
            if (i-- == 0)
                return e;
        }
        return null;
    }
}

public class Workload implements IWorkload{

    private Set<IUser> users = null;
    private Set<IPerm> perms = null;
    private Set<IRole> roles = null;
    private Set<IPA> pas = null;
    private Set<IUR> urs = null;

    Workload() {
        users = new HashSet<IUser>();
        perms = new HashSet<IPerm>();
        roles = new HashSet<IRole>();
        pas = new HashSet<IPA>();
        urs = new HashSet<IUR>();
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

        urs.add(ur);
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

}

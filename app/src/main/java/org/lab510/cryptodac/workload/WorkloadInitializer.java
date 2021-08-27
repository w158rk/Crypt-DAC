package org.lab510.cryptodac.workload;

import java.util.Set;

import org.lab510.cryptodac.config.Configuration;
import org.lab510.cryptodac.utils.RandomPicker;

public class WorkloadInitializer {
    private Workload workload = null;

    public boolean initialize(Workload workload, Configuration conf) {
        this.workload = workload;

        return initSets(conf) && initRelations(conf);
    }

    /**
     * Note: numUser, numPerm and numRole should all larger than 2
     * @param conf
     * @return
     */
    private boolean initSets(Configuration conf) {

        if(workload==null) return false;
        if(conf==null) return false;

        boolean ret = true;
        int numUser = conf.getIntegerValue("numUser");
        int numPerm = conf.getIntegerValue("numPerm");
        int numRole = conf.getIntegerValue("numRole");


        for(int i=0; i<numUser; i++) ret = ret && workload.addUser(new User());
        for(int i=0; i<numPerm; i++) ret = ret && workload.addPerm(new Perm());
        for(int i=0; i<numRole; i++) ret = ret && workload.addRole(new Role());

        Set<User> users = workload.getUsers();
        RandomPicker<User> uPicker = new RandomPicker<>(users);
        User userMax = uPicker.getRandomElement();
        User userMin = uPicker.getRandomElement();
        while(userMin.equals(userMax)) {
            userMin = uPicker.getRandomElement();
        }
        workload.setUserMax(userMax);
        workload.setUserMin(userMin);

        Set<Role> roles = workload.getRoles();
        RandomPicker<Role> rPicker = new RandomPicker<>(roles);
        Role roleMaxU = rPicker.getRandomElement();
        Role roleMinU = rPicker.getRandomElement();
        while(roleMinU.equals(roleMaxU)) {
            roleMinU = rPicker.getRandomElement();
        }
        Role rolemaxP = rPicker.getRandomElement();
        Role roleMinP = rPicker.getRandomElement();
        while(roleMinP.equals(rolemaxP)) {
            roleMinP = rPicker.getRandomElement();
        }

        workload.setRoleMaxU(roleMaxU);
        workload.setRoleMinU(roleMinU);
        workload.setRoleMinP(roleMinP);
        workload.setRoleMinP(roleMinP);

        // repeat the above procedure with PA
        Set<Perm> perms = workload.getPerms();
        RandomPicker<Perm> picker = new RandomPicker<>(perms);
        Perm permMax = picker.getRandomElement();
        Perm permMin = picker.getRandomElement();
        while(permMin.equals(permMax)) {
            permMin = picker.getRandomElement();
        }

        workload.setPermMin(permMin);
        workload.setPermMax(permMax);

        // remove all the special elements
        // users.remove(userMax);
        // users.remove(userMin);
        // roles.remove(roleMinP);
        // roles.remove(rolemaxP);
        // roles.remove(roleMinU);
        // roles.remove(roleMaxU);
        // perms.remove(permMin);
        // perms.remove(permMax);

        return ret;
    }

    private boolean initRelations(Configuration conf) {

        int numUR = conf.getIntegerValue("numUR");
        int count = 0;
        int minRolesPerUser = conf.getIntegerValue("minRolesPerUser");
        int maxRolesPerUser = conf.getIntegerValue("maxRolesPerUser");
        int minUsersPerRole = conf.getIntegerValue("minUsersPerRole");
        int maxUsersPerRole = conf.getIntegerValue("maxUsersPerRole");
        Set<User> users = workload.getUsers();
        Set<Role> roles = workload.getRoles();
        Set<UR> urs = workload.getUrs();
        RandomPicker<User> uPicker = new RandomPicker<>(users);
        RandomPicker<Role> rPicker = new RandomPicker<>(roles);

        // for every user, add `minRolesPerUser` role
        for(User user : users) {
            count = 0;
            while(count<minRolesPerUser) {
                Role role = rPicker.getRandomElement();
                if(role.equals(workload.getRoleMinU()))
                    continue;
                if(role.numUsers()>=maxUsersPerRole)
                    continue;
                if(workload.assign_user(user, role))
                    count ++;
            }
        }

        // let the max user achieve the max
        count = workload.getUserMax().numRoles();
        while(count<maxRolesPerUser) {
            Role role = rPicker.getRandomElement();
            if(role.equals(workload.getRoleMinU()))
                continue;
            if(role.numUsers()>=maxUsersPerRole)
                continue;
            if(workload.assign_user(workload.getUserMax(), role))
                count ++;
        }

        // for every role, add `minUsersPerRole` users
        for(Role role : roles) {
            count = role.numUsers();
            while(count<minUsersPerRole) {
                User user = uPicker.getRandomElement();
                if(user.equals(workload.getUserMin()))
                    continue;
                if(user.numRoles()>=maxRolesPerUser)
                    continue;
                if(workload.assign_user(user, role))
                    count ++;
            }
        }

        // let the max role achieve the max
        count = workload.getRoleMaxU().numUsers();
        while(count<maxUsersPerRole) {
            User user = uPicker.getRandomElement();
            if(user.equals(workload.getUserMin()))
                continue;
            if(user.numRoles()>=maxRolesPerUser)
                continue;
            if(workload.assign_user(user, workload.getRoleMaxU()))
                count ++;
        }

        // add the rest to achieve numUR
        count = urs.size();
        while(count<numUR) {
            User user = uPicker.getRandomElement();
            Role role = rPicker.getRandomElement();

            if(user.equals(workload.getUserMin()) || role.equals(workload.getRoleMinU()))
                continue;

            if(user.numRoles()>=maxRolesPerUser || role.numUsers()>=maxUsersPerRole)
                continue;

            UR ur = new UR(user, role);
            if(urs.contains(ur))
                continue;

            if(workload.assign_user(user, role))
                count ++;
        }

        int numPA = conf.getIntegerValue("numPA");

        return true;
    }
}

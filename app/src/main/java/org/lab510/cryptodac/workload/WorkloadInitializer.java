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

        Set<IUser> users = workload.getUsers();
        RandomPicker<IUser> uPicker = new RandomPicker<>(users);
        IUser userMax = uPicker.getRandomElement();
        IUser userMin = uPicker.getRandomElement();
        while(userMin.equals(userMax)) {
            userMin = uPicker.getRandomElement();
        }
        workload.setUserMax(userMax);
        workload.setUserMin(userMin);

        Set<IRole> roles = workload.getRoles();
        RandomPicker<IRole> rPicker = new RandomPicker<>(roles);
        IRole roleMaxU = rPicker.getRandomElement();
        IRole roleMinU = rPicker.getRandomElement();
        while(roleMinU.equals(roleMaxU)) {
            roleMinU = rPicker.getRandomElement();
        }
        IRole rolemaxP = rPicker.getRandomElement();
        IRole roleMinP = rPicker.getRandomElement();
        while(roleMinP.equals(rolemaxP)) {
            roleMinP = rPicker.getRandomElement();
        }

        workload.setRoleMaxU(roleMaxU);
        workload.setRoleMinU(roleMinU);
        workload.setRoleMinP(roleMinP);
        workload.setRoleMinP(roleMinP);

        Set<IPerm> perms = workload.getPerms();
        RandomPicker<IPerm> picker = new RandomPicker<>(perms);
        IPerm permMax = picker.getRandomElement();
        IPerm permMin = picker.getRandomElement();
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
        Set<IUser> users = workload.getUsers();
        Set<IRole> roles = workload.getRoles();
        Set<IUR> urs = workload.getUrs();
        RandomPicker<IUser> uPicker = new RandomPicker<>(users);
        RandomPicker<IRole> rPicker = new RandomPicker<>(roles);

        // for every user, add `minRolesPerUser` role
        for(IUser user : users) {
            count = 0;
            while(count<minRolesPerUser) {
                IRole role = rPicker.getRandomElement();
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
            IRole role = rPicker.getRandomElement();
            if(role.equals(workload.getRoleMinU()))
                continue;
            if(role.numUsers()>=maxUsersPerRole)
                continue;
            if(workload.assign_user(workload.getUserMax(), role))
                count ++;
        }

        // for every role, add `minUsersPerRole` users
        for(IRole role : roles) {
            count = role.numUsers();
            while(count<minUsersPerRole) {
                IUser user = uPicker.getRandomElement();
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
            IUser user = uPicker.getRandomElement();
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
            IUser user = uPicker.getRandomElement();
            IRole role = rPicker.getRandomElement();

            if(user.equals(workload.getUserMin()) || role.equals(workload.getRoleMinU()))
                continue;

            if(user.numRoles()>=maxRolesPerUser || role.numUsers()>=maxUsersPerRole)
                continue;

            IUR ur = new UR(user, role);
            if(urs.contains(ur))
                continue;

            if(workload.assign_user(user, role))
                count ++;
        }

        int numPA = conf.getIntegerValue("numPA");

        return true;
    }
}

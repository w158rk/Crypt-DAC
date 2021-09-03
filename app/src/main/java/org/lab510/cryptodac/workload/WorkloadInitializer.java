package org.lab510.cryptodac.workload;

import java.util.Set;

import org.lab510.cryptodac.config.Configuration;
import org.lab510.cryptodac.error.Error;
import org.lab510.cryptodac.utils.Pair;
import org.lab510.cryptodac.utils.RandomPicker;

public class WorkloadInitializer {
    private Workload workload = null;
    private Configuration configuration;

    WorkloadInitializer(Workload workload, Configuration configuration) {
        this.workload = workload;
        this.configuration = configuration;
    }

    public void initialize() {
        initSets();
        initRelations();
    }

    private void initSets() {
        initUsers();
        initRoles();
        initPerms();
    }

    private void initUsers() {
        throwErrorIfNullMember();

        int numUser = configuration.getIntegerValue("numUser");
        for(int i=0; i<numUser; i++) workload.addUser(new User());

        RandomPicker<User> uPicker = new RandomPicker<>(workload.getUsers());
        Pair<User, User> minMaxUsers = uPicker.getTwoDistinctRandomElements();
        workload.setUserMin(minMaxUsers.getFirst());
        workload.setUserMax(minMaxUsers.getSecond());

    }

    private void initPerms() {
        throwErrorIfNullMember();

        int numPerm = configuration.getIntegerValue("numPerm");
        for(int i=0; i<numPerm; i++) workload.addPerm(new Perm());

        RandomPicker<Perm> pPicker = new RandomPicker<>(workload.getPerms());
        Pair<Perm, Perm> minMaxPerms = pPicker.getTwoDistinctRandomElements();
        workload.setPermMin(minMaxPerms.getFirst());
        workload.setPermMax(minMaxPerms.getSecond());
    }

    private void initRoles() {
        throwErrorIfNullMember();

        int numRole = configuration.getIntegerValue("numRole");
        for(int i=0; i<numRole; i++) workload.addRole(new Role());

        RandomPicker<Role> rPicker = new RandomPicker<>(workload.getRoles());
        Pair<Role, Role> minMaxRolesU = rPicker.getTwoDistinctRandomElements();
        workload.setRoleMinU(minMaxRolesU.getFirst());
        workload.setRoleMaxU(minMaxRolesU.getSecond());

        Pair<Role, Role> minMaxRolesP = rPicker.getTwoDistinctRandomElements();
        workload.setRoleMinP(minMaxRolesP.getFirst());
        workload.setRoleMaxP(minMaxRolesP.getSecond());

    }

    private void throwErrorIfNullMember() {
        if(configuration==null) {
            throw new Error("Configuration null");
        }

        if(workload==null) {
            throw new Error("Workload null");
        }
    }

    private void initRelations() {
        initRelationsUR();
        initRelationsPA();
    }

    private void initRelationsUR() {

        assignMinURsToAllUsers();
        assignMaxURsToUserMax();
        assignMinURsToAllRoles();

        // NOTE: Originally, we re-select a roleMaxU here,
        // Now I think it is unnecessary and remove the related
        // code. If needed later, here we scan the role set and
        // find the one that with the maximum number of users

        assignMaxURsToRoleMaxU();
        addRandomURsToAchiveNumUR();

    }

    private void assignMinURsToAllUsers() {
        throwErrorIfNullMember();

        for(User user : workload.getUsers()) {
            assignMinURsToUser(user);
        }
    }

    private void assignMinURsToUser(User user) {
        int minRolesPerUser = configuration.getIntegerValue("minRolesPerUser");
        int count = 0;
        while(count<minRolesPerUser) {
            Role role = findRoleWithPriority();
            if(workload.assign_user(user, role)) count ++;
        }
    }

    private Role findRoleWithPriority() {
        int maxUsersPerRole = configuration.getIntegerValue("maxUsersPerRole");

        Role role = findFirstRoleWithUsersLessThanMin();
        if(role!=null) return role;

        if(workload.getRoleMaxU().numUsers()<maxUsersPerRole) return workload.getRoleMaxU();

        return findRandomRoleAvailableForMoreUser();
    }


    private Role findFirstRoleWithUsersLessThanMin() {
        int minUsersPerRole = configuration.getIntegerValue("minUsersPerRole");
        for(Role r : workload.getRoles()) {
            if(r.numUsers()<minUsersPerRole) return r;
        }
        return null;
    }

    private Role findRandomRoleAvailableForMoreUser() {
        int maxUsersPerRole = configuration.getIntegerValue("maxUsersPerRole");
        final int MAX_TRIES = 10;
        int attempts = 0;
        Role role = null;

        while(role==null && attempts<MAX_TRIES) {
            attempts ++;
            RandomPicker<Role> picker = new RandomPicker<>(workload.getRoles());
            role = picker.getRandomElement();

            if(role.equals(workload.getRoleMinU()) || role.numUsers()>=maxUsersPerRole) role = null;
        }

        if(role==null) throw new Error("Cannot find any role available for more users");

        return role;
    }

    private void assignMaxURsToUserMax() {
        int maxRolesPerUser = configuration.getIntegerValue("maxRolesPerUser");
        int count = workload.getUserMax().numRoles();
        while(count < maxRolesPerUser) {
            Role role = findRandomRoleAvailableForMoreUser();
            if(workload.assign_user(workload.getUserMax(), role)) count ++;
        }
    }

    private void assignMinURsToAllRoles() {
        for(Role role : workload.getRoles()) {
            assignMinURsToRole(role);
        }
    }

    private void assignMinURsToRole(Role role) {
        int minUsersPerRole = configuration.getIntegerValue("minUsersPerRole");
        int count = role.numUsers();
        while(count < minUsersPerRole) {
            User user = findRandomUserAvailableForMoreRole();
            if(workload.assign_user(user, role)) count ++;
        }
    }

    private User findRandomUserAvailableForMoreRole() {
        int maxRolesPerUser = configuration.getIntegerValue("maxRolesPerUser");
        final int MAX_TRIES = 10;
        int attempts = 0;
        User user = null;

        while(user==null && attempts<MAX_TRIES) {
            attempts ++;
            RandomPicker<User> picker = new RandomPicker<>(workload.getUsers());
            user = picker.getRandomElement();

            if(user.equals(workload.getUserMin()) || user.numRoles()>=maxRolesPerUser) user = null;
        }

        if(user==null) throw new Error("Cannot find any user available for more roles.");

        return user;
    }

    private void assignMaxURsToRoleMaxU() {
        int maxUsersPerRole = configuration.getIntegerValue("maxUsersPerRole");
        int count = workload.getRoleMaxU().numUsers();
        while(count<maxUsersPerRole) {
            User user = findRandomUserAvailableForMoreRole();
            if(workload.assign_user(user, workload.getRoleMaxU())) count ++;
        }
    }

    private void addRandomURsToAchiveNumUR() {
        Set<UR> urs = workload.getUrs();
        int count = urs.size();
        int numUR = configuration.getIntegerValue("numUR");

        while(count<numUR) {
            User user = findRandomUserAvailableForMoreRole();
            Role role = findRandomRoleAvailableForMoreUser();

            if(workload.assign_user(user, role))
                count ++;
        }
    }

    private void initRelationsPA() {

        int numPA = configuration.getIntegerValue("numPA");
        int count = 0;
        int minRolesPerPerm = configuration.getIntegerValue("minRolesPerPerm");
        int maxRolesPerPerm = configuration.getIntegerValue("maxRolesPerPerm");
        int minPermsPerRole = configuration.getIntegerValue("minPermsPerRole");
        int maxPermsPerRole = configuration.getIntegerValue("maxPermsPerRole");

        Set<Perm> perms = workload.getPerms();
        Set<Role> roles = workload.getRoles();
        Set<PA> pas = workload.getPas();
        RandomPicker<Perm> pPicker = new RandomPicker<>(perms);
        RandomPicker<Role> rPicker = new RandomPicker<>(roles);

        // for every perm, add `minRolesPerPerm` roles
        for(Perm perm : perms) {
            count = 0;
            while(count<minRolesPerPerm) {
                Role role = rPicker.getRandomElement();
                if(role.equals(workload.getRoleMinP()))
                    continue;
                if(role.numPerms()>=maxPermsPerRole)
                    continue;
                if(workload.assign_perm(perm, role))
                    count ++;
            }
        }

        // let the max perm achieve the max
        count = workload.getPermMax().numRoles();
        while(count<maxRolesPerPerm) {
            Role role = rPicker.getRandomElement();
            if(role.equals(workload.getRoleMinP()))
                continue;
            if(role.numPerms()>=maxPermsPerRole)
                continue;
            if(workload.assign_perm(workload.getPermMax(), role))
                count ++;
        }

        // for every role, add `minPermsPerRole` perms
        for(Role role : roles) {
            count = role.numPerms();
            while(count<minPermsPerRole) {
                Perm perm = pPicker.getRandomElement();
                if(perm.equals(workload.getPermMin()))
                    continue;
                if(perm.numRoles()>=maxRolesPerPerm)
                    continue;
                if(workload.assign_perm(perm, role))
                    count ++;
            }
        }

        // let the max role achieve the max
        count = workload.getRoleMaxP().numPerms();
        while(count<maxPermsPerRole) {
            Perm perm = pPicker.getRandomElement();
            if(perm.equals(workload.getPermMin()))
                continue;
            if(perm.numRoles()>=maxRolesPerPerm)
                continue;
            if(workload.assign_perm(perm, workload.getRoleMaxP()))
                count ++;
        }

        // add the rest to achieve numPA
        count = pas.size();
        while(count<numPA) {
            Perm perm = pPicker.getRandomElement();
            Role role = rPicker.getRandomElement();

            if(perm.equals(workload.getPermMin()) || role.equals(workload.getRoleMinP()))
                continue;

            if(perm.numRoles()>=maxRolesPerPerm || role.numPerms()>=maxPermsPerRole)
                continue;

            PA pa = new PA(perm, role);
            if(pas.contains(pa))
                continue;

            if(workload.assign_perm(perm, role))
                count ++;
        }

    }

}

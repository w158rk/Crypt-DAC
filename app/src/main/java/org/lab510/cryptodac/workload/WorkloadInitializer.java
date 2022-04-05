package org.lab510.cryptodac.workload;

import java.util.Set;

import org.lab510.cryptodac.config.Configuration;
import org.lab510.cryptodac.error.Error;
import org.lab510.cryptodac.utils.Pair;
import org.lab510.cryptodac.utils.RandomPicker;

public class WorkloadInitializer {
    private Workload workload = null;
    private Configuration configuration;

    public WorkloadInitializer(Workload workload, Configuration configuration) {
        this.workload = workload;
        this.configuration = configuration;
    }

    public void initialize() {
        if(workload.isInitialized()) return;
        workload.setInitialized();
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
            Role role = findRoleForUserWithPriority();
            if(workload.assignUr(user, role)) count ++;
        }
    }

    private Role findRoleForUserWithPriority() {
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
            if(workload.assignUr(workload.getUserMax(), role)) count ++;
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
            if(workload.assignUr(user, role)) count ++;
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
            if(workload.assignUr(user, workload.getRoleMaxU())) count ++;
        }
    }

    private void addRandomURsToAchiveNumUR() {
        Set<UR> urs = workload.getUrs();
        int count = urs.size();
        int numUR = configuration.getIntegerValue("numUR");

        while(count<numUR) {
            User user = findRandomUserAvailableForMoreRole();
            Role role = findRandomRoleAvailableForMoreUser();

            if(workload.assignUr(user, role))
                count ++;
        }
    }

    private void initRelationsPA() {

        assignMinPAsToAllPerms();
        assignMaxPAsToPermMax();
        assignMinPAsToAllRoles();
        assignMaxPAsToRoleMaxP();
        addRandomPAsToAchiveNumPA();

    }

    private void assignMinPAsToAllPerms() {
        for(Perm perm : workload.getPerms()) {
            assignMinPAsToPerm(perm);
        }
    }

    private void assignMinPAsToPerm(Perm perm) {
        int minRolesPerPerm = configuration.getIntegerValue("minRolesPerPerm");
        int count = 0;
        while(count<minRolesPerPerm) {
            Role role = findRoleForPermWithPriority();
            if(workload.assignPerm(perm, role)) count ++;
        }
    }

    private Role findRoleForPermWithPriority() {
        int maxPermsPerRole = configuration.getIntegerValue("maxPermsPerRole");

        Role role = findFirstRoleWithPermsLessThanMin();
        if(role!=null) return role;

        if(workload.getRoleMaxP().numPerms()<maxPermsPerRole) return workload.getRoleMaxP();

        return findRandomRoleAvailableForMorePerm();

    }

    private Role findFirstRoleWithPermsLessThanMin() {
        int minPermsPerRole = configuration.getIntegerValue("minPermsPerRole");
        for(Role r : workload.getRoles()) {
            if(r.numPerms()<minPermsPerRole) return r;
        }
        return null;
    }

    private Role findRandomRoleAvailableForMorePerm() {
        int maxPermsPerRole = configuration.getIntegerValue("maxPermsPerRole");
        final int MAX_TRIES = 10;
        int attempts = 0;
        Role role = null;

        while(role==null && attempts<MAX_TRIES) {
            attempts ++;
            RandomPicker<Role> picker = new RandomPicker<>(workload.getRoles());
            role = picker.getRandomElement();

            if(role.equals(workload.getRoleMinP()) || role.numPerms()>=maxPermsPerRole) role = null;
        }

        if(role==null) throw new Error("Cannot find any role available for more permissions");

        return role;

    }

    private void assignMaxPAsToPermMax() {
        int maxRolesPerPerm = configuration.getIntegerValue("maxRolesPerPerm");
        int count = workload.getPermMax().numRoles();
        while(count < maxRolesPerPerm) {
            Role role = findRandomRoleAvailableForMorePerm();
            if(workload.assignPerm(workload.getPermMax(), role)) count ++;
        }
    }

    private void assignMinPAsToAllRoles() {
        for(Role role : workload.getRoles()) {
            assignMinPAsToRole(role);
        }
    }

    private void assignMinPAsToRole(Role role) {
        int minPermsPerRole = configuration.getIntegerValue("minPermsPerRole");
        int count = role.numPerms();
        while(count < minPermsPerRole) {
            Perm perm = findRandomPermAvailableForMoreRole();
            if(workload.assignPerm(perm, role)) count ++;
        }
    }

    private Perm findRandomPermAvailableForMoreRole() {
        int maxRolesPerPerm = configuration.getIntegerValue("maxRolesPerPerm");
        final int MAX_TRIES = 10;
        int attempts = 0;
        Perm perm = null;

        while(perm==null && attempts<MAX_TRIES) {
            attempts ++;
            RandomPicker<Perm> picker = new RandomPicker<>(workload.getPerms());
            perm = picker.getRandomElement();

            if(perm.equals(workload.getPermMin()) || perm.numRoles()>=maxRolesPerPerm) perm = null;
        }

        if(perm==null) throw new Error("Cannot find any perm available for more roles.");

        return perm;
    }

    private void assignMaxPAsToRoleMaxP() {
        int maxPermsPerRole = configuration.getIntegerValue("maxPermsPerRole");
        int count = workload.getRoleMaxP().numPerms();
        while(count<maxPermsPerRole) {
            Perm perm = findRandomPermAvailableForMoreRole();
            if(workload.assignPerm(perm, workload.getRoleMaxP())) count ++;
        }
    }

    private void addRandomPAsToAchiveNumPA() {
        Set<PA> urs = workload.getPas();
        int count = urs.size();
        int numPA = configuration.getIntegerValue("numPA");

        while(count<numPA) {
            Perm perm = findRandomPermAvailableForMoreRole();
            Role role = findRandomRoleAvailableForMorePerm();

            if(workload.assignPerm(perm, role))
                count ++;
        }
    }


}

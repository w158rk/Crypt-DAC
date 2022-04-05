package org.lab510.cryptodac.dac;

import java.util.ArrayList;
import java.util.List;
import org.lab510.cryptodac.config.Configuration;
import org.lab510.cryptodac.dac.event.Event;
import org.lab510.cryptodac.time.DayTimer;
import org.lab510.cryptodac.workload.PA;
import org.lab510.cryptodac.workload.Role;
import org.lab510.cryptodac.workload.UR;
import org.lab510.cryptodac.workload.Workload;
import org.lab510.cryptodac.workload.WorkloadInitializer;

/**
 * dynamic access control
 */
public abstract class DAC {
    public void addUser() {
        workload.addUser();
    }

    public abstract void removeUser();

    public void addPerm() {
        workload.addPerm();
    }

    public void removePerm() {
        workload.removePerm();
    }

    public void addRole() {
        workload.addRole();
    }

    public Role removeRole() {
        Role role = workload.getRandomRole();
        for(UR ur : workload.getUrs(role)) {            // 不需要额外的密码操作，只需要删除绑定
            workload.revokeUr(ur);
        }
        for(PA pa : workload.getPas(role)) {
            revokePa(pa);                         // 这个就需要密码操作了，指导想法：向右的是需要的
        }
        return workload.removeRole(role);
    }

    public void assignRole() {
        workload.assignUr();
    }

    public UR revokeUr() {
        return revokeUr(workload.getRandomUr());
    }

    public UR revokeUr(UR ur) {
        return workload.revokeUr(ur);
    }

    public void assignPerm() {
        workload.assignPa();
    }

    public PA revokePa() {
        return revokePa(workload.getRandomPa());
    }

    public PA revokePa(PA pa) {
        return workload.revokePa(pa);
    }

    public abstract void read();

    public abstract void write();

    public abstract void run();

    public abstract void report();

    Workload workload;
    List<Transaction<Player, Event>> txs;
    Configuration configuration;
    DayTimer dayTimer;
    int rateAssignUser = 0;

    DAC(Configuration configuration) {
        this.configuration = configuration;
        workload = new Workload();
        txs = new ArrayList<>();
        dayTimer = new DayTimer();
    }

    void addEvent(Player player, Event event) {
        txs.add(new Transaction<Player, Event>(player, event));
    }

    public Workload getWorkload() {
        return workload;
    }

    void initializeWorkload() {
        WorkloadInitializer initializer = new WorkloadInitializer(workload, configuration);
        initializer.initialize();
    }

}

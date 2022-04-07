package org.lab510.cryptodac.dac;

import java.util.ArrayList;
import java.util.List;
import org.lab510.cryptodac.config.Configuration;
import org.lab510.cryptodac.dac.event.Event;
import org.lab510.cryptodac.utils.Printer;
import org.lab510.cryptodac.workload.PA;
import org.lab510.cryptodac.workload.Perm;
import org.lab510.cryptodac.workload.Role;
import org.lab510.cryptodac.workload.UR;
import org.lab510.cryptodac.workload.User;
import org.lab510.cryptodac.workload.Workload;
import org.lab510.cryptodac.workload.WorkloadInitializer;

/**
 * dynamic access control
 */
public abstract class DAC {

    Workload workload;
    List<Transaction<Player, Event>> txs;
    Configuration configuration;

    public void addUser() {
        workload.addUser();
    }

    public User removeUser() {
        var user = workload.getRandomUser();
        for (UR ur : workload.getUrs(user)) {
            revokeUrInner(ur);
        }
        return workload.removeUser(user);
    }

    public void addPerm() {
        workload.addPerm();
    }

    public Perm removePerm() {
        return workload.removePerm();
    }

    public void addRole() {
        workload.addRole();
    }

    public Role removeRole() {
        Role role = workload.getRandomRole();
        for (PA pa : workload.getPas(role)) {
            revokePaInner(pa); // 指导想法：向右的是需要密码操作的
        }
        return workload.removeRole(role);
    }

    public final void assignUr() {
        beforeAssignUr();
        assignUrInner();
        afterAssignUr();
    }

    void assignUrInner() {
        workload.assignUr();
    }

    void beforeAssignUr() {
        Printer.print("[au] begin");
    }

    void afterAssignUr() {
        Printer.print("[au] end");
    }


    // never been called inside the class
    public final UR revokeUr() {
        beforeRevokeUr();
        var ret = revokeUrInner(workload.getRandomUr());
        afterRevokeUr();
        return ret;
    }

    void beforeRevokeUr() {
        Printer.print("[ru] begin");
    }
    void afterRevokeUr() {
        Printer.print("[ru] end");
    }

    UR revokeUrInner(UR ur) {
        return workload.revokeUr(ur);
    }

    public final void assignPa() {
        beforeAssignPa();
        assignPaInner();
        afterAssignPa();
    }

    void assignPaInner() {
        workload.assignPa();
    }

    void beforeAssignPa() {
        Printer.print("[ap] begin");
    }

    void afterAssignPa() {
        Printer.print("[ap] end");
    }

    public final PA revokePa() {
        beforeRevokePa();
        var ret = revokePaInner(workload.getRandomPa());
        afterRevokePa();
        return ret;
    }

    void beforeRevokePa() {
        Printer.print("[rp] begin");
    }

    void afterRevokePa() {
        Printer.print("[rp] end");
    }

    public PA revokePaInner(PA pa) {
        return workload.revokePa(pa);
    }

    public abstract void read();

    public abstract void write();

    public abstract void report();

    public DAC(Configuration configuration) {
        this.configuration = configuration;
        workload = new Workload();
        txs = new ArrayList<>();

        initializeWorkload();
    }

    void addEvent(Player player, Event event) {
        Printer.print(String.format("[tx] %s - %s", player.name(), event.toString()));
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

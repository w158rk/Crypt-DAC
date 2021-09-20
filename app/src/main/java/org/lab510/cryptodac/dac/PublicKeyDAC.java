package org.lab510.cryptodac.dac;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.lab510.cryptodac.config.Configuration;
import org.lab510.cryptodac.time.DayTimer;
import org.lab510.cryptodac.workload.PA;
import org.lab510.cryptodac.workload.Role;
import org.lab510.cryptodac.workload.UR;
import org.lab510.cryptodac.workload.User;
import org.lab510.cryptodac.workload.Workload;
import org.lab510.cryptodac.workload.WorkloadInitializer;

class PublicKeyUserRevoker {
    private Workload workload;
    private List<Transaction<PublicKeyPlayer, PublicKeyEvent>> txs;
    PublicKeyUserRevoker(Workload workload) {
        this.workload = workload;
        txs = new ArrayList<>();
    }

    void revoke() {
        UR ur = workload.revoke_user();

        txs.add(new Transaction<PublicKeyPlayer,PublicKeyEvent>(PublicKeyPlayer.ADMIN, PublicKeyEvent.GEN_KEY));

        for(int i=0; i<ur.getRole().numUsers(); i++) {
            txs.add(new Transaction<PublicKeyPlayer,PublicKeyEvent>(PublicKeyPlayer.ADMIN, PublicKeyEvent.ENCRYPT));
            txs.add(new Transaction<PublicKeyPlayer,PublicKeyEvent>(PublicKeyPlayer.ADMIN, PublicKeyEvent.SIGN));
        }

        for(PA pa : ur.getRole().getPas()) {
            txs.add(new Transaction<PublicKeyPlayer,PublicKeyEvent>(PublicKeyPlayer.ADMIN, PublicKeyEvent.SYM_GEN_KEY));
            txs.add(new Transaction<PublicKeyPlayer,PublicKeyEvent>(PublicKeyPlayer.ADMIN, PublicKeyEvent.SYM_DECRYPT));
            txs.add(new Transaction<PublicKeyPlayer,PublicKeyEvent>(PublicKeyPlayer.ADMIN, PublicKeyEvent.SYM_ENCRYPT));

            for(int i=0; i<pa.getPerm().numRoles(); i++) {
                txs.add(new Transaction<PublicKeyPlayer,PublicKeyEvent>(PublicKeyPlayer.ADMIN, PublicKeyEvent.ENCRYPT));
                txs.add(new Transaction<PublicKeyPlayer,PublicKeyEvent>(PublicKeyPlayer.ADMIN, PublicKeyEvent.SIGN));
            }

        }

    }
}

public class PublicKeyDAC implements DAC{


    private Workload workload;
    private List<Transaction<PublicKeyPlayer, PublicKeyEvent>> txs;
    private Configuration configuration;
    private DayTimer dayTimer;
    private int rateAssignUser = 0;

    PublicKeyDAC(Configuration configuration) {
        this.configuration = configuration;
        workload = new Workload();
        txs = new ArrayList<>();
        dayTimer = new DayTimer();
    }

    public Workload getWorkload() {
        return workload;
    }

    int getRateAssignUser() {
        return rateAssignUser;
    }

    void initializeWorkload() {
        WorkloadInitializer initializer = new WorkloadInitializer(workload, configuration);
        initializer.initialize();
    }

    List<Transaction<PublicKeyPlayer, PublicKeyEvent>> getTxs() {
        return txs;
    }

    @Override
    public void addUser() {
        workload.addUser();
        txs.add(new Transaction<>(PublicKeyPlayer.ADMIN, PublicKeyEvent.GEN_KEY));
    }

    @Override
    public void removeUser() {
        // TODO Auto-generated method stub

    }

    @Override
    public void addPerm() {
        // TODO Auto-generated method stub

    }

    @Override
    public void removePerm() {
        // TODO Auto-generated method stub

    }

    @Override
    public void addRole() {
        // TODO Auto-generated method stub

    }

    @Override
    public void removeRole() {
        // TODO Auto-generated method stub

    }

    @Override
    public void assignUser() {
        workload.assign_user();
        txs.add(new Transaction<>(PublicKeyPlayer.ADMIN, PublicKeyEvent.VERIFY));
        txs.add(new Transaction<>(PublicKeyPlayer.ADMIN, PublicKeyEvent.DECRYPT));
        txs.add(new Transaction<>(PublicKeyPlayer.ADMIN, PublicKeyEvent.ENCRYPT));
        txs.add(new Transaction<>(PublicKeyPlayer.ADMIN, PublicKeyEvent.SIGN));
    }

    @Override
    public void revokeUser() {
        UR ur = workload.revoke_user();

    }

    @Override
    public void assignPerm() {
        // TODO Auto-generated method stub

    }

    @Override
    public void revokePerm() {
        // TODO Auto-generated method stub

    }

    @Override
    public void read() {
        // TODO Auto-generated method stub

    }

    @Override
    public void write() {
        // TODO Auto-generated method stub

    }

    @Override
    public void run() {
        int dayLimited = 30;

        initializeWorkload();
        calculateRates();

        while(dayTimer.getToday()<dayLimited) {
            for(int i=0; i<rateAssignUser; i++) {
                assignUser();
            }
            dayTimer.increment();
        }
    }

    private void calculateRates() {
        double r = 0.1 * Math.sqrt(workload.getUsers().size());
        double muA = configuration.getDoubleValue("muA");
        double muU = configuration.getDoubleValue("muU");
        rateAssignUser = (int)(muA*muU*r);
    }

    @Override
    public void report() {
        // TODO Auto-generated method stub

    }

}

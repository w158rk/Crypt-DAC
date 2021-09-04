package org.lab510.cryptodac.dac;

import java.util.ArrayList;
import java.util.List;

import org.lab510.cryptodac.config.Configuration;
import org.lab510.cryptodac.time.DayTimer;
import org.lab510.cryptodac.workload.Workload;
import org.lab510.cryptodac.workload.WorkloadInitializer;

enum Player {
    PKG,
    ADMIN;
}

enum Event {
    GEN_KEY,
    ENCRYPT,
    DECRYPT,
    SIGN,
    VERIFY,
    SYM_ENCRYPT,
    SYM_DECRYPT;
}

public class IdentityBasedDAC implements DAC{

    class Transaction {
        Player who;
        Event what;

        Transaction(Player who, Event what) {
            this.who = who;
            this.what = what;
        }

        @Override
        public String toString() {
            return String.format("%s : %s", who, what);
        }
    }

    private Workload workload;
    private List<Transaction> txs;
    private Configuration configuration;
    private DayTimer dayTimer;
    private int rateAssignUser = 0;

    IdentityBasedDAC(Configuration configuration) {
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

    List<Transaction> getTxs() {
        return txs;
    }

    @Override
    public void addUser() {
        workload.addUser();
        txs.add(new Transaction(Player.PKG, Event.GEN_KEY));
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
        while(!workload.assign_user());
        txs.add(new Transaction(Player.ADMIN, Event.VERIFY));
        txs.add(new Transaction(Player.ADMIN, Event.DECRYPT));
        txs.add(new Transaction(Player.ADMIN, Event.ENCRYPT));
        txs.add(new Transaction(Player.ADMIN, Event.SIGN));
    }

    @Override
    public void revokeUser() {
        // TODO Auto-generated method stub

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

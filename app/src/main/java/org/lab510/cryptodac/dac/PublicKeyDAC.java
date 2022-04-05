package org.lab510.cryptodac.dac;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.lab510.cryptodac.config.Configuration;
import org.lab510.cryptodac.dac.event.PublicKeyEventFactory;
import org.lab510.cryptodac.time.DayTimer;
import org.lab510.cryptodac.workload.PA;
import org.lab510.cryptodac.workload.Role;
import org.lab510.cryptodac.workload.UR;
import org.lab510.cryptodac.workload.User;
import org.lab510.cryptodac.workload.Workload;
import org.lab510.cryptodac.workload.WorkloadInitializer;

// class PublicKeyUserRevoker {
//     private Workload workload;

//     PublicKeyUserRevoker(Workload workload) {
//         this.workload = workload;
//         txs = new ArrayList<>();
//     }

//     void revoke() {
//         UR ur = workload.revoke_user();

//         txs.add(new Transaction<Player, PublicKeyEvent>(Player.ADMIN, PublicKeyEvent.GEN_KEY));

//         for (int i = 0; i < ur.getRole().numUsers(); i++) {
//             txs.add(new Transaction<Player, PublicKeyEvent>(Player.ADMIN, PublicKeyEvent.ENCRYPT));
//             txs.add(new Transaction<Player, PublicKeyEvent>(Player.ADMIN, PublicKeyEvent.SIGN));
//         }

//         for (PA pa : ur.getRole().getPas()) {
//             txs.add(new Transaction<Player, PublicKeyEvent>(Player.ADMIN, PublicKeyEvent.SYM_GEN_KEY));
//             txs.add(new Transaction<Player, PublicKeyEvent>(Player.ADMIN, PublicKeyEvent.SYM_DECRYPT));
//             txs.add(new Transaction<Player, PublicKeyEvent>(Player.ADMIN, PublicKeyEvent.SYM_ENCRYPT));

//             for (int i = 0; i < pa.getPerm().numRoles(); i++) {
//                 txs.add(new Transaction<Player, PublicKeyEvent>(Player.ADMIN, PublicKeyEvent.ENCRYPT));
//                 txs.add(new Transaction<Player, PublicKeyEvent>(Player.ADMIN, PublicKeyEvent.SIGN));
//             }

//         }

//     }
// }

public class PublicKeyDAC extends DAC {

    PublicKeyDAC(Configuration configuration) {
        super(configuration);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void addUser() {
        super.addUser();
        addEvent(Player.USER, PublicKeyEventFactory.getEvent(PublicKeyEventFactory.EventType.KEY_GEN));
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
    public Role removeRole() {
        // TODO Auto-generated method stub
        return super.removeRole();
    }

    @Override
    public void assignRole() {
        super.assignRole();
        addEvent(Player.ADMIN, PublicKeyEventFactory.getEvent(PublicKeyEventFactory.EventType.DEC));
        addEvent(Player.ADMIN, PublicKeyEventFactory.getEvent(PublicKeyEventFactory.EventType.ENC));
    }

    @Override
    public UR revokeUr() {
        UR ur = workload.revokeUr();
        return ur;
    }

    @Override
    public void assignPerm() {
        // TODO Auto-generated method stub

    }

    @Override
    public PA revokePa() {
        // TODO Auto-generated method stub
        return super.revokePa();
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

        while (dayTimer.getToday() < dayLimited) {
            for (int i = 0; i < rateAssignUser; i++) {
                assignRole();
            }
            dayTimer.increment();
        }
    }

    private void calculateRates() {
        double r = 0.1 * Math.sqrt(workload.getUsers().size());
        double muA = configuration.getDoubleValue("muA");
        double muU = configuration.getDoubleValue("muU");
        rateAssignUser = (int) (muA * muU * r);
    }

    @Override
    public void report() {
        // TODO Auto-generated method stub

    }

}

package org.lab510.cryptodac.dac;

import org.lab510.cryptodac.config.Configuration;
import org.lab510.cryptodac.dac.event.PublicKeyEventFactory;
import org.lab510.cryptodac.dac.event.PublicKeyEventFactory.EventType;
import org.lab510.cryptodac.utils.Printer;
import org.lab510.cryptodac.workload.PA;
import org.lab510.cryptodac.workload.Role;
import org.lab510.cryptodac.workload.UR;
import org.lab510.cryptodac.workload.User;

// class PublicKeyUserRevoker {
// private Workload workload;

// PublicKeyUserRevoker(Workload workload) {
// this.workload = workload;
// txs = new ArrayList<>();
// }

// void revoke() {
// UR ur = workload.revoke_user();

// txs.add(new Transaction<Player, PublicKeyEvent>(Player.ADMIN, PublicKeyEvent.GEN_KEY));

// for (int i = 0; i < ur.getRole().numUsers(); i++) {
// txs.add(new Transaction<Player, PublicKeyEvent>(Player.ADMIN, PublicKeyEvent.ENCRYPT));
// txs.add(new Transaction<Player, PublicKeyEvent>(Player.ADMIN, PublicKeyEvent.SIGN));
// }

// for (PA pa : ur.getRole().getPas()) {
// txs.add(new Transaction<Player, PublicKeyEvent>(Player.ADMIN, PublicKeyEvent.SYM_GEN_KEY));
// txs.add(new Transaction<Player, PublicKeyEvent>(Player.ADMIN, PublicKeyEvent.SYM_DECRYPT));
// txs.add(new Transaction<Player, PublicKeyEvent>(Player.ADMIN, PublicKeyEvent.SYM_ENCRYPT));

// for (int i = 0; i < pa.getPerm().numRoles(); i++) {
// txs.add(new Transaction<Player, PublicKeyEvent>(Player.ADMIN, PublicKeyEvent.ENCRYPT));
// txs.add(new Transaction<Player, PublicKeyEvent>(Player.ADMIN, PublicKeyEvent.SIGN));
// }

// }

// }
// }

public class PublicKeyDAC extends DAC {

    public PublicKeyDAC(Configuration configuration) {
        super(configuration);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void addUser() {
        super.addUser();
        addEvent(Player.USER, PublicKeyEventFactory.getEvent(EventType.KEY_GEN));
    }

    @Override
    void assignUrInner() {
        super.assignUrInner();
        addAssignUrEvents();
    }

    void addAssignUrEvents() {
        addEvent(Player.ADMIN, PublicKeyEventFactory.getEvent(EventType.DEC));
        addEvent(Player.ADMIN, PublicKeyEventFactory.getEvent(EventType.ENC));
    }


    @Override
    void assignPaInner() {
        super.assignPaInner();
        addAssignPaEvents();
    }

    void addAssignPaEvents() {
        addEvent(Player.ADMIN, PublicKeyEventFactory.getEvent(EventType.DEC));
        addEvent(Player.ADMIN, PublicKeyEventFactory.getEvent(EventType.ENC));
    }

    @Override
    UR revokeUrInner(UR ur) {
        super.revokeUrInner(ur);
        addRevokeUrEvents(ur);
        return ur;
    }

    void addRevokeUrEvents(UR ur) {
        for(var pa : workload.getPas(ur.getRole())) {
            addRevokePaEvents(pa);
        }

        addAddRoleEvents();

        for(var t : workload.getUrs(ur.getRole())) {        // re-assign
            addEvent(Player.ADMIN, PublicKeyEventFactory.getEvent(EventType.ENC));
        }

    }

    void addAddRoleEvents() {
        addEvent(Player.ADMIN, PublicKeyEventFactory.getEvent(EventType.KEY_GEN));
        addEvent(Player.ADMIN, PublicKeyEventFactory.getEvent(EventType.ENC));
    }

    @Override
    public PA revokePaInner(PA pa) {
        super.revokePaInner(pa);
        addRevokePaEvents(pa);
        return pa;
    }

    void addRevokePaEvents(PA pa) {
        addEvent(Player.ADMIN, PublicKeyEventFactory.getEvent(EventType.DEC));
        addEvent(Player.ADMIN, PublicKeyEventFactory.getEvent(EventType.SYM_DEC));
        addEvent(Player.ADMIN, PublicKeyEventFactory.getEvent(EventType.SYM_KEY_GEN));
        addEvent(Player.ADMIN, PublicKeyEventFactory.getEvent(EventType.SYM_ENC));
        for (var p : workload.getPas(pa.getPerm())) {
            addEvent(Player.ADMIN, PublicKeyEventFactory.getEvent(EventType.ENC));
        }
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
    public void report() {
        // TODO Auto-generated method stub

    }

}

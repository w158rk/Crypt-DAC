package org.lab510.cryptodac.dac;

import org.lab510.cryptodac.config.Configuration;
import org.lab510.cryptodac.dac.event.PREEventFactory;
import org.lab510.cryptodac.dac.event.PREEventFactory.EventType;
import org.lab510.cryptodac.utils.Printer;
import org.lab510.cryptodac.workload.PA;
import org.lab510.cryptodac.workload.Role;
import org.lab510.cryptodac.workload.UR;
import org.lab510.cryptodac.workload.User;

public class PREDAC extends DAC {

    public PREDAC(Configuration configuration) {
        super(configuration);
    }

    /**
     * @brief generate file key, encrypt the file, encrypt the key
     */
    @Override
    public void addPerm() {
        super.addPerm();
        addEvent(Player.USER, PREEventFactory.getEvent(EventType.SYM_KEY_GEN));
        addEvent(Player.USER, PREEventFactory.getEvent(EventType.SYM_ENC));
        addEvent(Player.USER, PREEventFactory.getEvent(EventType.ENC_2));
    }

    /**
     * @brief generate role key, generate re-encryption key, encrypt the role key
     */
    @Override
    public void addRole() {
        super.addRole();
        addAddRoleEvents();
    }

    void addAddRoleEvents() {
        addEvent(Player.ADMIN, PREEventFactory.getEvent(EventType.KEY_GEN));
        addEvent(Player.ADMIN, PREEventFactory.getEvent(EventType.REKEN_GEN));
        addEvent(Player.ADMIN, PREEventFactory.getEvent(EventType.ENC_2));
    }

    /**
     * @brief generate user key pair
     */
    @Override
    public void addUser() {
        super.addUser();
        addEvent(Player.USER, PREEventFactory.getEvent(EventType.KEY_GEN));
    }

    @Override
    public void read() {
        addEvent(Player.CLOUD, PREEventFactory.getEvent(EventType.REENC));
        addEvent(Player.CLOUD, PREEventFactory.getEvent(EventType.REENC));
        addEvent(Player.USER, PREEventFactory.getEvent(EventType.DEC_1));
        addEvent(Player.USER, PREEventFactory.getEvent(EventType.DEC_1));
        addEvent(Player.USER, PREEventFactory.getEvent(EventType.SYM_DEC));
    }

    /**
     *
     * 1. revoke all the permissions the given role has
     */
    @Override
    public Role removeRole() {
        Role role = super.removeRole();
        return role;
    }

    @Override
    public User removeUser() {
        User user = super.removeUser();
        return user;
    }

    @Override
    public void report() {
        // TODO Auto-generated method stub

    }

    /**
     * @brief
     *
     *        1. download data 2. decrypt key and data 3. generate new key and encrypt the file 4.
     *        encrypt key and upload
     */
    @Override
    public PA revokePaInner(PA pa) {
        super.revokePaInner(pa);
        addRevokePaEvents();
        return pa;
    }

    @Override
    UR revokeUrInner(UR ur) {
        super.revokeUrInner(ur);
        addRevokeUrEvents(ur);          // this has to be after super.revoke
        return ur;
    }

    void addRevokeUrEvents(UR ur) {

        /**
         *
         * 1. for all the permission where (role, permission) in PAS: re-generate permission keys
         * 2. generate new role key pair and re-encryption key
         * 3. encrypt the role key
         *
         * 2+3 = add role
         */
        for(var pa : workload.getPas(ur.getRole())) {
            addRevokePaEvents();
        }

        addAddRoleEvents();
    }

    @Override
    public void write() {
        addEvent(Player.CLOUD, PREEventFactory.getEvent(EventType.REENC));
        addEvent(Player.CLOUD, PREEventFactory.getEvent(EventType.REENC));
        addEvent(Player.USER, PREEventFactory.getEvent(EventType.DEC_1));
        addEvent(Player.USER, PREEventFactory.getEvent(EventType.DEC_1));
        addEvent(Player.USER, PREEventFactory.getEvent(EventType.SYM_ENC));
    }

    void addRevokePaEvents() {
        addEvent(Player.ADMIN, PREEventFactory.getEvent(EventType.DEC_2));
        addEvent(Player.ADMIN, PREEventFactory.getEvent(EventType.SYM_DEC));
        addEvent(Player.ADMIN, PREEventFactory.getEvent(EventType.SYM_KEY_GEN));
        addEvent(Player.ADMIN, PREEventFactory.getEvent(EventType.SYM_ENC));
        addEvent(Player.ADMIN, PREEventFactory.getEvent(EventType.ENC_2));
    }

}

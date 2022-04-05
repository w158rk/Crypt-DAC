package org.lab510.cryptodac.dac;

import java.util.List;

import org.lab510.cryptodac.dac.event.Event;

/**
 * dynamic access control
 */
public abstract class DAC {
    public abstract void addUser();
    public abstract void removeUser();
    public abstract void addPerm();
    public abstract void removePerm();
    public abstract void addRole();
    public abstract void removeRole();
    public abstract void assignUser();
    public abstract void revokeUser();
    public abstract void assignPerm();
    public abstract void revokePerm();
    public abstract void read();
    public abstract void write();
    public abstract void run();
    public abstract void report();

    protected void addEvent(Player player, Event event) {
        txs.add(new Transaction<Player,Event>(player, event));
    }

    private List<Transaction<Player, Event>> txs;
}

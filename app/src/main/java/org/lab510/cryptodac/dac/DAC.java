package org.lab510.cryptodac.dac;

/**
 * dynamic access control
 */
public interface DAC {
    public void addUser();
    public void removeUser();
    public void addPerm();
    public void removePerm();
    public void addRole();
    public void removeRole();
    public void assignUser();
    public void revokeUser();
    public void assignPerm();
    public void revokePerm();
    public void read();
    public void write();
    public void run();
    public void report();
}

package org.lab510.cryptodac.workload;

public interface IWorkload {
    public boolean assign_user();
    public boolean assign_perm();
    public boolean revoke_user();
    public boolean revoke_perm();
}

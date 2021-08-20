package org.lab510.cryptodac.workload;

import java.util.List;

import org.lab510.cryptodac.config.Configuration;

public interface IWorkload {

    boolean assign_user(IUser user, IRole role);

    public boolean assign_user();
    public boolean assign_perm();
    public boolean revoke_user();
    public boolean revoke_perm();
    public boolean initialize(Configuration conf);

    /**
     * obtain the number of User, Perm, Role, UR, PA
     * @return
     */
    public List<Integer> params();
}

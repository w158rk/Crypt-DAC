package org.lab510.cryptodac.dac;

import java.util.Set;
import org.lab510.cryptodac.utils.RandomPicker;
import org.lab510.cryptodac.workload.PA;
import org.lab510.cryptodac.workload.UR;
import org.lab510.cryptodac.workload.Workload;

public class PREUserRevoker {
    private PREDAC dac;

    public PREUserRevoker(PREDAC dac) {
        this.dac = dac;
    }

    /**
     *
     * 1. for all the permission where (role, permission) in PAS: re-generate permission keys
     * 2. generate new role key pair and re-encryption key
     * 3. encrypt the role key
     */
    void revoke(UR toBeRevoked) {
        Workload workload = dac.workload;

        // revoke the UR first
        workload.revokeUr(toBeRevoked);

        Set<PA> pas = workload.getPas(toBeRevoked.getRole());
        for(var pa : pas) {
            dac.addRevokePermEvents();
        }

        dac.addAddRoleEvents();
    }
}

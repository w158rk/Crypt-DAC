package org.lab510.cryptodac.config;

import java.util.Random;

/**
 * generate random parameters for the configuration
 *
 * @version 0.0.1
 */
public class ConfigProcessor {
    private double randomDouble(double low, double high) {
        double range = high - low;
        Random random = new Random(System.currentTimeMillis());
        double ret = random.nextDouble();
        ret = ret * range;
        return low + ret;
    }
    /**
     *
     * @param conf the configuration to be manipulated
     */
    public void process(Configuration conf) {

        double muA = randomDouble(conf.getDoubleValue("muAMin"), conf.getDoubleValue("muAMax"));
        double muU = randomDouble(conf.getDoubleValue("muUMin"), conf.getDoubleValue("muUMax"));
        conf.setDoubleValue("muA", muA);
        conf.setDoubleValue("muU", muU);
    }
}

package org.lab510.cryptodac.config;

import java.util.Random;

/**
 * generate random parameters for the configuration
 */
public class ConfigProcessor {
    private double randomDouble(double low, double high) {
        double range = high - low;
        Random random = new Random(System.currentTimeMillis());
        double ret = random.nextDouble();
        ret = ret * range;
        return low + ret;
    }

    public void process(Configuration conf) {

        double muA = randomDouble(conf.getDoubleValue("muAMin"), conf.getDoubleValue("muAMax"));
        double muU = randomDouble(conf.getDoubleValue("muUMin"), conf.getDoubleValue("muUMax"));
        conf.setDoubleValue("muA", muA);
        conf.setDoubleValue("muU", muU);
    }
}

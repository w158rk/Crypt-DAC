package org.lab510.cryptodac.config;

import java.util.HashMap;
import java.util.Map;

/**
 * class to store configuration contents
 *
 * @version 0.0.1
 */
public class Configuration {

    private Map<String, Integer> intProperties;
    private Map<String, Double> doubleProperties;

    Configuration() {
        intProperties = new HashMap<String, Integer>();
        doubleProperties = new HashMap<String, Double>();
    }

    int getIntegerValue(String key) {
        if(!intProperties.containsKey(key)) {
            return 0;
        }
        return intProperties.get(key).intValue();
    }

    double getDoubleValue(String key) {
        if(!doubleProperties.containsKey(key)) {
            return 0.0;
        }
        return doubleProperties.get(key).doubleValue();
    }

    int setIntegerValue(String key, int value) {
        Integer ret = intProperties.put(key, Integer.valueOf(value));
        return (ret==null) ? 0 : ret.intValue();
    }

    double setDoubleValue(String key, double value) {
        Double ret = doubleProperties.put(key, Double.valueOf(value));
        return (ret==null) ? 0 : ret.doubleValue();
    }
}

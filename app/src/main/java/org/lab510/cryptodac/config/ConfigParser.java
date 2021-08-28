package org.lab510.cryptodac.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


/**
 * This class is used to parse configuration files
 * written in YAML.
 *
 * @version 0.0.1
 */
public class ConfigParser {
    /**
     *
     * @param filename the configuration file, should be located in the resource folder
     * @return Configuration object corresponding to the given filename
     * @throws IOException
     * @throws NullPointerException
     */
    public Configuration parse (String filename) throws IOException,NullPointerException {
        Properties props = new Properties();
        if(!filename.startsWith("/")) {
            filename = "/" + filename;
        }
        InputStream stream = this.getClass().getResourceAsStream(filename);
        props.load(stream);
        Configuration ret = new Configuration();

        for(String key : props.stringPropertyNames()) {
            String sval = props.getProperty(key);

            if(key.startsWith("mu")) {
                ret.setDoubleValue(key, Double.parseDouble(sval));
            }
            else {
                ret.setIntegerValue(key, Integer.parseInt(sval));
            }
        }

        return ret;
    }
}

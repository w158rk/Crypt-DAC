package org.lab510.cryptodac.utils;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

public class SetFilter {
    public static <T> Set<T> filter(Set<T> set, Function<T, Boolean> func) {
        Set<T> ret = new HashSet<T>();
        for(T e : set) {
            if(func.apply(e)) {
                ret.add(e);
            }
        }
        return ret;
    }
}

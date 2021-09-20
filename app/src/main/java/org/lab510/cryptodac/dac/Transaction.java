package org.lab510.cryptodac.dac;

public class Transaction<K,V> {
    K who;
    V what;

    Transaction(K who, V what) {
        this.who = who;
        this.what = what;
    }

    @Override
    public String toString() {
        return String.format("%s : %s", who, what);
    }
}
package org.lab510.cryptodac.utils;
public class Pair<T, E> {
    private T t=null;
    private E e=null;

    public Pair(T t, E e) {
        this.t = t;
        this.e = e;
    }

    public T getFirst() {
        return t;
    }

    public E getSecond() {
        return e;
    }
}
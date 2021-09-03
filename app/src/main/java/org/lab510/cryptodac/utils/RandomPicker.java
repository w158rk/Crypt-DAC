package org.lab510.cryptodac.utils;

import java.util.Random;
import java.util.Set;

import org.lab510.cryptodac.error.Error;

public class RandomPicker<E> {
    private Set<E> set;

    public RandomPicker(Set<E> set) {
        this.set = set;
    }

    private int getRandomInteger(int bound) {
        return new Random().nextInt(bound);
    }

    private void throwIfNullOrEmpty() {
        if(set==null || set.isEmpty()) {
            throw new Error("The set of the picker is null/empty");
        }
    }

    public E getRandomElement() {

        throwIfNullOrEmpty();

        int i = getRandomInteger(set.size());
        for(E e : set) {
            if (i-- == 0)
                return e;
        }

        throw new Error("Cannot get any random element");
    }

    public Pair<E, E> getTwoDistinctRandomElements() {
        E e1 = getRandomElement();
        E e2 = getRandomElement();
        int i = 0;
        final int MAX_TRIES = 10;

        while(e2.equals(e1) && i<MAX_TRIES) {
            e2 = getRandomElement();
            i++;
        }

        if(i==MAX_TRIES) {
            throw new Error("Cannot find two distinct elements");
        }

        return new Pair<>(e1, e2);
    }
}

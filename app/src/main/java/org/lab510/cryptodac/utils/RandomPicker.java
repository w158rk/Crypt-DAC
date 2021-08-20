package org.lab510.cryptodac.utils;

import java.util.Random;
import java.util.Set;

public class RandomPicker<E> {
    private Set<E> set;

    public RandomPicker(Set<E> set) {
        this.set = set;
    }

    private int getRandomInteger(int bound) {
        return new Random().nextInt(bound);
    }

    public E getRandomElement() {
        if(set==null || set.isEmpty()) {
            return null;
        }

        int i = getRandomInteger(set.size());
        for(E e : set) {
            if (i-- == 0)
                return e;
        }
        return null;
    }
}

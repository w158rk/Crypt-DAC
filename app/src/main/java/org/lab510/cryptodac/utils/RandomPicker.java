package org.lab510.cryptodac.utils;

import java.util.Random;
import java.util.Set;
/**
 * To pick random element from a given Set object
 *
 * @version 0.0.1
 */
public class RandomPicker<E> {
    private Set<E> set;

    /**
     * constructor
     * @param set from which elements are selected
     */
    public RandomPicker(Set<E> set) {
        this.set = set;
    }

    private int getRandomInteger(int bound) {
        return new Random().nextInt(bound);
    }

    /**
     * get random element from the set
     * @return the selected element
     */
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

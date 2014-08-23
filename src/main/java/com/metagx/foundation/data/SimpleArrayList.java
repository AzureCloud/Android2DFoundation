package com.metagx.foundation.data;

import java.util.ArrayList;

/**
 * Created by Adam on 4/18/14.
 */
public class SimpleArrayList<E> extends ArrayList<E> {

    public SimpleArrayList() {
        super();
    }

    public E getFirst() {
        if (size() == 0) {
            return null;
        }
        return get(0);
    }

    public E getLast() {
        if (size() == 0) {
            return null;
        }
        return get(size() - 1);
    }
}

package com.metagx.foundation.math;

import java.util.Random;

/**
 * Created by Adam on 8/24/14.
 */
public class MathUtils {
    public static float randInRange(Random random, float min, float max) {
        return min + random.nextInt((int) (max - min));
    }
}

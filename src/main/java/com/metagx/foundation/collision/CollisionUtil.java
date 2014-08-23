package com.metagx.foundation.collision;

import com.metagx.foundation.bettergl.model.MotionModel;
import com.metagx.foundation.math.OverlapTester;

/**
 * Created by Adam on 12/18/13.
 */
public class CollisionUtil {
    public static boolean isCollision(MotionModel m1, MotionModel m2) {
        return OverlapTester.overlapRectangles(m1.bounds, m2.bounds);
    }
}

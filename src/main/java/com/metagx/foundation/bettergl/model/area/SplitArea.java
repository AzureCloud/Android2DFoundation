package com.metagx.foundation.bettergl.model.area;

/**
 * Created by Adam on 12/17/13.
 */
public class SplitArea {
    public final Area leftTop, rightBottom;
    public SplitArea(Area leftTop, Area rightBottom) {
        this.leftTop = leftTop;
        this.rightBottom = rightBottom;
    }
}

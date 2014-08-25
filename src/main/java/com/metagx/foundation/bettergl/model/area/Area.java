package com.metagx.foundation.bettergl.model.area;

import com.metagx.foundation.bettergl.BindableVertices;
import com.metagx.foundation.bettergl.GLGraphics;
import com.metagx.foundation.bettergl.model.LineType;
import com.metagx.foundation.bettergl.model.OpenGLLine;
import com.metagx.foundation.math.Rectangle;import java.util.Random;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Adam on 12/16/13.
 */
public class Area {
    protected Rectangle areaBounds;

    public Area(float centerX, float centerY, float width, float height) {
        areaBounds = new Rectangle(centerX-width/2, centerY-height/2, width, height);
    }

    public Rectangle getAreaBounds() {
        return areaBounds;
    }

    public Area createArea(float centerX, float centerY, float width, float height) {
        return new Area(centerX, centerY, width, height);
    }

    public SplitArea splitArea(OpenGLLine bisect) {
        if(bisect.getLineType() == LineType.HORIZONTAL) {

            //Horizontal
            float centerXTop = areaBounds.lowerLeft.x + areaBounds.width/2.0f;
            float centerYTop = (areaBounds.lowerLeft.y+areaBounds.height+bisect.getMotionModel().getPosition().y)/2.0f;

            float centerXBottom = areaBounds.lowerLeft.x + areaBounds.width/2.0f;
            float centerYBottom = (areaBounds.lowerLeft.y+bisect.getMotionModel().getPosition().y)/2.0f;

            float topHeight = 2*(centerYTop-bisect.getMotionModel().getPosition().y);
            float bottomHeight = 2*(bisect.getMotionModel().getPosition().y - centerYBottom);

            final Area top = createArea(centerXTop, centerYTop, areaBounds.width, topHeight);
            final Area bottom = createArea(centerXBottom, centerYBottom, areaBounds.width, bottomHeight);

            return new SplitArea(top, bottom);
        } else {
            //Vertical

            float centerXRight = (areaBounds.lowerLeft.x+areaBounds.width+bisect.getMotionModel().getPosition().x)/2;
            float centerYRight = areaBounds.lowerLeft.y+areaBounds.height/2;

            float centerXLeft = (areaBounds.lowerLeft.x+bisect.getMotionModel().getPosition().x)/2;
            float centerYLeft = areaBounds.lowerLeft.y+areaBounds.height/2;

            float rightWidth = 2*(centerXRight-bisect.getMotionModel().getPosition().x);
            float leftWidth = 2*(bisect.getMotionModel().getPosition().x - centerXLeft);

            final Area right = createArea(centerXRight, centerYRight, rightWidth, areaBounds.height);
            final Area left = createArea(centerXLeft, centerYLeft, leftWidth, areaBounds.height);

            return new SplitArea(left, right);
        }
    }
}

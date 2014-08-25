package com.metagx.foundation.bettergl.model;

import com.metagx.foundation.bettergl.model.area.Area;
import com.metagx.foundation.math.Rectangle;
import com.metagx.foundation.math.Vector;

import java.util.Random;

/**
 * Created by Adam on 4/18/14.
 */
public class BoundedMotionModel extends MotionModel {
    private static final Random rand = new Random();

    //World Bounds
    protected Area areaBounds;

    public final boolean bounce;

    public BoundedMotionModel(Area areaBounds, int width, int height) {
        this(areaBounds, width, height, true);
    }

    public BoundedMotionModel(Area areaBounds, int width, int height, boolean bounce) {
        super(width, height, areaBounds.getAreaBounds().width, areaBounds.getAreaBounds().height);
        this.areaBounds = areaBounds;
        position.set(areaBounds.getAreaBounds().lowerLeft.x + rand.nextFloat() * areaBounds.getAreaBounds().width, areaBounds.getAreaBounds().lowerLeft.y +  rand.nextFloat() * areaBounds.getAreaBounds().height);
        this.bounds = new Rectangle(position.x-width/2, position.y-height/2, width, height);
        this.wrapWorld = false;
        this.bounce = bounce;
    }

    public BoundedMotionModel(int width, int height, int glWorldWidth, int glWorldHeight, boolean bounce) {
        this(new Area(glWorldWidth/2, glWorldHeight/2, glWorldWidth, glWorldHeight), width, height, bounce);
    }

    @Deprecated
    public BoundedMotionModel(int glWorldWidth, int glWorldHeight, int width, int height, float vx, float vy) {
        super(glWorldWidth, glWorldHeight, width, height, vx, vy);
        this.bounce = true;
    }

    public void setNewArea(Area areaBounds) {
        this.areaBounds = areaBounds;
    }

    public BoundedMotionModel(Area areaBounds, int width, int height, float vX, float vY) {
        this(areaBounds, width, height);
        velocity.set(vX, vY);
    }

    public void update(float deltaTime) {
        super.update(deltaTime);

        boolean hitWall = false;

        if (position.x < areaBounds.getAreaBounds().lowerLeft.x + scaledWidth/2) {
            if (bounce) {
                velocity.x = velocity.x * -1;
            }
            position.x = areaBounds.getAreaBounds().lowerLeft.x +scaledWidth/2;
            hitWall = true;
        } else if (position.x > (areaBounds.getAreaBounds().lowerLeft.x+areaBounds.getAreaBounds().width)-scaledWidth/2) {
            if (bounce) {
                velocity.x = velocity.x * -1;
            }
            position.x = (areaBounds.getAreaBounds().lowerLeft.x+areaBounds.getAreaBounds().width)-scaledWidth/2;
            hitWall = true;
        }

        if (position.y < areaBounds.getAreaBounds().lowerLeft.y + scaledHeight/2) {
            if (bounce) {
                velocity.y = velocity.y * -1;
            }
            position.y = areaBounds.getAreaBounds().lowerLeft.y +scaledHeight/2;
            hitWall = true;
        } else if (position.y > (areaBounds.getAreaBounds().lowerLeft.y+areaBounds.getAreaBounds().height)-scaledHeight/2) {
            if (bounce) {
                velocity.y = velocity.y * -1;
            }
            position.y = (areaBounds.getAreaBounds().lowerLeft.y+areaBounds.getAreaBounds().height)-scaledHeight/2;
            hitWall = true;
        }

        if(hitWall) {
            lastCollisionId = -1;
        }
    }
}

package com.metagx.foundation.bettergl.model;

import android.graphics.RectF;

import com.metagx.foundation.bettergl.model.area.Area;
import com.metagx.foundation.math.Rectangle;
import com.metagx.foundation.math.Vector;

import java.util.Random;

/**
 * Created by Adam on 12/10/13.
 */
public class MotionModel {
    private static final Random rand = new Random();

    public static final float DEFAULT_MIN_SPEED = 160.0f;

    //Position
    public Vector position = new Vector();

    //Speed
    public Vector velocity = new Vector();

    public Vector acceleration = new Vector(0,0);

    protected float minimumSpeed = DEFAULT_MIN_SPEED;

    public float scaleX = 1, scaleY = 1;

    //Size
    public volatile int width, height;

    //World Bounds
    protected Area areaBounds;

    protected float scaledWidth;
    protected float scaledHeight;

    //Bounds
    public Rectangle bounds;

    public static final int UNSET = -1;
    public static final int IRRELEVANT = -9;

    private Integer lastCollisionId = UNSET;

    public MotionModel(Area areaBounds, int width, int height) {
        this.areaBounds = areaBounds;

        this.width = width;
        this.height = height;

        this.scaledWidth = width;
        this.scaledHeight = height;

        position.set(areaBounds.getAreaBounds().lowerLeft.x + rand.nextFloat() * areaBounds.getAreaBounds().width, areaBounds.getAreaBounds().lowerLeft.y +  rand.nextFloat() * areaBounds.getAreaBounds().height);

        this.bounds = new Rectangle(position.x-width/2, position.y-height/2, width, height);
    }

    @Deprecated
    public MotionModel(int glWorldWidth, int glWorldHeight, int width, int height, float vx, float vy) {
        this.areaBounds = null;

        this.width = width;
        this.height = height;

        this.scaledWidth = width;
        this.scaledHeight = height;

        position.set(rand.nextFloat() * glWorldWidth, rand.nextFloat() * glWorldHeight);

        velocity.set(vx, vy);

        this.bounds = new Rectangle(position.x-width/2, position.y-height/2, width, height);
    }

    public void setNewArea(Area areaBounds) {
        this.areaBounds = areaBounds;
    }

    public MotionModel(Area areaBounds, int width, int height, float vX, float vY) {
        this(areaBounds, width, height);
        velocity.set(vX, vY);
    }

    public void collideWith(Vector velocity, int lastCollisionId) {
        getVelocity().set(velocity);
        this.lastCollisionId = lastCollisionId;
    }

    public void collideWith(int lastCollisionId) {
        this.lastCollisionId = lastCollisionId;
    }

    public boolean wasLastCollisionWith(int id, int otherLastCollisionId) {
        int lastCollisionId = getLastCollisionId();

        if(lastCollisionId == IRRELEVANT) {
            return otherLastCollisionId != UNSET && otherLastCollisionId == getId();
        } else if(otherLastCollisionId == IRRELEVANT) {
            return lastCollisionId != UNSET && lastCollisionId == id;
        } else {
            return lastCollisionId != UNSET && otherLastCollisionId != UNSET &&
                getId() == otherLastCollisionId && lastCollisionId == id;
        }
    }

    public int getLastCollisionId() {
        return lastCollisionId;
    }

    public Integer getId() {
        return hashCode();
    }

    public Vector getVelocity() {
        return velocity;
    }

    public Vector getPosition() {
        return position;
    }

    public void update(float deltaTime) {
        position.add(velocity.x*deltaTime, velocity.y*deltaTime);

        velocity.add(acceleration);

        bounds.lowerLeft.set(position).sub(bounds.width / 2, bounds.height / 2);

        boolean hitWall = false;

        if (position.x < areaBounds.getAreaBounds().lowerLeft.x + scaledWidth/2) {
            velocity.x = velocity.x*-1;
            position.x = areaBounds.getAreaBounds().lowerLeft.x +scaledWidth/2;
            hitWall = true;
        } else if (position.x > (areaBounds.getAreaBounds().lowerLeft.x+areaBounds.getAreaBounds().width)-scaledWidth/2) {
            velocity.x = velocity.x*-1;
            position.x = (areaBounds.getAreaBounds().lowerLeft.x+areaBounds.getAreaBounds().width)-scaledWidth/2;
            hitWall = true;
        }

        if (position.y < areaBounds.getAreaBounds().lowerLeft.y + scaledHeight/2) {
            velocity.y = velocity.y*-1;
            position.y = areaBounds.getAreaBounds().lowerLeft.y +scaledHeight/2;
            hitWall = true;
        } else if (position.y > (areaBounds.getAreaBounds().lowerLeft.y+areaBounds.getAreaBounds().height)-scaledHeight/2) {
            velocity.y = velocity.y*-1;
            position.y = (areaBounds.getAreaBounds().lowerLeft.y+areaBounds.getAreaBounds().height)-scaledHeight/2;
            hitWall = true;
        }

        if(hitWall) {
            lastCollisionId = -1;
        }

//        if(hitWall) {
//            vX = vX/2;
//            if(vX < minimumSpeed && vX > 0) {
//                vX = minimumSpeed;
//            } else if(vX > -minimumSpeed && vX < 0) {
//                vX = -minimumSpeed;
//            }
//
//            vY = vY/2;
//            if(vY < minimumSpeed && vY > 0) {
//                vY = minimumSpeed;
//            } else if(vY > -minimumSpeed && vY < 0) {
//                vY = -minimumSpeed;
//            }
//        }

//        bounds.set(x, (y+height*scaleY),x+width*scaleX, y);
    }

    public void setScale(float scaleX, float scaleY) {
        this.scaleX = scaleX;
        this.scaleY = scaleY;

        this.scaledWidth = width * scaleX;
        this.scaledHeight = height * scaleY;
    }

    public void setMinimumSpeed() {
        this.minimumSpeed = 10;
    }

    public void moveTowardsPoint(Vector touchPoint) {
        float xDir = touchPoint.x<position.x?-1:1;
        float yDir = touchPoint.y<position.y?-1:1;

        velocity.set(Math.abs(velocity.x)*xDir, Math.abs(velocity.y)*yDir);
    }
}

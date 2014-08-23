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

    protected float scaledWidth;
    protected float scaledHeight;

    //Bounds
    public Rectangle bounds;

    public static final int UNSET = -1;
    public static final int IRRELEVANT = -9;

    protected Integer lastCollisionId = UNSET;

    public MotionModel(float posX, float posY, int width, int height) {
        this.width = width;
        this.height = height;

        this.position.set(posX, posY);

        this.scaledWidth = width;
        this.scaledHeight = height;

        this.bounds = new Rectangle(position.x-width/2, position.y-height/2, width, height);
    }

    public MotionModel(int width, int height) {
        this.width = width;
        this.height = height;

        this.scaledWidth = width;
        this.scaledHeight = height;

        this.bounds = new Rectangle(position.x-width/2, position.y-height/2, width, height);
    }

    @Deprecated
    public MotionModel(int glWorldWidth, int glWorldHeight, int width, int height, float vx, float vy) {
        this.width = width;
        this.height = height;

        this.scaledWidth = width;
        this.scaledHeight = height;

        position.set(rand.nextFloat() * glWorldWidth, rand.nextFloat() * glWorldHeight);

        velocity.set(vx, vy);

        this.bounds = new Rectangle(position.x-width/2, position.y-height/2, width, height);
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

    public boolean wrapWorld = true;

    public void update(float deltaTime) {
        position.add(velocity.x*deltaTime, velocity.y*deltaTime);

        if (wrapWorld) {
            if (position.x < 0) {
                position.x = 960 + position.x;
            } else if (position.x > 960) {
                position.x = position.x - 960;
            }

            if (position.y < 0) {
                position.y = 960 + position.y;
            } else if (position.y > 640) {
                position.y = position.y - 640;
            }
        }

        velocity.add(acceleration);
        bounds.lowerLeft.set(position).sub(scaledWidth / 2, scaledHeight / 2);
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

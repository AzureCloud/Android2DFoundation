package com.metagx.foundation.bettergl.model;

import android.graphics.RectF;

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
    protected float glWorldWidth;
    protected float glWorldHeight;

    protected float scaledWidth;
    protected float scaledHeight;

    //Bounds
    public Rectangle bounds;
//    public RectF bounds = new RectF();

    private Integer lastCollisionId = -1;

    //Movement
    protected boolean randomMovement = false;

    public MotionModel(int glWorldWidth, int glWorldHeight, int width, int height) {
        this.glWorldWidth = glWorldWidth;
        this.glWorldHeight = glWorldHeight;

        this.width = width;
        this.height = height;

        this.scaledWidth = width;
        this.scaledHeight = height;

        position.set(rand.nextFloat() * glWorldWidth, rand.nextFloat() * glWorldHeight);

        this.bounds = new Rectangle(position.x-width/2, position.y-height/2, width, height);
    }

    public MotionModel(int glWorldWidth, int glWorldHeight, int width, int height, float vX, float vY) {
        this.glWorldWidth = glWorldWidth;
        this.glWorldHeight = glWorldHeight;

        this.width = width;
        this.height = height;

        velocity.set(vX, vY);
    }

    public void collideWith(Vector velocity, int lastCollisionId) {
        getVelocity().set(velocity);
        this.lastCollisionId = lastCollisionId;
    }

    public boolean wasLastCollisionWith(int id, int otherLastCollisionId) {
        return lastCollisionId != -1 && otherLastCollisionId != -1 && getId() == otherLastCollisionId && lastCollisionId == id;
    }

    public int getLastCollisionId() {
        return lastCollisionId;
    }

    public Integer getId() {
        return hashCode();
    }

    public void setRandomMovement(boolean randomMovement) {
        this.randomMovement = randomMovement;
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

        if (position.x < scaledWidth/2) {
            velocity.x = velocity.x*-1;
            position.x = scaledWidth/2;
            hitWall = true;
        } else if (position.x > glWorldWidth-scaledWidth/2) {
            velocity.x = velocity.x*-1;
            position.x = glWorldWidth-scaledWidth/2;
            hitWall = true;
        }

        if (position.y < scaledHeight/2) {
            velocity.y = velocity.y*-1;
            position.y = scaledHeight/2;
            hitWall = true;
        } else if (position.y > glWorldHeight-scaledHeight/2) {
            velocity.y = velocity.y*-1;
            position.y = glWorldHeight-scaledHeight/2;
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

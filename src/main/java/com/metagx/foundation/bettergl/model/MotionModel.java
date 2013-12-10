package com.metagx.foundation.bettergl.model;

import android.graphics.RectF;

import java.util.Random;

/**
 * Created by Adam on 12/10/13.
 */
public class MotionModel {
    private static final Random rand = new Random();

    //Position
    public float x, y;

    //Speed
    private float vX, vY;

    //Size
    public int width, height;

    //World Bounds
    private float glWorldWidth;
    private float glWorldHeight;

    //Bounds
    public RectF bounds = new RectF();

    //Movement
    private boolean randomMovement = false;

    public MotionModel(int glWorldWidth, int glWorldHeight, int width, int height) {
        this.glWorldWidth = glWorldWidth;
        this.glWorldHeight = glWorldHeight;

        this.width = width;
        this.height = height;

        x = rand.nextFloat() * glWorldWidth;
        y = rand.nextFloat() * glWorldHeight;
    }

    public MotionModel(int glWorldWidth, int glWorldHeight, int width, int height, float vX, float vY) {
        this.glWorldWidth = glWorldWidth;
        this.glWorldHeight = glWorldHeight;

        this.width = width;
        this.height = height;

        this.vX = vX;
        this.vY = vY;
    }

    public void setRandomMovement(boolean randomMovement) {
        this.randomMovement = randomMovement;
    }

    public void setSpeedX(float vX) {
        this.vX = vX;
    }

    public void setSpeedY(float vY) {
        this.vY = vY;
    }

    public void setSpeed(float vX, float vY) {
        this.vX = vX;
        this.vY = vY;
    }

    public void update(float deltaTime) {
        x = x + vX * deltaTime;
        y = y + vY * deltaTime;

        if (x < 0) {
            vX = -vX ;
            x = 0;
        } else if (x > glWorldWidth) {
            vX = -vX;
            x = glWorldWidth;
        }

        if (y < 0) {
            vY = -vY;
            y = 0;
        } else if (y > glWorldHeight) {
            vY = -vY;
            y = glWorldHeight;
        }

        bounds.set(x,y,x+width,y+height);
    }
}

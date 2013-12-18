package com.metagx.foundation.math;

public class Rectangle {
    public final Vector lowerLeft;
    public float width, height;
    
    public Rectangle(float lowerLeftX, float lowerLeftY, float width, float height) {
        this.lowerLeft = new Vector(lowerLeftX,lowerLeftY);
        this.width = width;
        this.height = height;
    }

    public synchronized void setNewBounds(float lowerLeftX, float lowerLeftY, float width, float height) {
        this.lowerLeft.set(lowerLeftX,lowerLeftY);
        this.width = width;
        this.height = height;
    }
}

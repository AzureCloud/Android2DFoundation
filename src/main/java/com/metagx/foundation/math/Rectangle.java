package com.metagx.foundation.math;

public class Rectangle {
    public final Vector lowerLeft;
    public float width, height;
    
    public Rectangle(float x, float y, float width, float height) {
        this.lowerLeft = new Vector(x,y);
        this.width = width;
        this.height = height;
    }
}

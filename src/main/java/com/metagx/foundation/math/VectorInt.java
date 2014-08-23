package com.metagx.foundation.math;

import android.util.FloatMath;

/**
 * Created by Adam on 4/19/14.
 */
public class VectorInt {
    public static float TO_RADIANS = (1 / 180.0f) * (float) Math.PI;
    public static float TO_DEGREES = (1 / (float) Math.PI) * 180;
    public int x, y;

    public VectorInt() {
    }

    public VectorInt(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public VectorInt(VectorInt other) {
        this.x = other.x;
        this.y = other.y;
    }

    public VectorInt cpy() {
        return new VectorInt(x, y);
    }

    public VectorInt set(int x, int y) {
        this.x = x;
        this.y = y;
        return this;
    }

    public VectorInt set(VectorInt other) {
        this.x = other.x;
        this.y = other.y;
        return this;
    }

    public VectorInt add(int x, int y) {
        this.x += x;
        this.y += y;
        return this;
    }

    public VectorInt add(VectorInt other) {
        this.x += other.x;
        this.y += other.y;
        return this;
    }

    public VectorInt sub(int x, int y) {
        this.x -= x;
        this.y -= y;
        return this;
    }

    public VectorInt sub(VectorInt other) {
        this.x -= other.x;
        this.y -= other.y;
        return this;
    }

    public VectorInt mul(int scalar) {
        this.x *= scalar;
        this.y *= scalar;
        return this;
    }

    // return the inner product of this VectorInt a and b
    public double dot(VectorInt other) {
        return x*other.x + y*other.y;
    }

    public int len() {
        return (int) FloatMath.sqrt(x * x + y * y);
    }

    public VectorInt nor() {
        int len = len();
        if (len != 0) {
            this.x /= len;
            this.y /= len;
        }
        return this;
    }

    public int angle() {
        int angle = (int) (Math.atan2(y, x) * TO_DEGREES);
        if (angle < 0)
            angle += 360;
        return angle;
    }

    public VectorInt rotate(int angle) {
        float rad = angle * TO_RADIANS;
        float cos = FloatMath.cos(rad);
        float sin = FloatMath.sin(rad);

        int newX = (int) (this.x * cos - this.y * sin);
        int newY = (int) (this.x * sin + this.y * cos);

        this.x = newX;
        this.y = newY;

        return this;
    }

    public int dist(VectorInt other) {
        int distX = this.x - other.x;
        int distY = this.y - other.y;
        return (int) FloatMath.sqrt(distX * distX + distY * distY);
    }

    public int dist(int x, int y) {
        int distX = this.x - x;
        int distY = this.y - y;
        return (int) FloatMath.sqrt(distX * distX + distY * distY);
    }

    public int distSquared(VectorInt other) {
        int distX = this.x - other.x;
        int distY = this.y - other.y;
        return distX * distX + distY * distY;
    }

    public int distSquared(int x, int y) {
        int distX = this.x - x;
        int distY = this.y - y;
        return distX * distX + distY * distY;
    }
}

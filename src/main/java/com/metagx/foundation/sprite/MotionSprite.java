package com.metagx.foundation.sprite;

import android.graphics.Canvas;
import android.graphics.RectF;

import java.util.List;

/**
 * Created by Adam on 5/15/13.
 */
public abstract class MotionSprite extends Sprite {
    protected final int screenWidth, screenHeight;

    protected int speedX = 5, speedY = 5;

    public MotionSprite(int screenWidth, int screenHeight) {
        this.screenHeight = screenHeight;
        this.screenWidth = screenWidth;
    }

    protected abstract void positionInBounds();

    @Override
    public void update() {
        yPos += speedY;
        xPos += speedX;

        positionInBounds();

        super.update();
    }

    @Override
    public void draw(Canvas canvas) {
        if(activeFrameSet == null) {
            return;
        }
        update();

        curPos = new RectF(getXPos(), getYPos(),
                getXPos() + spriteWidth, getYPos() + spriteHeight);

        canvas.drawBitmap(sprite, sRectangle, curPos, null);
    }

    public void setSpeedX(int speedX) {
        this.speedX = speedX;
    }

    public void setSpeedY(int speedY) {
        this.speedY = speedY;
    }
}
